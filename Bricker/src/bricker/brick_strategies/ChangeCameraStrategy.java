package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;
import danogl.util.Counter;

public class ChangeCameraStrategy implements CollisionStrategy {
    private final BrickerGameManager gameManager;
    public ChangeCameraStrategy(BrickerGameManager gameManager){
        this.gameManager = gameManager;
    }
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (gameObject2.getTag().equals(BrickerGameManager.BALL) && gameManager.camera() == null){
            gameManager.setStrategyCamera();
        }
        gameManager.removeGameObject(gameObject1);
    }
}
