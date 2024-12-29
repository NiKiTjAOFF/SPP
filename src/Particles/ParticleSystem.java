package Particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import renderEngine.DisplayManager;

import java.util.Random;

public class ParticleSystem {

    private float pps, averageSpeed, gravityComplient, averageLifeLength, averageScale;
    private Vector3f startColor, endColor;
    private float speedError, lifeError, scaleError = 0, directionError = 0;
    private Vector3f direction;
    private boolean randomRotation = false;
    private Vector3f positiveBounds, negativeBounds;
    private Random random = new Random();
    public final int PULSE = 0, CONSTANT_EMITTING = 1;
    public final int HARD_AREA = 0, SOFT_AREA = 1, DOT = 2;
    private final float SECOND = 1;
    private float timePassed = 0;

    public ParticleSystem(float pps, float speed, float gravityComplient, float lifeLength, float scale,
                          Vector3f startColor, Vector3f endColor) {
        this.pps = pps;
        this.averageSpeed = speed;
        this.gravityComplient = gravityComplient;
        this.averageLifeLength = lifeLength;
        this.averageScale = scale;
        this.startColor = startColor;
        this.endColor = endColor;
    }



    public void generateParticles(Vector3f systemCenter, Vector3f positiveBounds, Vector3f negativeBounds,
                                  int particleEmittingType, int particleSpawnArea) {
        if(particleEmittingType == PULSE) {
            if(timePassed > SECOND) {
                for (int i = 0; i < pps; i++) {
                    emitParticle(systemCenter, positiveBounds, negativeBounds, particleSpawnArea);
                }
                timePassed = 0;
                return;
            }
            timePassed += DisplayManager.getFrameTimeSeconds();
        }
        else if (particleEmittingType == CONSTANT_EMITTING) {
            float particlesToCreate = pps * DisplayManager.getFrameTimeSeconds();
            int count = (int) Math.floor(particlesToCreate);
            for (int i = 0; i < count; i++) {
                emitParticle(systemCenter, positiveBounds, negativeBounds, particleSpawnArea);
            }
            float partialParticle = particlesToCreate % 1;
            if (Math.random() < partialParticle) {
                emitParticle(systemCenter, positiveBounds, negativeBounds, particleSpawnArea);
            }
        }
    }

    private void emitParticle(Vector3f center, Vector3f positiveBounds, Vector3f negativeBounds, int particleSpawnArea) {
        Vector3f velocity = null;
        if(direction!=null && !direction.equals(new Vector3f(0, 0, 0))){
            velocity = generateRandomUnitVectorWithinCone(direction, directionError);
        }else{
            velocity = generateRandomUnitVector();
        }
        velocity.normalise();
        velocity.scale(generateDeviation(averageSpeed, speedError));
        float scale = generateDeviation(averageScale, scaleError);
        float lifeLength = generateDeviation(averageLifeLength, lifeError);
        if (particleSpawnArea == HARD_AREA) {
            Vector3f particlePos = generatePositionInHardBounds(center, positiveBounds, negativeBounds);
            new Particle(new Vector3f(particlePos), velocity, gravityComplient, lifeLength, generateRotation(), scale,
                    startColor, endColor);
        }
        if (particleSpawnArea == SOFT_AREA) {
            Vector3f particlePos = generatePositionInSoftBounds(center, positiveBounds, negativeBounds);
            new Particle(new Vector3f(particlePos), velocity, gravityComplient, lifeLength, generateRotation(), scale,
                    startColor, endColor);
        }
        else if (particleSpawnArea == DOT) {
            new Particle(new Vector3f(center), velocity, gravityComplient, lifeLength, generateRotation(), scale,
                    startColor, endColor);
        }

    }

