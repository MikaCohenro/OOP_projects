package bricker.main;
import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    public static final int DEFUALT_BRICK_IN_ROW = 8;
    public static final int DEFAULT_BRICK_ROWS = 7;
    private static final float WINDOW_HEIGHT = 700;
    private static final float WINDOW_WIDTH = 500;
    private static final float BALL_SPEED = 300;
    private static final float HEART_SPEED = 100;
    private static final int BALL_RADIUS = 30;
    private static final float PUCK_RATIO = 0.75f;
    private static final int NEGATIVE_DIRECTION = -1;
    private static final int BRICK_HEIGHT = 15;
    private static final int TOP_WALL_HEIGHT = 30;
    private static final int WALL_WIDTH = 15;
    private static final int SPACE_BETWEEN_BRICKS = 3;
    private static final int PADDLE_HEIGHT = 20;
    private static final int PADDLE_WIDTH = 200;
    private static final int BRICKS_OPTIONS = 10;
    private static final int MAX_LOSSES = 3;
    public final static Vector2 HEART_GRAPHICS_SIZE = new Vector2(20, 20);
    private static final String BRICK_IMAGE = "assets/brick.png";
    private static final String PADDLE_IMAGE = "assets/paddle.png";
    private static final String HEART_IMAGE = "assets/heart.png";
    private static final String BACKGROUND_IMAGE = "assets/DARK_BG2_small.jpeg";
    private static final String BALL_IMAGE = "assets/ball.png";
    private static final String BALL_SOUND = "assets/blop_cut_silenced.wav";
    private static final String PUCK_IMAGE = "assets/mockBall.png";
    public static final String HEART = "HEART";
    public static final String MAIN_PADDLE = "MAIN_PADDLE";
    private final int brickInRow;
    private final int brickRows;
    private Ball ball;
    private Vector2 windowDimensions;
    private WindowController windowController;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private UserInputListener inputListener;
    private LifeCounter lifeCounter;
    private static final String WIN = "You win! ";
    private static final String LOSE = "You Lose! ";
    private static final String REPEAT = " Play again?";
    public static final String BALL = "BALL";
    private static final String PUCK = "PUCK";
    private int brickNum;
    private GameObjectCollection bricks;
    private GameObjectCollection pucks;
    private GameObject addedPaddle = null;
    private int cameraCounter = 0;
    public CollisionStrategy[] collisionStrategies;
    private int basicStrategyLocation;
    private int doubleBehaviorLocation;
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions){
        super(windowTitle, windowDimensions);
        this.brickInRow = DEFUALT_BRICK_IN_ROW;
        this.brickRows = DEFAULT_BRICK_ROWS;
        brickNum = brickInRow*brickRows;
    }

    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickInRow, int brickRows){
        super(windowTitle, windowDimensions);
        this.brickInRow = brickInRow;
        this.brickRows = brickRows;
        brickNum = brickInRow*brickRows;
    }

    private void initializeStrategies() {
        doubleBehaviorLocation = 4;
        basicStrategyLocation = 5;
        collisionStrategies = new CollisionStrategy[]{
                new AddBallStrategy(this),
                new AddPaddleStrategy(this),
                new ChangeCameraStrategy(this),
                new ReturnPenaltyStrategy(this),
                new DoubleBehaviorStrategy(this),
                new BasicCollisionStrategy(this)
        };
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        checkForGameEnd(deltaTime);
        removeObjectsOutside();
        checkCameraCounter();
    }

    private void checkCameraCounter() {
        if (ball.getCollisionCounter() - cameraCounter == 4){
            setCamera(null);
        }
    }

    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController){
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        initialVaribels(imageReader, soundReader, inputListener, windowController);
        setBackground();
        createBall();
        createLifeCounter();
        creatPaddle(new Vector2(windowDimensions.x() / 2, windowDimensions.y()-PADDLE_HEIGHT));
        // left wall
        createWall(Vector2.ZERO, new Vector2(WALL_WIDTH, windowDimensions.y()));
        // right wall
        createWall(new Vector2(windowDimensions.x()-WALL_WIDTH,0), new Vector2(WALL_WIDTH, windowDimensions.y()));
        // top wall
        createWall(new Vector2(0, 0), new Vector2(windowDimensions.x(), WALL_WIDTH));
        initializeStrategies();
        createBricks();
    }

    private void initialVaribels(ImageReader imageReader, SoundReader soundReader,
                                 UserInputListener inputListener, WindowController windowController) {
        windowDimensions = windowController.getWindowDimensions();
        this.windowController = windowController;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        brickNum = brickInRow*brickRows;
        bricks = new GameObjectCollection(windowController.messages());
        pucks = new GameObjectCollection(windowController.messages());
    }

    private void setBackground(){
        Renderable backgroundImage = imageReader.readImage(BACKGROUND_IMAGE, false);
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects().addGameObject(background, Layer.BACKGROUND);
    }


    private void createBall(){
        Renderable ballImage =
                imageReader.readImage(BALL_IMAGE, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND);
        Ball ball =  new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage,
                collisionSound);
        setBall(ball);
        this.ball = ball;
        ball.setTag(BALL);
        gameObjects().addGameObject(ball);
    }
    private void createLifeCounter() {
        Renderable heartImage =
                imageReader.readImage(HEART_IMAGE, true);
        lifeCounter =  new LifeCounter(Vector2.ZERO,  HEART_GRAPHICS_SIZE, heartImage,this, MAX_LOSSES);
    }

    private void creatPaddle(Vector2 startLocation){
        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE, true);
        GameObject paddle = new Paddle(Vector2.ZERO,
                new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                paddleImage, inputListener, windowDimensions);
        paddle.setCenter(startLocation);
        gameObjects().addGameObject(paddle);
        paddle.setTag(MAIN_PADDLE);
    }

    public void createStrategyPaddle(CollisionStrategy collisionStrategy){
        if (addedPaddle == null) {
            Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE, true);
            GameObject paddle = new Paddle(Vector2.ZERO,
                    new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                    paddleImage, inputListener, windowDimensions, collisionStrategy);
            paddle.setCenter(new Vector2((float) windowDimensions.x() / 2, (float) windowDimensions.y() / 2));
            gameObjects().addGameObject(paddle);
            addedPaddle = paddle;
        }
    }

    private void createWall(Vector2 topLeftCorner, Vector2 dimensions){
        GameObject wall = new GameObject(topLeftCorner, dimensions, null);
        gameObjects().addGameObject(wall);
    }

    private void createBricks(){
        Renderable brickImage = imageReader.readImage(BRICK_IMAGE, false);
        float brickWidth = (windowDimensions.x()-2*WALL_WIDTH-SPACE_BETWEEN_BRICKS*(brickInRow+1))/brickInRow;
        Vector2 brickDimensions = new Vector2(brickWidth, BRICK_HEIGHT);
        float[] brickTopLeftCorner = {(float)WALL_WIDTH+SPACE_BETWEEN_BRICKS,
                (float)WALL_WIDTH+SPACE_BETWEEN_BRICKS+TOP_WALL_HEIGHT};
        Random rand = new Random();
        for (int row = 0; row < brickRows; row++) {
            for (int brickCount = 0; brickCount < brickInRow; brickCount++) {
                CollisionStrategy collisionStrategy =
                        collisionStrategies[Math.min(rand.nextInt(BRICKS_OPTIONS), basicStrategyLocation)];
                if (collisionStrategy == collisionStrategies[doubleBehaviorLocation]){
                    collisionStrategies[doubleBehaviorLocation] = new DoubleBehaviorStrategy(this);
                }
                initializeStrategies();
                GameObject brick = new Brick(new Vector2(brickTopLeftCorner[0], brickTopLeftCorner[1]),
                        brickDimensions,
                        brickImage,
                        collisionStrategy);
                brickTopLeftCorner[0] += brickWidth + SPACE_BETWEEN_BRICKS;
                gameObjects().addGameObject(brick);
                bricks.addGameObject(brick);
            }
            brickTopLeftCorner[0]= (float)WALL_WIDTH+SPACE_BETWEEN_BRICKS;
            brickTopLeftCorner[1] += BRICK_HEIGHT+SPACE_BETWEEN_BRICKS;
        }
    }
    public void addObject(GameObject gameObject, int layer){
        gameObjects().addGameObject(gameObject, layer);
    }

    public void removeGameObject(GameObject gameObject, int layer){
        if (bricks.removeGameObject(gameObject)){
            brickNum -=1;
        }
        gameObjects().removeGameObject(gameObject, layer);
    }

    private void removeObjectsOutside() {
        for (GameObject puck: pucks){
            Vector2 objectCenter = puck.getCenter();
            if (objectCenter.y() > windowDimensions.y()){
                gameObjects().removeGameObject(puck);
                pucks.removeGameObject(puck);
            }
        }
    }

    private void checkForGameEnd(float deltaTime) {
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (brickNum == 0 ||
                inputListener.isKeyPressed(KeyEvent.VK_W)){
            // we win
            prompt = WIN;
        }
        if (ballHeight > windowDimensions.y()){
            // we lost
            lifeCounter.update(deltaTime);
            if (lifeCounter.isGameEnd())
                prompt = LOSE;
            else
                createBall();
        }
        if (!prompt.isEmpty()){
            prompt+=REPEAT;
            if (windowController.openYesNoDialog(prompt))
                windowController.resetGame();
            else
                windowController.closeWindow();
        }
    }

    public void createPuck(GameObject brick){
        Renderable ballImage =
                imageReader.readImage(PUCK_IMAGE, true);
        Sound collisionSound = soundReader.readSound(BALL_SOUND);
        Ball puck =  new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS*PUCK_RATIO, BALL_RADIUS*PUCK_RATIO),
                ballImage,
                collisionSound);
        setBall(puck);
        puck.setCenter(brick.getCenter());
        puck.setTag(PUCK);
        gameObjects().addGameObject(puck);
    }

    public void dropHeart(GameObject brick, CollisionStrategy collisionStrategy){
        Renderable heartImage =
                imageReader.readImage(HEART_IMAGE, true);
        GameObject heart = new Heart(Vector2.ZERO,
                HEART_GRAPHICS_SIZE,heartImage, collisionStrategy);
        heart.setCenter(brick.getCenter());
        gameObjects().addGameObject(heart);
        heart.setVelocity(Vector2.DOWN.mult(HEART_SPEED));
        heart.setTag(HEART);
    }

    public void addLife(){
        lifeCounter.addLife();
    }

    private void setBall(Ball ball){
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();
        if (rand.nextBoolean()){
            ballVelX *= NEGATIVE_DIRECTION;
        }
        if (rand.nextBoolean()){
            ballVelY *= NEGATIVE_DIRECTION;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
        ball.setCenter(windowDimensions.mult(0.5f));
    }

    public void removeGameObject(GameObject gameObject){
        if (bricks.removeGameObject(gameObject)){
            brickNum -=1;
        }
        if (gameObject == addedPaddle){
            addedPaddle = null;
        }
        gameObjects().removeGameObject(gameObject);
    }

    public void setStrategyCamera() {
        super.setCamera(new Camera(
                        ball, //object to follow
                        Vector2.ZERO, //follow the center of the object
                        windowController.getWindowDimensions().mult(1.2f), //widen the frame a bit
                        windowController.getWindowDimensions() //share the window dimensions
                )
        );
        cameraCounter = ball.getCollisionCounter();
    }

    public static void main(String[] args) {
        new BrickerGameManager("name",
                new Vector2(WINDOW_HEIGHT, WINDOW_WIDTH)).run();
    }
}