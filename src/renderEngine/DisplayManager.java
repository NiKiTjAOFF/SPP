package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

//Упрвление дисплеем (создание, обновление, удаление)
public class DisplayManager {
    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 120;
    private static final String TITLE = "Particle Editor";
    private static long lastFrameTime;
    private static float delta;


    //Создаёт дисплей при начале программы
    public static void createDisplay(){

        ContextAttribs attribs = new ContextAttribs(3, 2)//Версия OpenGL, которая используется
        .withForwardCompatible(true)//Настройки
        .withProfileCompatibility(true);//Настройки

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));//Определяет размеры экрана
            Display.create(new PixelFormat(), attribs);//Создание дисплея
            Display.setTitle(TITLE);//Указать название окна
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
