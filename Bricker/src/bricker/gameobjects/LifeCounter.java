package bricker.gameobjects;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class LifeCounter extends GameObject{
    private final int initialLosses;
    private int losses;
    private final static float GAP = 5;
    private final static int MAX_LIFE = 4;
    private GameObject[] hearts;
    private TextRenderable numericRenderer;
    private final Renderable renderable;
    private BrickerGameManager gameManager;
    private GameObject numericLife;
    private int curLife;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     */
    public LifeCounter(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                        BrickerGameManager brickerGameManager,
                       int initialLosses) {
        super(topLeftCorner, dimensions, renderable);
        this.renderable = renderable;
        this.gameManager = brickerGameManager;
        float[] leftCorner = {2*GAP,2*GAP};
        curLife = initialLosses;
        hearts = new GameObject[MAX_LIFE];
        numericRenderer = new TextRenderable(((Integer)curLife).toString());
        setColor();
        numericLife = new GameObject(new Vector2(MAX_LIFE*(dimensions.x()+2*GAP), initialLosses),
                                    dimensions,
                                    numericRenderer);
        gameManager.addObject(numericLife, Layer.BACKGROUND);
        for (int lose = 0; lose < initialLosses; lose++) {
            createHeart(dimensions, leftCorner, lose);
            leftCorner[0] += dimensions.x()+GAP;
        }
        this.initialLosses = initialLosses;
    }

    private void createHeart(Vector2 dimensions, float[] leftCorner, int lose) {
        GameObject lifeGraphics =  new Heart(new Vector2(leftCorner[0], leftCorner[1]),
                dimensions,
                renderable);
        hearts[lose] = lifeGraphics;
        gameManager.addObject(lifeGraphics, Layer.BACKGROUND);
    }

    private void setColor(){
        if (curLife >= 3)
            numericRenderer.setColor(Color.GREEN);
        else if (curLife == 2)
            numericRenderer.setColor(Color.YELLOW);
        else
            numericRenderer.setColor(Color.RED);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        losses++;
        curLife --;
        gameManager.removeGameObject(hearts[curLife], Layer.BACKGROUND);
        numericRenderer.setString(((Integer)curLife).toString());
        setColor();
        hearts[curLife] = null;
    }
    public void addLife(){
        if (curLife < 4){
            float[] topLeftCorner = {2*GAP + (BrickerGameManager.HEART_GRAPHICS_SIZE.y() + GAP)*curLife,
                    2*GAP};
            createHeart(BrickerGameManager.HEART_GRAPHICS_SIZE, topLeftCorner, curLife);
            losses --;
            curLife ++;
            numericRenderer.setString(((Integer)curLife).toString());
            setColor();
        }
    }
    public boolean isGameEnd(){
        return losses == initialLosses;
    }
}
