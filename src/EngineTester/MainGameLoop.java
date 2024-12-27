package EngineTester;

import models.TexturedModel;
import org.lwjgl.opengl.Display;
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
        ModelRenderer renderer = new ModelRenderer();
        StaticShader shader = new StaticShader();

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

        while(!Display.isCloseRequested()){//Основной цикл рендеринга
            renderer.preapare();

            shader.start();
            renderer.render(texturedModel);
            shader.stop();

            DisplayManager.updateDisplay();

        }

        shader.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
