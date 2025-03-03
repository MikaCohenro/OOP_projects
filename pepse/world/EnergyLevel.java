package pepse.world;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.Constants;
/**
 * This class represents the energy level component of the avatar. It keeps track of the
 * current energy level and displays it on the screen using a TextRenderer object. The
 * avatar can use energy to perform actions like jumping and running.
 */
public class EnergyLevel extends GameObject {
    private double curEnergy;
    private final TextRenderable renderer;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public EnergyLevel(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        curEnergy = Constants.MAX_ENERGY_LEVEL;
        this.renderer = renderable;
    }
    /**
     * This method attempts to update the current energy level by the specified amount.
     * It enforces a minimum and maximum energy level and updates the displayed value
     * if the update is successful.
     *
     * @param energy The amount of energy to add (positive) or remove (negative).
     * @return True if the energy level was updated successfully, false otherwise.
     */
    public boolean updateEnergy(double energy){
        double energyToUpdate = Math.min(energy+curEnergy, Constants.MAX_ENERGY_LEVEL);
        if (energyToUpdate >= Constants.MIN_ENERGY_LEVEL){
            curEnergy = energyToUpdate;
            renderer.setString(((Double)curEnergy).toString());
            return true;
        }
        return false;
    }
}
