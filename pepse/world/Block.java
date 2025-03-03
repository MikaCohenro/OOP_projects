package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;

/**
 * Represents a single block .
 */
public class Block extends GameObject{
    /**
     * default size of a single block.
     */

    /**
     * Constructor of a block
     *
     * @param topLeftCorner The location of the top-left corner of the created block.
     * @param renderable    A renderable to render as the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(Constants.BLOCK_SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}