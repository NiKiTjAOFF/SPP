package EngineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;
import renderEngine.ModelLoader;
import renderEngine.ModelRenderer;
import renderEngine.RawModel;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        ModelLoader loader = new ModelLoader();
        ModelRenderer renderer = new ModelRenderer();

        //Вершины для отрисовки квадрата. Квадрат - 2 полигона, для которых вершины указываются против часовой стрелки.
        //Поэтому указываем левую верхнюю, левую нижнюю, правую верхнюю. После правую верхнюю, левую нижнюю, правую нижнюю.
        float[] quad = {
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };
        RawModel model = loader.loadToVAO(quad);

        while(!Display.isCloseRequested()){//Основной цикл рендеринга
            renderer.preapare();

            renderer.render(model);
            DisplayManager.updateDisplay();


        }

        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
}
