package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.util.List;
import java.util.ArrayList;

import java.awt.*;
/**
 * This class represents the terrain of the game world. It generates a procedural ground
 * using noise and creates block objects to represent the ground visually.
 */
public class Terrain{

    private final float groundHeightAtX0;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123,74);

    private final NoiseGenerator noiseGenerator;
    private final Vector2 windowDimensions;

    /**
     * Creates a new terrain object.
     *
     * @param windowDimensions The dimensions of the game window in window coordinates.
     * @param seed             The seed for the noise generator, used to create different terrain variations.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = (windowDimensions.y() * ((float) 0.75));
        this.noiseGenerator = new NoiseGenerator(seed, (int) this.groundHeightAtX0);
        this.windowDimensions = windowDimensions;

    }

    /**
     * Calculates the ground height at a specific X position based on noise and the initial ground height.
     *
     * @param x The X position in window coordinates.
     * @return The ground height at the specified X position.
     */
    public float groundHeightAt(float x) {
        float noise = (float) noiseGenerator.noise(x, Constants.BLOCK_SIZE *7);
        return groundHeightAtX0 + noise;
    }

    /**
     * Generates ground blocks within a specified X-axis range.
     *
     * @param minX The minimum X position (inclusive) in window coordinates.
     * @param maxX The maximum X position (inclusive) in window coordinates.
     * @return A list of generated Block objects representing the ground.
     */
    public List<Block> createInRange(int minX, int maxX) {
        Renderable blockRenderable =
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
        List<Block> blocks = new ArrayList<>();
        for (int x = minX; x <= maxX; x++) {
            for (int y = (int) windowDimensions.y(); y >= groundHeightAt(x * Constants.BLOCK_SIZE) ;
                 y -= Constants.BLOCK_SIZE) {
                Vector2 blockPosition = new Vector2(x * Constants.BLOCK_SIZE, y);
                Block block = new Block(blockPosition, blockRenderable);
                block.setTag(Constants.GROUND);
                blocks.add(block);
            }
        }
        return blocks;
    }
}
