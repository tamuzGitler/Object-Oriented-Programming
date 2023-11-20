/**
 * This class builds a new Renderer.
 * @author Tamuz Gitler
 */
public class RendererFactory {

    //================ public methods ================

    /**
     * builds a renderer instance
     * @param rendererType renderer name to build
     * @return new renderer
     */
    public Renderer buildRenderer(String rendererType) {
        switch (rendererType) {
            case "console":
                return new ConsoleRenderer();
            case "none":
                return new VoidRenderer();
            default:
                return null;
        }
    }
}
