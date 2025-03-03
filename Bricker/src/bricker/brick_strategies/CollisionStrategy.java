package bricker.brick_strategies;

import bricker.main.BrickerGameManager;
import danogl.GameObject;

public interface CollisionStrategy {
    void onCollision(GameObject gameObject1, GameObject gameObject2);
}
