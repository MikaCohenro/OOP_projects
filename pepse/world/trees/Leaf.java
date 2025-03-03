package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.world.IsJump;

import java.util.Random;
/**
 * This class represents a leaf game object. It can flutter and change size to simulate
 * wind effects when the player jumps.
 */
class Leaf extends GameObject {
    private final IsJump<Boolean> isJump;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     */
    public Leaf(Vector2 topLeftCorner, IsJump<Boolean> isJump) {
        super(topLeftCorner, Vector2.ONES.mult(Constants.BLOCK_SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(Constants.LEAF_COLOR)));
        Random random = new Random();
        new ScheduledTask(this, random.nextFloat(),
                true,
                () ->  new Transition<>(this,
                        (Float angle)-> this.renderer().setRenderableAngle(angle),
                        Constants.INITIAL_TRANSITION, Constants.FINAL_TRANSITION,
                        Transition.LINEAR_INTERPOLATOR_FLOAT, 5,
                        Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null));
        new ScheduledTask(this, random.nextFloat(),
                true,
                () -> new Transition<>(this, this::setDimensions, this.getDimensions(),
                        this.getDimensions().mult(Constants.LEAF_FACTOR), Transition.LINEAR_INTERPOLATOR_VECTOR,
                        Constants.LEAF_TRANSITION_TIME, Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                        null));
        this.isJump = isJump;
    }
    /**
     * This method is called every frame to update the game object. It checks if the player
     * is jumping. If so, it creates a temporary animation to circle the leaf in 90 degrees
     * while jumping. Otherwise, the update behavior falls back to regular class.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isJump.isJump().equals(true)){
            new Transition<>(this,
                    (Float angle)-> this.renderer().setRenderableAngle(angle+Constants.LEAF_ANGEL),
                    Constants.INITIAL_TRANSITION, Constants.FINAL_TRANSITION,
                    Transition.LINEAR_INTERPOLATOR_FLOAT, 0.1f,
                    Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
        }
    }
}