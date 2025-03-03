package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.Constants;
import pepse.world.GroundHeight;
import pepse.world.IsJump;
import pepse.world.RemoveAndCreate;

import java.util.ArrayList;
import java.util.Random;
/**
 * This class manages the creation and placement of flora elements (trees, leaves and fruits) within
 * a specified range in the game world. It considers factors like ground height and randomness
 * to create a natural distribution.
 */
public class Flora {
    private final ArrayList<GameObject> treeObjects;
    private final IsJump<Boolean> isJump;
    private final GroundHeight<Float> groundHeight;
    private final RemoveAndCreate<GameObject> removeAndCreate;
    /**
     * Creates a new Flora object.
     *
     * @param isJump A callback function that checks if the player is currently jumping.
     * @param groundHeight A callback function that retrieves the ground height at a specific position.
     * @param removeAndCreate A callback function that can remove and create game objects.
     */
    public Flora(IsJump<Boolean> isJump, GroundHeight<Float> groundHeight,
                 RemoveAndCreate<GameObject> removeAndCreate){
        treeObjects = new ArrayList<>();
        this.isJump = isJump;
        this.groundHeight = groundHeight;
        this.removeAndCreate = removeAndCreate;
    }
    /**
     * Creates trees, leaves and fruits (flora) within a specified range (minX to maxX). The
     * placement and density are randomized to create a natural look. It considers the ground
     * height to position the flora elements.
     *
     * @param minX The minimum X position value for the range.
     * @param maxX The maximum X position value for the range.
     * @return An ArrayList containing all the created flora game objects (trees and leaves).
     */
    public ArrayList<GameObject> createInRange(int minX, int maxX){
        Random random= new Random();
        for (int i = minX; i < maxX; i += (maxX-minX)/ Constants.BLOCK_SIZE) {
            if (random.nextFloat()<= 0.15){
                StaticTree tree = new StaticTree(
                        new Vector2((float) i,
                                (groundHeight.groundHeightAt((float)i)-Constants.BLOCK_SIZE*4)), isJump);
                treeObjects.add(tree);
                tree.setTag(Constants.TREE);
                createLeafs(tree.getCenter().x(), random);
            }
        }
        return treeObjects;
    }

    private void createLeafs(float x, Random random){
        int floraSize = random.nextInt(Constants.FLORA_MIN_SIZE, Constants.FLORA_MAX_SIZE);
        float treeTop = groundHeight.groundHeightAt((x))-Constants.TREE_HEIGHT+Constants.BLOCK_SIZE;
        for (float i = x-floraSize; i < x + floraSize; i+=Constants.BLOCK_SIZE) {
            for (float j = treeTop-floraSize; j < treeTop+floraSize; j+=Constants.BLOCK_SIZE) {
                if (random.nextBoolean()){
                    Leaf leaf = new Leaf(new Vector2(i, j), isJump);
                    treeObjects.add(leaf);
                    leaf.setTag(Constants.LEAF);
                }
                if (random.nextFloat()<=0.15){
                    Fruit fruit = new Fruit(new Vector2(i, j), isJump, removeAndCreate);
                    treeObjects.add(fruit);
                    fruit.setTag(Constants.FRUIT);
                }
            }
        }
    }
}