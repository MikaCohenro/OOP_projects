package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.Constants;

import java.awt.*;
import java.awt.event.KeyEvent;
/**
 * This class represents the player-controlled avatar in the game world. The avatar can move left,
 * right, jump, and interact with objects in the environment. It also has an energy level
 * that affects its abilities.
 */
public class Avatar extends GameObject{
    private static final  String[] IDLE_IMAGES = {"idle_0.png", "idle_1.png", "idle_2.png", "idle_3.png"};
    private static final  String[] JUMP_IMAGES = {"jump_0.png", "jump_1.png", "jump_2.png", "jump_3.png"};
    private static final  String[] RUN_IMAGES = {"run_0.png", "run_1.png", "run_2.png", "run_3.png",
                                                "run_4.png", "run_5.png"};
    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y =  -400;
    private static final float GRAVITY = 300;
    private State state;
    private final EnergyLevel energyLevel;
    private final ImageReader imageReader;
    private boolean isOnAir;

    /**
     *  represents the different states of the avatar
     */
    private enum State {IDLE, RUN_LEFT, RUN_RIGHT, JUMP}

    private final UserInputListener inputListener;
    /**
     * Creates a new avatar object.
     *
     * @param pos         The initial position of the avatar in window coordinates.
     * @param inputListener  A reference to the user input listener for handling keyboard input.
     * @param imageReader  A reference to the image reader for loading avatar animations.
     * @param energyLevel  A reference to the energy level component of the avatar.
     */
    public Avatar(Vector2 pos,
                  UserInputListener inputListener,
                  ImageReader imageReader, EnergyLevel energyLevel){
        super(pos, Vector2.ONES.mult(50), imageReader.readImage(IDLE_IMAGES[0], true));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        state = State.IDLE;
        this.energyLevel = energyLevel;
        this.imageReader = imageReader;
        this.isOnAir = false;

    }
    /**
     * Checks if the avatar is currently jumping (in the air)- callback function with the functional
     * interface IsJump.
     *
     * @return True if the avatar is jumping, false otherwise.
     */
    public boolean isJump() {
        return isOnAir;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if ((other.getTag().equals(Constants.FRUIT))){
            energyLevel.updateEnergy(Constants.FRUIT_ENERGY);
        }
        if (other.getTag().equals(Constants.GROUND)||other.getTag().equals(Constants.TREE)){
            isOnAir = false;
        }
    }

    /**
     * method that updates avatars movement and animation
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = runningMove(0);
        transform().setVelocityX(xVel);
        if(inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0) {
            if (energyLevel.updateEnergy(Constants.JUMP_ENERGY)) {
                transform().setVelocityY(VELOCITY_Y);
                isOnAir = true;
                state = State.JUMP;
            }
        }
        if (getVelocity().equals(Vector2.ZERO)) {
            state = State.IDLE;
            energyLevel.updateEnergy(Constants.IDLE_ENERGY);
        }
        if (getVelocity().y() < 0){isOnAir =true;}
        setAnimation();
    }
    private float runningMove(float xVel) {
        // Left
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energyLevel.updateEnergy(Constants.RUN_ENERGY)){
                xVel -= VELOCITY_X;
                state =State.RUN_LEFT;
        }
        //right
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energyLevel.updateEnergy(Constants.RUN_ENERGY)) {
                xVel += VELOCITY_X;
                state = State.RUN_RIGHT;
        }
        transform().setVelocityX(xVel);
        return xVel;
    }
    private void setAnimation(){
        if (state == State.IDLE){
            this.renderer().setRenderable(new AnimationRenderable(IDLE_IMAGES, this.imageReader,
                    true, 1));
        }
        if (state == State.RUN_RIGHT){
            this.renderer().setRenderable(new AnimationRenderable(RUN_IMAGES, this.imageReader,
                    true, 1));
            renderer().setIsFlippedHorizontally(false);
        }
        if (state == State.RUN_LEFT){
            this.renderer().setRenderable(new AnimationRenderable(RUN_IMAGES, this.imageReader,
                    true, 1));
            renderer().setIsFlippedHorizontally(true);
        }
        if (state == State.JUMP){
            this.renderer().setRenderable(new AnimationRenderable(JUMP_IMAGES, this.imageReader,
                    true, 1));
        }
    }
}

