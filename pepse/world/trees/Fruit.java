package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.world.IsJump;
import pepse.world.RemoveAndCreate;
import java.awt.*;

/**
 * This class represents a fruit game object. It can change color when the player jumps
 * and revert to its default color when the player lands.
 */
public class Fruit extends GameObject {
    private final RemoveAndCreate<GameObject> removeAndCreate;
    private final IsJump<Boolean> isJump;
    private Color curColor;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     */
    public Fruit(Vector2 topLeftCorner, IsJump<Boolean> isJump, RemoveAndCreate<GameObject> removeAndCreate) {
        super(topLeftCorner, Vector2.ONES.mult(Constants.BLOCK_SIZE),
                new OvalRenderable(ColorSupplier.approximateColor(Constants.FRUIT_DEAFULT_COLOR)));
        this.removeAndCreate = removeAndCreate;
        this.isJump = isJump;
        curColor = Constants.FRUIT_DEAFULT_COLOR;
    }
    /**
     * This method is called whenever the fruit object collides with another game object.
     * In this case, it triggers a recreation of the fruit at its current position.
     *
     * @param other The other game object that collided with this fruit.
     * @param collision Information about the collision.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Fruit fruit = new Fruit(getTopLeftCorner(), isJump, removeAndCreate);
        removeAndCreate.removeAndCreate(this, fruit);
    }
    /**
     * This method is called every frame to update the game object. It checks if the player
     * is jumping and changes the fruit's color accordingly.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isJump.isJump().equals(true)){
            curColor = Constants.FRUIT_CAHANGED_COLOR;
            renderer().setRenderable(
                    new OvalRenderable(ColorSupplier.approximateColor(curColor)));
        }
        else if (curColor.equals(Constants.FRUIT_CAHANGED_COLOR)){
            curColor = Constants.FRUIT_DEAFULT_COLOR;
            renderer().setRenderable(
                    new OvalRenderable(ColorSupplier.approximateColor(curColor)));
        }
    }
}
