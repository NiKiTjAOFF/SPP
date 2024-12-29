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


        //Вершины для отрисовки квадрата. Квадрат - 2 полигона, для которых вершины указываются против часовой стрелки.
        //Поэтому указываем левую верхнюю, левую нижнюю, правую верхнюю. После правую верхнюю, левую нижнюю, правую нижнюю.

        //Entity rendering
//        float[] vertices = {
//                -0.5f, 0.5f, 0f,//v0
//                -0.5f, -0.5f, 0f,//v1
//                0.5f, -0.5f, 0f,//v2
//                0.5f, 0.5f, 0f//v3
//        };
//        int[] indices = {
//                0, 1, 3,//Левый верхний треугольник (V0, V1, V3)
//                3, 1, 2//Правый нижний треугольник (V3, V1, V2)
//        };
//        float[] textureCoords = {
//                0, 0,//V0
//                0, 1,//V1
//                1, 1,//V2
//                1, 0//V3
//        };

        //RawModel quad = loader.loadToVAO(vertices, textureCoords, indices);//Создание 3д модели на основе индексированных вершин
        //ModelTexture texture = new ModelTexture(loader.loadTexture("test"));//Загрузка текстуры
        //TexturedModel texturedModel = new TexturedModel(quad, texture);//Добавление текстуры 3д модели
        //Entity entity = new Entity(texturedModel, new Vector3f(0, 0, -1), 0, 0, 0, 1);

        //Particle properties
        float spawnPointX = 0;
        float spawnPointY = 0;
        float spawnPointZ = 0;
        Vector3f particleSpawnPoint = new Vector3f(spawnPointX, spawnPointY, spawnPointZ);
        //Area bounds
        float positiveXBound = 10 + particleSpawnPoint.x;
        float positiveYBound = 10 + particleSpawnPoint.y;
        float positiveZBound = 10 + particleSpawnPoint.z;
        float negativeXBound = -10 + particleSpawnPoint.x;
        float negativeYBound = -10 + particleSpawnPoint.y;
        float negativeZBound = -10 + particleSpawnPoint.z;
        Vector3f particlePositiveBounds;
        Vector3f particleNegativeBounds;
        float pps = 50f;
        float ppsChange = 1f;
        float speed = 0f;
        float speedChange = 1f;
        float gravityComplient = 0f;
        float gravityComplientChange = 0f;
        float lifeLength = 4;
        float lifeLengthChange = 0.2f;
        float scale = 1;
        //Colors
        float scaleChange = 0.1f;
        float startColorR = 1;
        float startColorG = 0;
        float startColorB = 0;
        float endColorR = 0;
        float endColorG = 0;
        float endColorB = 1;
        Vector3f startColor = new Vector3f(startColorR, startColorG, startColorB);
        Vector3f endColor = new Vector3f(endColorR, endColorG, endColorB);

        //Particle utils
        float directionX = 0;
        float directionY = 0;
        float directionZ = 0;
        Vector3f direction;
        float directionError = 0.1f;
        boolean randomRotation = true;
        float lifeError = 0.1f;
        float speedError = 0;
        float scaleError = 0.4f;

        //Particle spawn properties
        int particleEmittingType = 1;
        int particleSpawnArea = 1;

        ParticleSystem system = new ParticleSystem(pps, speed, gravityComplient, lifeLength, scale, startColor, endColor);
        ParticleMaster.init(loader, renderer.getProjectionMatrix());

        boolean isRotationAroundObjectNeeded = true;
        Camera camera = new Camera(particleSpawnPoint, isRotationAroundObjectNeeded);


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
                    particleEmittingType = system.CONSTANT_EMITTING;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_7)) {
                    particleSpawnArea = system.SOFT_AREA;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_8)) {
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
                    particleEmittingType = system.PULSE;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_7)) {
                    particleSpawnArea = system.DOT;
                }
                if(Keyboard.isKeyDown(Keyboard.KEY_8)) {
                    isRotationAroundObjectNeeded = true;
                }
            }
            particleSpawnPoint = new Vector3f(spawnPointX, spawnPointY, spawnPointZ);
            particlePositiveBounds = new Vector3f(positiveXBound, positiveYBound, positiveZBound);
            particleNegativeBounds = new Vector3f(negativeXBound, negativeYBound, negativeZBound);
            startColor = new Vector3f(startColorR, startColorG, startColorB);
            endColor = new Vector3f(endColorR, endColorG, endColorB);
            direction = new Vector3f(directionX, directionY, directionZ);

            system.setParticle(pps, speed, gravityComplient, lifeLength, scale, startColor, endColor);
            system.setUtils(direction, directionError, randomRotation, speedError, lifeError, scaleError);
            system.generateParticles(particleSpawnPoint, particlePositiveBounds, particleNegativeBounds,
                    particleEmittingType, particleSpawnArea);
            ParticleMaster.update();

            //Entity rendering
//            modelShader.start();
//            modelShader.loadViewMatrix(camera);
//            renderer.render(entity, modelShader);
//            modelShader.stop();

            ParticleMaster.renderParticles(camera);

            DisplayManager.updateDisplay();

        }

        ParticleMaster.cleanUp();
        modelShader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
