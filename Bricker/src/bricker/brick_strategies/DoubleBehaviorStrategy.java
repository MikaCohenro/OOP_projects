package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

import java.util.Random;

public class DoubleBehaviorStrategy implements CollisionStrategy{
    private CollisionStrategy collisionStrategy1;
    private CollisionStrategy collisionStrategy2;
    private static int special_behaviors = 5;
    private BrickerGameManager gameManager;

    public DoubleBehaviorStrategy(BrickerGameManager gameManager){
        this.gameManager = gameManager;
    }
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        Random rand = new Random();
        collisionStrategy1 = gameManager.collisionStrategies[rand.nextInt(special_behaviors)];
        if (collisionStrategy1.getClass().equals(this.getClass()))
            special_behaviors --;
        collisionStrategy2 = gameManager.collisionStrategies[rand.nextInt(special_behaviors)];
        collisionStrategy1.onCollision(gameObject1, gameObject2);
        collisionStrategy2.onCollision(gameObject1, gameObject2);
        gameManager.removeGameObject(gameObject1);
    }
}
