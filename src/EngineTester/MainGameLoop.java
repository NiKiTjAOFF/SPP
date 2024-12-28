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

import java.security.Key;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        ModelLoader loader = new ModelLoader();
        StaticShader modelShader = new StaticShader();
        ModelRenderer renderer = new ModelRenderer(modelShader);
        ParticleMaster.init(loader, renderer.getProjectionMatrix());

        //Вершины для отрисовки квадрата. Квадрат - 2 полигона, для которых вершины указываются против часовой стрелки.
        //Поэтому указываем левую верхнюю, левую нижнюю, правую верхнюю. После правую верхнюю, левую нижнюю, правую нижнюю.

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
        Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);
        Camera camera = new Camera();

        float pps = 50;
        float ppsChange = 1;
        float speed = 25;
        float speedChange = 1;
        float gravityComplient = 0.3f;
        float gravityComplientChange = 0.05f;
        float lifeLength = 4;
        float lifeLengthChange = 0.2f;
        float scale = 1;
        float scaleChange = 0.1f;
        Vector3f startColor = new Vector3f(1, 0, 0);
        Vector3f endColor = new Vector3f(0, 0, 1);

        Vector3f direction = new Vector3f(0, 1, 0);
        float directionError = 0.1f;
        float lifeError = 0.1f;
        float speedError = 0.4f;
        float scaleError = 0.4f;

        ParticleSystem system = new ParticleSystem(pps, speed, gravityComplient, lifeLength, scale, startColor, endColor);
        system.randomizeRotation();
        system.setDirection(direction, directionError);
        system.setLifeError(lifeError);
        system.setSpeedError(speedError);
        system.setScaleError(scaleError);


        while(!Display.isCloseRequested()){//Основной цикл рендеринга
            //entity.increasePosition(0, 0, -0.02f);
            //entity.increaseRotation(-1, 1, 0.5f);
            //entity.increaseScale(0.002f);
            camera.move();
            renderer.preapare();

            if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                if(Keyboard.isKeyDown(Keyboard.KEY_1)) {
                    pps -= ppsChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_2)) {
                    speed -= speedChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_3)) {
                    gravityComplient -= gravityComplientChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_4)) {
                    lifeLength -= lifeLengthChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_5)) {
                    scale -= scaleChange;
                }
            }
            else {
                if(Keyboard.isKeyDown(Keyboard.KEY_1)) {
                    pps += ppsChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_2)) {
                    speed += speedChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_3)) {
                    gravityComplient += gravityComplientChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_4)) {
                    lifeLength += lifeLengthChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_5)) {
                    scale += scaleChange;
                }
            }
            system.setAll(pps, speed, gravityComplient, lifeLength, scale, startColor, endColor);

            system.generateParticles(new Vector3f(0, 0, 0));
            ParticleMaster.update();
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
