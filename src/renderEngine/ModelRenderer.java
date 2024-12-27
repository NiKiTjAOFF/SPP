package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

//Отображение 3д модели
public class ModelRenderer {

    //Подготовка OpenGL для рендеринга
    public void preapare(){
        GL11.glClearColor(0, 1, 1, 1);//Подготовить цвет с параметрами rgba
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);//Очистить дисплей подготовленным цветом (замена на него)
    }

    //Отобразить 3д модель
    public void render(RawModel model){
        GL30.glBindVertexArray(model.getVaoID());//Обращаемся к VAO модели
        GL20.glEnableVertexAttribArray(0);//Обращаемся по индексу атрибута в VAO
        //Указываем, что рисуем, в нашем случае - полигоны, далее откуда начинаем и сколько нужно нарисовать
        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, model.getVertexCount());
        GL20.glDisableVertexAttribArray(0);//Прекращаем обращение к атрибуту VAO
        GL30.glBindVertexArray(0);//Прекращаем обращение к VAO
    }

}
