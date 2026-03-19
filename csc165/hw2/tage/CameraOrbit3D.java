package tage;

import a2.MyGame;
import net.java.games.input.Component;
import net.java.games.input.Event;
import org.joml.Vector3f;
import tage.input.IInputManager;
import tage.input.InputManager;
import tage.input.action.AbstractInputAction;

/**
 * CameraOrbit3D is a camera that targets a specific GameObject and can orbit around that game object
 * It has 3 sub-classes which allow the user to adjust the Azimuth, Elevation and Radius
 * Default Keybinds are:
 *  Q and E for rotating the Camera Left and Right
 *  PAGEUP and PAGEUP for Evelating the camera around the GameObject
 *  And 1 and 2 are zoom-in and zoom-out
 */

public class CameraOrbit3D {
    private Engine engine;
    private Camera camera;
    private GameObject avatar;
    private float cameraAzimuth;
    private float cameraElevation;
    private float cameraRadius;
    private MyGame game;

    public CameraOrbit3D(Camera cam, GameObject av, Engine e, MyGame game) {
        engine = e;
        camera = cam;
        avatar = av;
        cameraAzimuth = 5.0f;
        cameraElevation = 20.0f;
        cameraRadius = 10.0f;
        this.game = game;
        setUpInputs();
        updateCameraPosition();
    }

    private void setUpInputs() {
        InputManager im = engine.getInputManager();

        OrbitAzimuthAction azimuthActionLeft = new OrbitAzimuthAction("LEFT", false);
        OrbitAzimuthAction azimuthActionRight = new OrbitAzimuthAction("RIGHT", false);

        OrbitAzimuthAction azimuthAction = new OrbitAzimuthAction("", true);

        OrbitElevationAction elevationActionUp = new OrbitElevationAction("UP", false);
        OrbitElevationAction elevationActionDown = new OrbitElevationAction("DOWN", false);

        OrbitElevationAction elevationAction = new OrbitElevationAction("", true);

        OrbitRadiusAction radiusActionIn = new OrbitRadiusAction("IN", false);
        OrbitRadiusAction radiusActionOut = new OrbitRadiusAction("OUT", false);

        im.associateActionWithAllKeyboards(Component.Identifier.Key.Q, azimuthActionLeft, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.E, azimuthActionRight, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        im.associateActionWithAllKeyboards(Component.Identifier.Key.UP, elevationActionUp, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key.DOWN, elevationActionDown, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        im.associateActionWithAllKeyboards(Component.Identifier.Key._1, radiusActionIn, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllKeyboards(Component.Identifier.Key._2, radiusActionOut, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

        im.associateActionWithAllGamepads(Component.Identifier.Axis.RX, azimuthAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
        im.associateActionWithAllGamepads(Component.Identifier.Axis.RY, elevationAction, IInputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

    }

    public void updateCameraPosition() {
        Vector3f avatarRot = avatar.getWorldForwardVector();
        double avatarAngle = Math.toDegrees((double) avatarRot.angleSigned(new Vector3f(0,0,-1), new Vector3f(0,1,0)));
        float totalAz = cameraAzimuth - (float) avatarAngle;

        double theta = Math.toRadians(totalAz);
        double phi = Math.toRadians(cameraElevation);

        float x = cameraRadius * (float) (Math.cos(phi) * Math.sin(theta));
        float y = cameraRadius * (float) (Math.sin(phi));
        float z = cameraRadius * (float) (Math.cos(phi) * Math.cos(theta));

        camera.setLocation(new Vector3f(x,y,z).add(avatar.getWorldLocation()));
        camera.lookAt(avatar);
    }

    private class OrbitAzimuthAction extends AbstractInputAction {
        private String direction;
        private boolean onController;

        public OrbitAzimuthAction(String dir, boolean cont) {
            direction = dir;
            onController = cont;
        }

        @Override
        public void performAction(float time, Event evt) {
            float rotAmount;
            if (evt.getValue() < -0.2) {
                rotAmount = -0.2f;
            } else {
                if(evt.getValue() > 0.2) {
                    rotAmount = 0.2f;
                } else {
                    rotAmount = 0.0f;
                }
            }

            if(!onController) {
                if(direction.equals("LEFT")) {
                    cameraAzimuth += -rotAmount;
                } else {
                    cameraAzimuth += rotAmount;
                }
            } else {
                cameraAzimuth += rotAmount;
            }

            cameraAzimuth = cameraAzimuth % 360;
            updateCameraPosition();
        }
    }

    private class OrbitElevationAction extends AbstractInputAction {
        private String direction;
        private boolean onController;

        public OrbitElevationAction(String dir, boolean cont) {
            direction = dir;
            onController = cont;
        }

        @Override
        public void performAction(float time, Event evt) {
            float rotAmount;
            if (evt.getValue() < -0.2) {
                rotAmount = -0.2f;
            } else {
                if(evt.getValue() > 0.2) {
                    rotAmount = 0.2f;
                } else {
                    rotAmount = 0.0f;
                }
            }

            if(!onController) {
                if(direction.equals("UP")) {
                    cameraElevation += rotAmount;
                } else {
                    cameraElevation += -rotAmount;
                }
            } else {
                cameraElevation += rotAmount;
            }

            cameraElevation = cameraElevation % 360;
            updateCameraPosition();
        }
    }

    private class OrbitRadiusAction extends AbstractInputAction {
        private String direction;
        private boolean onController;

        public OrbitRadiusAction(String dir, boolean cont) {
            direction = dir;
            onController = cont;
        }

        @Override
        public void performAction(float time, Event evt) {
            float rotAmount;
            if (evt.getValue() < -0.2) {
                rotAmount = -0.02f;
            } else {
                if (evt.getValue() > 0.2) {
                    rotAmount = 0.02f;
                } else {
                    rotAmount = 0.0f;
                }
            }

            if(!onController) {
                if (direction.equals("IN")) {
                    cameraRadius += -rotAmount;
                } else {
                    cameraRadius += rotAmount;
                }
            } else {
                cameraRadius += rotAmount;
            }

            cameraRadius = cameraRadius % 360;
            updateCameraPosition();
        }
    }

}
