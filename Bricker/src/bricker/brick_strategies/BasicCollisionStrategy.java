package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;



public class BasicCollisionStrategy implements CollisionStrategy {
    private BrickerGameManager gameManager;
    public BasicCollisionStrategy(BrickerGameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        gameManager.removeGameObject(gameObject1);
    }
}
