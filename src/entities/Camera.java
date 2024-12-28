package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

public class Camera {
    private Vector3f position = new Vector3f(0, 0, 0);
    private float pitch;
    private float yaw;
    private float roll;
    private final float PITCH_LIMIT = 90;

    public Camera() {}

    public void move() {
        final float CAMERA_SPEED = 0.2f;
        if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.z -= CAMERA_SPEED;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.z += CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.x += CAMERA_SPEED;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.x -= CAMERA_SPEED;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            position.y -= CAMERA_SPEED;
        }
        else if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            position.y += CAMERA_SPEED;
        }

//        float yawChange = Mouse.getDX() * 0.1f;
//        yaw += yawChange;
        if(Mouse.isButtonDown(0)) {
            float pitchChange = Mouse.getDY() * 0.1f;
            pitch += pitchChange;
            if(pitch > PITCH_LIMIT) {
                pitch = PITCH_LIMIT;
            }
            if(pitch < -PITCH_LIMIT) {
                pitch = PITCH_LIMIT;
            }
        }


    }
    public Vector3f getPosition() {
        return position;
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
}
