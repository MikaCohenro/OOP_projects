/**
 * Factory for creating different types of renderers.
 */
public class RendererFactory {

    /**
     * Default constructor for the RendererFactory.
     */
    public RendererFactory() {}

    /**
     * Builds and returns a renderer based on the specified type.
     *
     * @param type The type of renderer to build ("console" or "none").
     * @param size The size parameter required for specific renderer types.
     * @return A renderer object based on the specified type, or null if the type is not recognized.
     */
    public Renderer buildRenderer(String type, int size) {
        if ("console".equalsIgnoreCase(type)) {
            return new ConsoleRenderer(size);
        } else if ("none".equalsIgnoreCase(type)) {
            return new VoidRenderer();
        } else{
            System.out.println("Choose a renderer, and start again.\nPlease choose one of the following: " +
                    "[console, none]");
            return null;
        }
    }
}
