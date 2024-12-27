package EngineTester;

import org.lwjgl.opengl.Display;
import renderEngine.DisplayManager;

public class MainGameLoop {
    public static void main(String[] args) {
        DisplayManager.createDisplay();
        while(!Display.isCloseRequested()){//Основной цикл рендеринга
            DisplayManager.updateDisplay();
        }
        DisplayManager.closeDisplay();
    }
}
