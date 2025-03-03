package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The class represents the Night mode in a game world. It creates a game object that overlays
 * the entire window and gradually transitions its opacity to simulate a night effect.
 */
public class Night {
    private static final String NIGHT_TAG = "Night";
    private static final float INITIAL_OPACITY = 0;
    private static final float MIDNIGHT_OPACITY = 0.5f;
    /**
     * Creates a night game object that overlays the entire window and transitions its opacity
     * over a specified cycle length to simulate a night effect.
     *
     * @param windowDimensions The dimensions of the game window as a Vector2 object.
     * @param cycleLength The total time (in seconds) for the night effect to complete a cycle
     *                     from full day to full night and back.
     * @return A newly created GameObject representing the night mode.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength){
        GameObject night = new GameObject(Vector2.ZERO,
                windowDimensions,
                new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(NIGHT_TAG);
        new Transition<>(night,
                night.renderer()::setOpaqueness,
                INITIAL_OPACITY,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength / 2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        return night;
    }
}
