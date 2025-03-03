package pepse.world;

/**
 * This functional interface represents a function that can remove and create a game object.
 *
 * @param <T> The type of the game object.
 */
@FunctionalInterface
public interface RemoveAndCreate <T>{
    /**
     * Removes an existing game object and creates a new one.
     *
     * @param object The game object to be removed.
     * @param newObject The new game object to be created.
     */
    void removeAndCreate(T object, T newObject);
}
