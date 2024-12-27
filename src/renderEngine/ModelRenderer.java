package renderEngine;

import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.w3c.dom.Text;

//Отображение 3д модели
public class ModelRenderer {

    //Подготовка OpenGL для рендеринга
    public void preapare(){
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);//Очистка предыдущего кадра
        GL11.glClearColor(0, 0, 0, 1);//Заливка фона цветом с параметрами rgba
    }

    //Отобразить 3д модель
    public void render(TexturedModel texturedModel){
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray(model.getVaoID());//Обращаемся к VAO модели
        GL20.glEnableVertexAttribArray(0);//Обращаемся по индексу атрибута в VAO
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);//Активация текстуры для OpenGL
        GL11.glBindTexture(GL11.GL_TEXTURE, texturedModel.getTexture().getTextureID());
        //Указываем, что рисуем, в нашем случае - полигоны, далее сколько нужно нарисовать, какого типа и откуда начинаем
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);//Прекращаем обращение к атрибуту VAO
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);//Прекращаем обращение к VAO
    }

}
