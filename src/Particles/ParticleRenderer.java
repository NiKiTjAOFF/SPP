package Particles;

import entities.Camera;
import models.RawModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.ModelLoader;
import toolbox.Maths;

import java.util.List;

public class ParticleRenderer {
    private static final float[] VERTICES = {
            -0.5f, 0.5f, 0f,//v0
            -0.5f, -0.5f, 0f,//v1
            0.5f, -0.5f, 0f,//v2
            0.5f, 0.5f, 0f//v3
    };
    private static final int[] INDICES = {
            0, 1, 3,//Левый верхний треугольник (V0, V1, V3)
            3, 1, 2//Правый нижний треугольник (V3, V1, V2)
    };
    private static final float[] TEXTURE_COORDS = {
            0, 0,//V0
            0, 1,//V1
            1, 1,//V2
            1, 0//V3
    };
    private RawModel quad;
    private ParticleShader shader;

    protected ParticleRenderer(ModelLoader loader, Matrix4f projectionMatrix){
        quad = loader.loadToVAO(VERTICES, TEXTURE_COORDS, INDICES);
        shader = new ParticleShader();
        shader.start();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    protected void render(List<Particle> particles, Camera camera){
        Matrix4f viewMatrix = Maths.createViewMatrix(camera);
        prepare();
        for(Particle particle : particles) {
            shader.loadLifePercentage(particle.getLifePercentage());
            shader.loadColors(particle.getColors());
            shader.loadColorsLength(particle.getColors().size());
            updateModelViewMatrix(particle.getPosition(), particle.getRotation(), particle.getScale(), viewMatrix);
            GL11.glDrawElements(GL11.GL_TRIANGLES, quad.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        }
        finishRendering();
    }

    protected void cleanUp(){
        shader.cleanUp();
    }

    private void updateModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix) {
        Matrix4f modelMatrix = new Matrix4f();
        Matrix4f.translate(position, modelMatrix, modelMatrix);
        //Транспонирование, чтобы частицы всегда были перпендикулярны взгляду камеры
        modelMatrix.m00 = viewMatrix.m00;
        modelMatrix.m01 = viewMatrix.m10;
        modelMatrix.m02 = viewMatrix.m20;
        modelMatrix.m10 = viewMatrix.m01;
        modelMatrix.m11 = viewMatrix.m11;
        modelMatrix.m12 = viewMatrix.m21;
        modelMatrix.m20 = viewMatrix.m02;
        modelMatrix.m21 = viewMatrix.m12;
        modelMatrix.m22 = viewMatrix.m22;
        Matrix4f.rotate((float)Math.toRadians(rotation), new Vector3f(0, 0, 1), modelMatrix, modelMatrix);
        Matrix4f.scale(new Vector3f(scale, scale, scale), modelMatrix, modelMatrix);
        Matrix4f modelViewMatrix = Matrix4f.mul(viewMatrix, modelMatrix, null);
        shader.loadModelViewMatrix(modelViewMatrix);
    }

    private void prepare(){
        shader.start();
        GL30.glBindVertexArray(quad.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL11.glEnable(GL11.GL_BLEND);//Включить прозрачность
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
    }

    private void finishRendering(){
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        shader.stop();
    }
}