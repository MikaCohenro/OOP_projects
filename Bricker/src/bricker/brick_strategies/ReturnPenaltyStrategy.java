package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public class ReturnPenaltyStrategy implements CollisionStrategy{
    private final BrickerGameManager gameManager;

    public ReturnPenaltyStrategy(BrickerGameManager gameManager){
        this.gameManager = gameManager;
    }
    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        if (!gameObject1.getTag().equals(BrickerGameManager.HEART)) {
            gameManager.dropHeart(gameObject1, this);
        }
        else
            gameManager.addLife();
        gameManager.removeGameObject(gameObject1);
    }
}
