package pepse.world;
/**
 * This functional interface represents a function that calculates the ground height
 * at a given position.
 *
 * @param <T> The type of the parameter and return value. This can be any type
 * representing a position (e.g., float, Vector2).
 */
@FunctionalInterface
public interface GroundHeight<T>{
    T groundHeightAt(T x);
}
