package EngineTester;

import Particles.Particle;
import Particles.ParticleMaster;
import Particles.ParticleSystem;
import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.ModelLoader;
import renderEngine.ModelRenderer;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

import javax.swing.*;
import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;

public class MainGameLoop {
    public static void main(String[] args) {
        GUI gui = new GUI();
        DisplayManager.createDisplay(GUI.canvas);
        ModelLoader loader = new ModelLoader();
        StaticShader modelShader = new StaticShader();
        ModelRenderer renderer = new ModelRenderer(modelShader);

        //Entity rendering
        float[] vertices = {
                -0.5f, 0.5f, 0f,//v0
                -0.5f, -0.5f, 0f,//v1
                0.5f, -0.5f, 0f,//v2
                0.5f, 0.5f, 0f//v3
        };
        int[] indices = {
                0, 1, 3,//Левый верхний треугольник (V0, V1, V3)
                3, 1, 2//Правый нижний треугольник (V3, V1, V2)
        };
        float[] textureCoords = {
                0, 0,//V0
                0, 1,//V1
                1, 1,//V2
                1, 0//V3
        };

        RawModel quad = loader.loadToVAO(vertices, textureCoords, indices);//Создание 3д модели на основе индексированных вершин
        ModelTexture texture = new ModelTexture(loader.loadTexture("test"));//Загрузка текстуры
        TexturedModel texturedModel = new TexturedModel(quad, texture);//Добавление текстуры 3д модели
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);

        ParticleSystem system = new ParticleSystem();
        ParticleMaster.init(loader, renderer.getProjectionMatrix());

        boolean isRotationAroundObjectNeeded = false;
        Camera camera = new Camera(new Vector3f(0, 0, 0), isRotationAroundObjectNeeded);

        while(!Display.isCloseRequested()){//Основной цикл рендеринга
            //Entity transformation
            //entity.increasePosition(0, 0, -0.02f);
            //entity.increaseRotation(-1, 1, 0.5f);
            //entity.increaseScale(0.002f);

            camera.setIsRotationAroundObjectNeeded(isRotationAroundObjectNeeded);
            camera.move();
            renderer.preapare();

            //Particle Emitting
            gui.setParticle(system);
            gui.setUtils(system);
            system.generateParticles();
            ParticleMaster.update();

            //Entity rendering
            modelShader.start();
            modelShader.loadViewMatrix(camera);
            renderer.render(entity, modelShader);
            modelShader.stop();

            ParticleMaster.renderParticles(camera);

            DisplayManager.updateDisplay();
        }

        ParticleMaster.cleanUp();
        modelShader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}