package Particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import renderEngine.DisplayManager;

import java.util.ArrayList;
import java.util.Random;

public class ParticleSystem {

    private float pps, averageSpeed, gravityComplient, averageLifeLength, averageScale;
    private ArrayList<Vector3f> colors;
    private float speedError, lifeError, scaleError = 0, directionError = 0, particlePulsesASecond = 1;
    private Vector3f direction;
    private boolean randomRotation = false;
    private Vector3f positiveBounds = new Vector3f(0, 0, 0);
    private Vector3f negativeBounds = new Vector3f(0, 0, 0);
    private Vector3f systemCenter = new Vector3f(0, 0, 0);
    private Vector3f pullCenter = new Vector3f(0, 0, 0);
    private int particleEmittingType = 0, spawnAreaType = 0, spawnBounds = 0, directionType = 0;
    private Random random = new Random();
    public final int PULSE = 0, CONSTANT_EMITTING = 1, PARTICLE_EMITTING_TYPE_NUMBER = 2;
    public final int HARD_AREA = 0, SOFT_AREA = 1, DOT = 2, SPAWN_AREA_NUMBER = 3;
    public final int IN_BOUNDS = 0, SPAWN_BOUNDS_NUMBER = 1;
    public final int TO_DOT = 0, CONE_DIRECTION = 1, OUT_OF_CENTRE = 2;
    private final float SECOND = 1;
    private float timePassed = 0;

    public ParticleSystem(float pps, float speed, float gravityComplient, float lifeLength, float scale, ArrayList<Vector3f> colors) {
        this.pps = pps;
        this.averageSpeed = speed;
        this.gravityComplient = gravityComplient;
        this.averageLifeLength = lifeLength;
        this.averageScale = scale;
        this.colors = colors;
    }

    public void generateParticles() {
        if (particleEmittingType == PULSE) {
            if (timePassed > SECOND / particlePulsesASecond) {
                for (int i = 0; i < pps; i++) {
                    emitParticle();
                }
                timePassed = 0;
                return;
            }
            timePassed += DisplayManager.getFrameTimeSeconds();
        } else if (particleEmittingType == CONSTANT_EMITTING) {
            float particlesToCreate = pps * DisplayManager.getFrameTimeSeconds();
            int count = (int) Math.floor(particlesToCreate);
            for (int i = 0; i < count; i++) {
                emitParticle();
            }
            float partialParticle = particlesToCreate % 1;
            if (Math.random() < partialParticle) {
                emitParticle();
            }
        }
    }

    private void emitParticle() {
        Vector3f velocity = new Vector3f();
        Vector3f particlePos = generatePositionInBounds();
        float lifeLength = generateDeviation(averageLifeLength, lifeError);
        if (this.directionType == TO_DOT) {
            velocity.x = pullCenter.x - particlePos.x;
            velocity.y = pullCenter.y - particlePos.y;
            velocity.z = pullCenter.z - particlePos.z;
            float velocityLength = (float) Math.sqrt(velocity.x * velocity.x + velocity.y * velocity.y + velocity.z * velocity.z);

            velocity.normalise();
            velocity.scale(velocityLength / lifeLength);
        }
        else if (this.directionType == CONE_DIRECTION && !direction.equals(new Vector3f(0, 0, 0))) {
            float cosAngle = (float) Math.cos(directionError);
            Random random = new Random();
            float theta = (float) (random.nextFloat() * 2f * Math.PI);
            float z = cosAngle + (random.nextFloat() * (1 - cosAngle));
            float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
            float x = (float) (rootOneMinusZSquared * Math.cos(theta));
            float y = (float) (rootOneMinusZSquared * Math.sin(theta));

            Vector4f overallDirection = new Vector4f(x, y, z, 1);
            if (direction.x != 0 || direction.y != 0 || (direction.z != 1 && direction.z != -1)) {
                Vector3f rotateAxis = Vector3f.cross(direction, new Vector3f(0, 0, 1), null);
                rotateAxis.normalise();
                float rotateAngle = (float) Math.acos(Vector3f.dot(direction, new Vector3f(0, 0, 1)));
                Matrix4f rotationMatrix = new Matrix4f();
                rotationMatrix.rotate(-rotateAngle, rotateAxis);
                Matrix4f.transform(rotationMatrix, overallDirection, overallDirection);
            } else if (direction.z == -1) {
                overallDirection.z *= -1;
            }

            velocity = new Vector3f(overallDirection);
            velocity.normalise();
            velocity.scale(generateDeviation(averageSpeed, speedError));
        }
        else if (this.directionType == OUT_OF_CENTRE || direction.equals(new Vector3f(0, 0, 0))) {
            float theta = (float) (random.nextFloat() * 2f * Math.PI);
            float z = (random.nextFloat() * 2) - 1;
            float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
            float x = (float) (rootOneMinusZSquared * Math.cos(theta));
            float y = (float) (rootOneMinusZSquared * Math.sin(theta));

            velocity = new Vector3f(x, y, z);
            velocity.normalise();
            velocity.scale(generateDeviation(averageSpeed, speedError));
        }
        float scale = generateDeviation(averageScale, scaleError);
        new Particle(new Vector3f(particlePos), velocity, gravityComplient, lifeLength, generateRotation(), scale, colors);
    }

