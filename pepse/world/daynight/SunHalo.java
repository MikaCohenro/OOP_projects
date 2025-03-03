package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;
/**
 * This class represents a halo effect around the Sun game object. It creates a slightly
 * larger, transparent yellow oval that is always centered on the sun to create a glowing
 * halo effect.
 */
public class SunHalo {

    private static final String SUN_HALO_TAG = "sun_halo";
    private static final Color HALO_COLOR = new Color(255, 255, 0, 40);
    private static final float HALO_SIZE = (float)1.15;
    /**
     * Creates a halo game object that is slightly larger and transparent yellow than the
     * provided sun object. The halo remains centered on the sun at all times.
     *
     * @param sun A reference to the Sun game object this halo will be attached to.
     * @return A newly created GameObject representing the sun halo.
     */
    public static GameObject create(GameObject sun){

        GameObject sunHalo = new GameObject(Vector2.ZERO, sun.getDimensions().mult(HALO_SIZE),
                                    new OvalRenderable(HALO_COLOR));
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(SUN_HALO_TAG);
        sunHalo.addComponent((deltaTime)-> sunHalo.setCenter(sun.getCenter()));
        return sunHalo;
    }
}
