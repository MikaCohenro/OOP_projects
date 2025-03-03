package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

public class AddPaddleStrategy implements CollisionStrategy {
    private static final int POSSIBLE_LIFE = 5;
    private static final String BALL = "BALL";
    private static final String PUCK = "PUCK";
    private BrickerGameManager gameManager;
    private Counter life = new Counter(POSSIBLE_LIFE);
    public AddPaddleStrategy(BrickerGameManager gameManager){
        this.gameManager = gameManager;
    }
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (life.value() == 5){
            gameManager.createStrategyPaddle(this);
            gameManager.removeGameObject(gameObject1);
        }
        if (gameObject2.getTag().equals(BALL) || gameObject2.getTag().equals(PUCK)){
            life.decrement();
        }
        if (life.value() == 0){
            gameManager.removeGameObject(gameObject1);
        }
    }
}
