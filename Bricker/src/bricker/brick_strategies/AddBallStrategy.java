package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;


public class AddBallStrategy implements CollisionStrategy{
    private static final float PUCKS_TO_ADD = 2;
    private BrickerGameManager gameManager;
    public AddBallStrategy(BrickerGameManager gameManager){
        this.gameManager = gameManager;
    }

    @Override
    public void onCollision(GameObject gameObject1, GameObject gameObject2) {
        for (int i = 0; i <PUCKS_TO_ADD; i++) {
            gameManager.createPuck(gameObject1);
        }
        gameManager.removeGameObject(gameObject1);
    }
}
