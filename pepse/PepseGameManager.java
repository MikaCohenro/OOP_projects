package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;

import java.util.ArrayList;
import java.util.List;

public class PepseGameManager extends GameManager {

    private Vector2 windowDimensions;
    private GameObjectCollection gameObjects;
    private int rightMostXWithGround;
    private int leftMostXWithGround;
    private Avatar avatar;
    private Terrain terrain;

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

    /**
     *
     *
     * @param imageReader      Contains a single method: readImage, which reads an image from disk.
     *                         See its documentation for help.
     * @param soundReader      Contains a single method: readSound, which reads a wav file from disk.
     *                         See its documentation for help.
     * @param inputListener    Contains a single method: isKeyPressed, which returns whether a given key is
     *                         currently pressed by the user or not. See its documentation.
     * @param windowController Contains an array of helpful, self-explanatory methods concerning the window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowDimensions = windowController.getWindowDimensions();
        this.gameObjects = gameObjects();
        rightMostXWithGround = (int) windowController.getWindowDimensions().x();
        leftMostXWithGround = 0;
        setGameFrame(windowController);
        setGamePlayer(imageReader, inputListener);
    }

    private void setGamePlayer(ImageReader imageReader, UserInputListener inputListener) {
        TextRenderable textRenderable = new TextRenderable(((Double) Constants.MAX_ENERGY_LEVEL).toString());
        EnergyLevel energyLevel = new EnergyLevel(Vector2.ZERO, new Vector2(40,40), textRenderable);
        gameObjects.addGameObject(energyLevel, Layer.BACKGROUND);
        Avatar avatar = new Avatar(Vector2.of(windowDimensions.x()-85,windowDimensions.y()-260),
                inputListener, imageReader, energyLevel);
        Flora flora = new Flora(avatar::isJump, terrain::groundHeightAt, this::removeAndCreate);
        ArrayList<GameObject> floras = flora.createInRange(10, (int)windowDimensions.x());
        for (GameObject object: floras){
            if (object.getTag().equals(Constants.TREE)){
                gameObjects.addGameObject(object, Layer.STATIC_OBJECTS);
            } else if (object.getTag().equals(Constants.LEAF)) {
                gameObjects.addGameObject(object, Layer.STATIC_OBJECTS+Constants.BLOCK_SIZE);

            } else if (object.getTag().equals(Constants.FRUIT)) {
                gameObjects.addGameObject(object, Layer.DEFAULT);
            }
        }
        gameObjects.addGameObject(avatar,Layer.DEFAULT);
        this.avatar = avatar;
    }

    private void setGameFrame(WindowController windowController) {
        windowController.setTargetFramerate(30);
        GameObject sky = Sky.create(windowDimensions);
        gameObjects().addGameObject(sky, Layer.BACKGROUND);
        terrain = new Terrain(windowController.getWindowDimensions(),0);
        terrain.createInRange(rightMostXWithGround,leftMostXWithGround);
        List<Block> blocks = terrain.createInRange(leftMostXWithGround, rightMostXWithGround);
        for(Block block: blocks){
            gameObjects.addGameObject(block, Layer.STATIC_OBJECTS);
        }
        GameObject night = Night.create(this.windowDimensions, Constants.CYCLE_LENGTH);
        gameObjects().addGameObject(night, Layer.BACKGROUND + 5);
        GameObject sun = Sun.create(this.windowDimensions, Constants.CYCLE_LENGTH);
        gameObjects().addGameObject(sun,Layer.BACKGROUND + 10);
        GameObject sunHalo = SunHalo.create(sun);
        gameObjects().addGameObject(sunHalo,Layer.BACKGROUND + 15);
    }

    public void removeAndCreate(GameObject object, GameObject newObject){
        gameObjects.removeGameObject(object);
        new ScheduledTask(avatar,Constants.GAME_CIRCLE_TIME,false,
                () -> gameObjects.addGameObject(newObject, Layer.DEFAULT));
    }

}
