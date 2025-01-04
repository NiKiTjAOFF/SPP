package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private float distanceFromObject;
    private float angleAroundObject = 0;
    private boolean isRotationAroundObjectNeeded = true;
    private Vector3f cameraPosition = new Vector3f(0, 0, 0);
    private Vector3f objectPosition;
    private float pitch;
    private float yaw = 0;
    private float roll;
    private final float PITCH_LIMIT = 90;

    public Camera(Vector3f objectPosition, boolean isRotationAroundObjectNeeded) {
        this.objectPosition = objectPosition;
        this.distanceFromObject = objectPosition.z + 50;
        this.isRotationAroundObjectNeeded = isRotationAroundObjectNeeded;
    }

    public void move() {
        final float CAMERA_SPEED = 0.2f;
        final float MOUSE_CHANGE_RATE = 0.1f;
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            distanceFromObject -= CAMERA_SPEED;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            distanceFromObject += CAMERA_SPEED;
        }

        if(isRotationAroundObjectNeeded) {
            angleAroundObject += CAMERA_SPEED;
        }

        if(Mouse.isButtonDown(1)) {
            float angleChange = Mouse.getDX() * MOUSE_CHANGE_RATE;
            angleAroundObject += angleChange;
        }

        if(Mouse.isButtonDown(0)) {
            float pitchChange = Mouse.getDY() * MOUSE_CHANGE_RATE;
            pitch -= pitchChange;
            if(pitch > PITCH_LIMIT) {
                pitch = PITCH_LIMIT;
            }
            if(pitch < -PITCH_LIMIT) {
                pitch = -PITCH_LIMIT;
            }
        }

        float horizontalDistance = (float) (distanceFromObject * Math.cos(Math.toRadians(pitch)));
        float verticalDistance = (float) (distanceFromObject * Math.sin(Math.toRadians(pitch)));
        float offsetX = (float) (horizontalDistance * Math.sin(Math.toRadians(angleAroundObject)));
        float offsetZ = (float) (horizontalDistance * Math.cos(Math.toRadians(angleAroundObject)));
        cameraPosition.y = objectPosition.y + verticalDistance;
        cameraPosition.x = objectPosition.x - offsetX;
        cameraPosition.z = objectPosition.z - offsetZ;
        this.yaw = 180 - angleAroundObject;
    }
    public Vector3f getPosition() {
        return cameraPosition;
    }
    public float getPitch() {
        return pitch;
    }
    public float getYaw() {
        return yaw;
    }
    public float getRoll() {
        return roll;
    }
    public void setIsRotationAroundObjectNeeded(boolean isRotationAroundObjectNeeded) {
        this.isRotationAroundObjectNeeded = isRotationAroundObjectNeeded;
    }
}
