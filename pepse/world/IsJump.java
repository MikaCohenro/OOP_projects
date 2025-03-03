package pepse.world;
/**
 * This functional interface represents a function that checks if the player is currently
 * jumping.
 *
 * @param <T> The type of the return value. This can be any type that can represent
 * a jumping state (e.g., boolean, JumpState enum).
 */
@FunctionalInterface
public interface IsJump<T>{
    T isJump();
}
