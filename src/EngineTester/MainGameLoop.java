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
        DisplayManager.createDisplay((Canvas) gui.getContentPane().getComponents()[GUI.CANVAS_ID]);
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

        //Particle properties
        float spawnPointX = 0;
        float spawnPointY = 0;
        float spawnPointZ = 0;
        Vector3f particleEmittingCenter = new Vector3f(spawnPointX, spawnPointY, spawnPointZ);
        //Area bounds
        float positiveXBound = 10 + particleEmittingCenter.x;
        float positiveYBound = 10 + particleEmittingCenter.y;
        float positiveZBound = 10 + particleEmittingCenter.z;
        float negativeXBound = -10 + particleEmittingCenter.x;
        float negativeYBound = -10 + particleEmittingCenter.y;
        float negativeZBound = -10 + particleEmittingCenter.z;
        float pullCenterX = 5;
        float pullCenterY = 5;
        float pullCenterZ = 5;
        Vector3f particlePositiveBounds;
        Vector3f particleNegativeBounds;
        Vector3f pullCenter;
        float pps = 50f;
        float ppsChange = 1f;
        float speed = 0f;
        float speedChange = 1f;
        float gravityComplient = 0f;
        float gravityComplientChange = 0.002f;
        float lifeLength = 0.2f;
        float lifeLengthChange = 0.2f;
        float scale = 1;
        //Colors
        float scaleChange = 0.1f;
        ArrayList<ArrayList<Float>> colorsRGB = new ArrayList<>();
        colorsRGB.add(new ArrayList<>(Arrays.asList(1f, 0f, 0f)));
        colorsRGB.add(new ArrayList<>(Arrays.asList(1f, 0.5f, 0f)));
        colorsRGB.add(new ArrayList<>(Arrays.asList(0f, 0f, 0f)));
        colorsRGB.add(new ArrayList<>(Arrays.asList(0f, 0.5f, 1f)));
        ArrayList<Vector3f> colors = new ArrayList<>();
        for(int i = 0; i < colorsRGB.size(); i++) {
            colors.add(new Vector3f(colorsRGB.get(i).get(0), colorsRGB.get(i).get(1), colorsRGB.get(i).get(2)));
        }

        //Particle utils
        float directionX = 0;
        float directionY = 0;
        float directionZ = 0;
        Vector3f direction;
        float directionError = 0.2f;
        boolean randomRotation = true;
        float lifeError = 0.1f;
        float speedError = 0.f;
        float scaleError = 0.4f;
        float particlePulsesASecond = 1f;
        float particlePulsesASecondChange = 0.5f;

        //Particle spawn properties
        int particleEmittingType = 0;
        int spawnAreaType = 0;
        int spawnBounds = 0;
        int directionType = 0;

        ParticleSystem system = new ParticleSystem(pps, speed, gravityComplient, lifeLength, scale, colors);
        ParticleMaster.init(loader, renderer.getProjectionMatrix());

        boolean isRotationAroundObjectNeeded = false;
        Camera camera = new Camera(particleEmittingCenter, isRotationAroundObjectNeeded);

        while(!Display.isCloseRequested()){//Основной цикл рендеринга
            //Entity transformation
            //entity.increasePosition(0, 0, -0.02f);
            //entity.increaseRotation(-1, 1, 0.5f);
            //entity.increaseScale(0.002f);

            camera.setIsRotationAroundObjectNeeded(isRotationAroundObjectNeeded);
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
                if(Keyboard.isKeyDown(Keyboard.KEY_6)) {
                    particleEmittingType -= 1;
                    if(particleEmittingType < 0) {
                        particleEmittingType = system.PARTICLE_EMITTING_TYPE_NUMBER - 1;
                    }
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_7)) {
                    spawnAreaType -= 1;
                    if(spawnAreaType < 0) {
                        spawnAreaType = system.SPAWN_AREA_NUMBER - 1;
                    }
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_8)) {
                    spawnBounds -= 1;
                    if(spawnBounds < 0) {
                        spawnBounds = system.SPAWN_BOUNDS_NUMBER - 1;
                    }
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_9)) {
                    particlePulsesASecond -= particlePulsesASecondChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_0)) {
                    isRotationAroundObjectNeeded = false;
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
                if(Keyboard.isKeyDown(Keyboard.KEY_6)) {
                    particleEmittingType += 1;
                    if(particleEmittingType > system.PARTICLE_EMITTING_TYPE_NUMBER - 1) {
                        particleEmittingType = 0;
                    }
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_7)) {
                    spawnAreaType += 1;
                    if(spawnAreaType > system.SPAWN_AREA_NUMBER - 1) {
                        spawnAreaType = 0;
                    }
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_8)) {
                    spawnBounds += 1;
                    if(spawnBounds > system.SPAWN_BOUNDS_NUMBER - 1) {
                        spawnBounds = 0;
                    }
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_9)) {
                    particlePulsesASecond += particlePulsesASecondChange;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_0)) {
                    isRotationAroundObjectNeeded = true;
                }
            }
            particleEmittingCenter = new Vector3f(spawnPointX, spawnPointY, spawnPointZ);
            particlePositiveBounds = new Vector3f(positiveXBound, positiveYBound, positiveZBound);
            particleNegativeBounds = new Vector3f(negativeXBound, negativeYBound, negativeZBound);
            pullCenter = new Vector3f(pullCenterX, pullCenterY, pullCenterZ);
            for(int i = 0; i < colorsRGB.size(); i++) {
                colors.get(i).set(colorsRGB.get(i).get(0), colorsRGB.get(i).get(1), colorsRGB.get(i).get(2));
            }
            direction = new Vector3f(directionX, directionY, directionZ);

            system.setParticle(pps, speed, gravityComplient, lifeLength, scale, colors);
            system.setUtils(direction, directionError, randomRotation, speedError, lifeError, scaleError,
                    particlePulsesASecond, particleEmittingCenter, particlePositiveBounds, particleNegativeBounds, pullCenter,
                    particleEmittingType, spawnAreaType, spawnBounds, directionType);
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
