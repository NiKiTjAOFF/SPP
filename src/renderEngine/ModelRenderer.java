package renderEngine;

import entities.Entity;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.newdawn.slick.opengl.renderer.Renderer;
import shaders.StaticShader;
import toolbox.Maths;

//Отображение 3д модели
public class ModelRenderer {

    private static final float FOV = 70;
    private static final float NEAR_PLANE = 0.1f;
    private static final float FAR_PLANE = 1000000f;
    private Matrix4f projectionMatrix;

    public ModelRenderer(StaticShader shader) {
        this.projectionMatrix = Maths.createProjectionMatrix(FOV, NEAR_PLANE, FAR_PLANE);
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    //Подготовка OpenGL для рендеринга
    public void preapare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);//Очистка предыдущего кадра
        GL11.glClearColor(0, 0, 0, 1);//Заливка фона цветом с параметрами rgba
    }

    //Отобразить 3д модель
    public void render(Entity entity, StaticShader shader){
        TexturedModel model = entity.getModel();
        RawModel rawModel = model.getRawModel();

        //Обращаемся к VAO модели
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);//Обращаемся по индексу атрибута в VAO
        GL20.glEnableVertexAttribArray(1);

        //Создание и загрузка в униформу матрицы трансформации
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(),
                entity.getRotY(),entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);

        //Активация текстуры для OpenGL
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE, model.getTexture().getTextureID());

        //Указываем, что рисуем, в нашем случае - полигоны, далее сколько нужно нарисовать, какого типа и откуда начинаем
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);//Прекращаем обращение к атрибуту VAO
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);//Прекращаем обращение к VAO
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }
}
