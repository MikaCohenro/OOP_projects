package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
/**
 * This class represents the Sun in a game world. It creates a game object with an oval shape
 * and yellow color, and animates its position in a circular path over a specified cycle length
 * to simulate the sun's movement in the sky.
 */
public class Sun {
    private static final String SUN_TAG = "sun";

    /**
     * Creates a sun game object with an oval shape and yellow color. The object animates its
     * position in a circular path over a specified cycle length to simulate the sun's movement.
     *
     * @param windowDimensions The dimensions of the game window as a Vector2 object.
     * @param cycleLength The total time (in seconds) for the sun to complete a circular path.
     * @return A newly created GameObject representing the sun.
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength){
        Vector2 initialSunSize = Vector2.of(windowDimensions.x() * 0.15f, windowDimensions.x() * 0.15f);
        Vector2 initialSunCenter = Vector2.of(windowDimensions.x() / 2, windowDimensions.y() / 4);
        GameObject sun = new GameObject(initialSunCenter ,initialSunSize, new OvalRenderable(Color.YELLOW));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        Vector2 cycleCenter = Vector2.of(windowDimensions.x() / 2, windowDimensions.y() / 2);
        new Transition<>(sun,
                        (Float angle) ->
                        sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                        (float)0,
                        (float)360,
                        Transition.LINEAR_INTERPOLATOR_FLOAT,
                        cycleLength,
                        Transition.TransitionType.TRANSITION_LOOP,
                null);
        return sun;
    }
}