    //Параллелепипедная форма
    private Vector3f generatePositionInHardBounds(Vector3f center, Vector3f positiveBounds, Vector3f negativeBounds) {
        float x = random.nextFloat() * (positiveBounds.x - negativeBounds.x) + negativeBounds.x + center.x;
        float y = random.nextFloat() * (positiveBounds.y - negativeBounds.y) + negativeBounds.y + center.y;
        float z = random.nextFloat() * (positiveBounds.z - negativeBounds.z) + negativeBounds.z + center.z;
        return new Vector3f(x, y, z);
    }

    //Эллипсоидовидная форма
    private Vector3f generatePositionInSoftBounds(Vector3f center, Vector3f positiveBounds, Vector3f negativeBounds) {
        float theta = random.nextFloat() * (float) Math.PI;
        float phi = random.nextFloat() * 2 * (float) Math.PI;

        float a = random.nextFloat() * (positiveBounds.x - negativeBounds.x) + negativeBounds.x + center.x;
        float b = random.nextFloat() * (positiveBounds.y - negativeBounds.y) + negativeBounds.y + center.y;
        float c = random.nextFloat() * (positiveBounds.z - negativeBounds.z) + negativeBounds.z + center.z;

        float x = (float) (a * Math.sin(theta) * Math.cos(phi));
        float y = (float) (b * Math.sin(theta) * Math.sin(phi));
        float z = (float) (c * Math.cos(theta));

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

    //Направление частиц в границах конуса
    private static Vector3f generateRandomUnitVectorWithinCone(Vector3f coneDirection, float angle) {
        float cosAngle = (float) Math.cos(angle);
        Random random = new Random();
        float theta = (float) (random.nextFloat() * 2f * Math.PI);
        float z = cosAngle + (random.nextFloat() * (1 - cosAngle));
        float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
        float x = (float) (rootOneMinusZSquared * Math.cos(theta));
        float y = (float) (rootOneMinusZSquared * Math.sin(theta));


        Vector4f direction = new Vector4f(x, y, z, 1);
        if (coneDirection.x != 0 || coneDirection.y != 0 || (coneDirection.z != 1 && coneDirection.z != -1)) {
            Vector3f rotateAxis = Vector3f.cross(coneDirection, new Vector3f(0, 0, 1), null);
            rotateAxis.normalise();
            float rotateAngle = (float) Math.acos(Vector3f.dot(coneDirection, new Vector3f(0, 0, 1)));
            Matrix4f rotationMatrix = new Matrix4f();
            rotationMatrix.rotate(-rotateAngle, rotateAxis);
            Matrix4f.transform(rotationMatrix, direction, direction);
        } else if (coneDirection.z == -1) {
            direction.z *= -1;
        }
        return new Vector3f(direction);
    }

    //Рандомное направление частиц
    private Vector3f generateRandomUnitVector() {
        float theta = (float) (random.nextFloat() * 2f * Math.PI);
        float z = (random.nextFloat() * 2) - 1;
        float rootOneMinusZSquared = (float) Math.sqrt(1 - z * z);
        float x = (float) (rootOneMinusZSquared * Math.cos(theta));
        float y = (float) (rootOneMinusZSquared * Math.sin(theta));
        return new Vector3f(x, y, z);
    }

    //Устанавливает свойства частицы
    public void setParticle(float pps, float speed, float gravityComplient, float lifeLength, float scale,
                       Vector3f startColor, Vector3f endColor) {
        this.pps = pps;
        this.averageSpeed = speed;
        this.gravityComplient = gravityComplient;
        this.averageLifeLength = lifeLength;
        this.averageScale = scale;
        this.startColor = startColor;
        this.endColor = endColor;
    }

    //Устанавливает погрешность и направление движения частицы
    public void setUtils(Vector3f direction, float directionError, boolean randomRotation, float speedError,
                         float lifeError, float scaleError) {
        this.direction = new Vector3f(direction);
        this.directionError = (float) (directionError * Math.PI);
        this.randomRotation = randomRotation;
        this.speedError = speedError * averageSpeed;
        this.lifeError = lifeError * averageLifeLength;
        this.scaleError = scaleError * averageScale;
    }
}