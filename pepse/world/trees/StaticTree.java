package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.world.IsJump;
import java.awt.*;
import java.util.Random;

/**
 * This class represents a static tree game object. It can temporarily change color
 * to a random brown shade when the player jumps and revert to its default color when the player lands.
 */
class StaticTree extends GameObject {
    private final IsJump<Boolean> isJump;
    private boolean jumping;
    private Color curColor;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     */
    public StaticTree(Vector2 topLeftCorner, IsJump<Boolean> isJump) {
        super(topLeftCorner, new Vector2(Constants.BLOCK_SIZE, Constants.TREE_HEIGHT),
                new RectangleRenderable(ColorSupplier.approximateColor(Constants.TREE_DEFAULT_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.isJump = isJump;
        curColor = Constants.TREE_DEFAULT_COLOR;
        jumping = false;
    }
    /**
     * This method is called every frame to update the game object. It checks if the player
     * is jumping and changes the tree's color accordingly.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (isJump.isJump().equals(true) && !jumping){
            jumping = true;
            Random random = new Random();
            int change = random.nextInt(Constants.MIN_COLOR_CHANGE, Constants.MAX_COLOR_CHANGE);
            curColor =new Color(
                    Math.max(Constants.COLOR_INDICES[0]-change,0),
                    Math.max(Constants.COLOR_INDICES[1]-change,0),
                    Math.max(Constants.COLOR_INDICES[2]-change,0));
            renderer().setRenderable(
                    new RectangleRenderable(ColorSupplier.approximateColor(curColor)));
        }
        if (!curColor.equals(Constants.TREE_DEFAULT_COLOR) && isJump.isJump().equals(false)){
            jumping = false;
            curColor = Constants.TREE_DEFAULT_COLOR;
            renderer().setRenderable(
                    new RectangleRenderable(ColorSupplier.approximateColor(curColor)));
        }
    }
}
