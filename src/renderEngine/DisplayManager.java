package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import java.awt.*;

//Упрвление дисплеем (создание, обновление, удаление)
public class DisplayManager {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;
    private static final int FPS_CAP = 120;
    private static final String TITLE = "Particle Editor";
    private static long lastFrameTime;
    private static float delta;

    //Создаёт дисплей при начале программы
    public static void createDisplay(Canvas canvas){

        ContextAttribs attribs = new ContextAttribs(3, 2)//Версия OpenGL, которая используется
        .withForwardCompatible(true)//Настройки
        .withProfileCompatibility(true);//Настройки

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));//Определяет размеры экрана
            canvas.setSize(WIDTH, HEIGHT);
            Display.setParent(canvas);
            Display.setTitle(TITLE);//Указать название окна
            Display.create(new PixelFormat(), attribs);//Создание дисплея
        } catch (LWJGLException e) {
            throw new RuntimeException(e);
        }
        GL11.glViewport(0, 0, WIDTH, HEIGHT);//Определяет, в каком пространстве дисплея происходит рендеринг
        lastFrameTime = getCurrentTime();
    }

    //Обновляет дисплей каждый кадр
    public static void updateDisplay(){
        Display.sync(FPS_CAP);//Указать FPS, с которым будет обновляться дисплей
        Display.update();
        long currentFrameTime = getCurrentTime();
        delta = (currentFrameTime - lastFrameTime) / 1000f;
        lastFrameTime = currentFrameTime;
    }

    public static float getFrameTimeSeconds() {
        return delta;
    }

    //Удаляет дисплей после завершения программы
    public static void closeDisplay(){
        Display.destroy();//Удаление дисплея
    }

    private static long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }
}