    private Vector3f generatePositionInBounds() {
        float x = 0, y = 0, z = 0;
        //Параллелепипедная форма
        if (spawnAreaType == HARD_AREA) {
            if (spawnBounds == IN_BOUNDS) {
                x = random.nextFloat() * (positiveBounds.x - negativeBounds.x) + negativeBounds.x + systemCenter.x;
                y = random.nextFloat() * (positiveBounds.y - negativeBounds.y) + negativeBounds.y + systemCenter.y;
                z = random.nextFloat() * (positiveBounds.z - negativeBounds.z) + negativeBounds.z + systemCenter.z;
            }
        }
        //Эллипсоидовидная форма
        else if (spawnAreaType == SOFT_AREA) {
            float theta = random.nextFloat() * (float) Math.PI;
            float phi = random.nextFloat() * 2 * (float) Math.PI;
            float a = 0, b = 0, c = 0;
            if (spawnBounds == IN_BOUNDS) {
                a = random.nextFloat() * (positiveBounds.x - negativeBounds.x) + negativeBounds.x + systemCenter.x;
                b = random.nextFloat() * (positiveBounds.y - negativeBounds.y) + negativeBounds.y + systemCenter.y;
                c = random.nextFloat() * (positiveBounds.z - negativeBounds.z) + negativeBounds.z + systemCenter.z;
            }
            x = (float) (a * Math.sin(theta) * Math.cos(phi));
            y = (float) (b * Math.sin(theta) * Math.sin(phi));
            z = (float) (c * Math.cos(theta));
        }
        //Появление из точки
        else if (spawnAreaType == DOT) {
            x = systemCenter.x;
            y = systemCenter.y;
            z = systemCenter.z;
        }
        return new Vector3f(x, y, z);
    }

    private float generateDeviation(float average, float errorMargin) {
        float offset = (random.nextFloat() - 0.5f) * 2f * errorMargin;
        return average + offset;
    }

    private float generateRotation() {
        if (randomRotation) {
            return random.nextFloat() * 360f;
        } else {
            return 0;
        }
    }

    //Устанавливает свойства частицы
    public void setParticle(float pps, float speed, float gravityComplient, float lifeLength, float scale,
                            ArrayList<Vector3f> colors) {
        this.pps = pps;
        this.averageSpeed = speed;
        this.gravityComplient = gravityComplient;
        this.averageLifeLength = lifeLength;
        this.averageScale = scale;
        this.colors = colors;
    }

    //Устанавливает погрешность и направление движения частицы
    public void setUtils(Vector3f direction, float directionError, boolean randomRotation, float speedError,
                         float lifeError, float scaleError, float particlePulsesASecond,
                         Vector3f systemCenter, Vector3f positiveBounds, Vector3f negativeBounds, Vector3f pullCenter,
                         int particleEmittingType, int spawnAreaType, int spawnBounds, int directionType)
    {
        this.direction = new Vector3f(direction);
        this.directionError = (float) (directionError * Math.PI);
        this.randomRotation = randomRotation;
        this.speedError = speedError * averageSpeed;
        this.lifeError = lifeError * averageLifeLength;
        this.scaleError = scaleError * averageScale;
        this.particlePulsesASecond = particlePulsesASecond;
        this.systemCenter = systemCenter;
        this.positiveBounds = positiveBounds;
        this.negativeBounds = negativeBounds;
        this.pullCenter = pullCenter;
        this.particleEmittingType = particleEmittingType;
        this.spawnAreaType = spawnAreaType;
        this.spawnBounds = spawnBounds;
        this.directionType = directionType;
    }
}