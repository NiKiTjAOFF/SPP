package EngineTester;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.DisplayManager;
import renderEngine.ModelLoader;
import renderEngine.ModelRenderer;
import models.RawModel;
import shaders.StaticShader;
import textures.ModelTexture;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        ModelLoader loader = new ModelLoader();
        StaticShader shader = new StaticShader();
        ModelRenderer renderer = new ModelRenderer(shader);

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

        while(!Display.isCloseRequested()){//Основной цикл рендеринга
            //entity.increasePosition(0, 0, -0.02f);
            //entity.increaseRotation(-1, 1, 0.5f);
            //entity.increaseScale(0.002f);
            camera.move();
            renderer.preapare();

            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity, shader);
            shader.stop();

            DisplayManager.updateDisplay();

        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
