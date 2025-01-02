package Particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import shaders.ShaderProgram;

import java.util.ArrayList;

public class ParticleShader extends ShaderProgram {

    private static final String VERTEX_FILE = "src/particles/particleVShader.glsl";
    private static final String FRAGMENT_FILE = "src/particles/particleFShader.glsl";
    private int location_modelViewMatrix;
    private int location_projectionMatrix;
    private int location_lifePercentage;
    private int location_colors;
    private int location_colorsLength;

    public ParticleShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);
    }

    @Override
    protected void getAllUniformLocations() {
        location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
        location_projectionMatrix = super.getUniformLocation("projectionMatrix");
        location_lifePercentage = super.getUniformLocation("lifePercentage");
        location_colors = super.getUniformLocation("colors");
        location_colorsLength = super.getUniformLocation("colorsLength");
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "position");
    }

    protected void loadModelViewMatrix(Matrix4f modelView) {
        super.loadMatrix(location_modelViewMatrix, modelView);
    }

    protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
        super.loadMatrix(location_projectionMatrix, projectionMatrix);
    }

    protected void loadLifePercentage(float lifePercentage) {
        super.loadFloat(location_lifePercentage, lifePercentage);
    }

    protected void loadColors(ArrayList<Vector3f> colors) {
        super.loadVectors(location_colors, colors);
    }

    protected void loadColorsLength(int length) {
        super.loadInt(location_colorsLength, length);
    }
}