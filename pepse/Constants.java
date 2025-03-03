package pepse;

import java.awt.*;

public class Constants {
    public static final int CYCLE_LENGTH = 30;
    public static final double MAX_ENERGY_LEVEL = 100;
    public static final double MIN_ENERGY_LEVEL = 0;
    public static final double IDLE_ENERGY = 1;
    public static final double RUN_ENERGY = -0.5;
    public static final double JUMP_ENERGY = -10;
    public static final double FRUIT_ENERGY = 10;
    public static final int BLOCK_SIZE = 30;
    public static final int TREE_HEIGHT = 150;
    public static final int FLORA_MAX_SIZE = 100;
    public static final int FLORA_MIN_SIZE = 40;
    public static final int GAME_CIRCLE_TIME = 30;
    public static final String TREE = "tree";
    public static final String LEAF = "leaf";
    public static final String FRUIT = "fruit";
    public static final String GROUND = "ground";
    public static final Color TREE_DEFAULT_COLOR = new Color(100, 50, 20);
    public static final int[] COLOR_INDICES = {100, 50, 20};
    public static final int MAX_COLOR_CHANGE = 60;
    public static final int MIN_COLOR_CHANGE = 1;
    public static final Color LEAF_COLOR = new  Color(50, 200, 30);
    public static final Color FRUIT_DEAFULT_COLOR = new  Color(200, 150, 0);
    public static final Color FRUIT_CAHANGED_COLOR = new  Color(255, 0, 0);
    public static final float INITIAL_TRANSITION = 0f;
    public static final float FINAL_TRANSITION = 90f;
    public static final int LEAF_TRANSITION_TIME = 5;
    public static final float LEAF_FACTOR = 1;
    public static final float LEAF_ANGEL = 90;
}
