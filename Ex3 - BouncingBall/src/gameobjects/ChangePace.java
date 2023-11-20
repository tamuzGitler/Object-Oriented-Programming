package src.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * this class is a specific status definer for changing the pace of the game when this obejct
 * collides with paddle.
 * @author Tamuz Gitler
 */
public class ChangePace extends StatusDefiner {

    private final WindowController windowController;
    private final float changeSeterSpeed;

    /**
     * Constructor
     *
     * @param topLeftCorner        Position of the object, in window coordinates (pixels).
     *                             Note that (0,0) is the top-left corner of the window.
     * @param dimensions           Width and height in window coordinates.
     * @param renderable           The renderable representing the object. Can be null, in which case
     * @param gameObjectCollection global game object collection managed by game manager
     * @param windowController     controls visual rendering of the game window and object renderables
     * @param seterSpeed           sets the gameObject speed
     */
    public ChangePace(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, GameObjectCollection gameObjectCollection, WindowController windowController, float seterSpeed) {
        super(topLeftCorner, dimensions, renderable, gameObjectCollection, windowController, seterSpeed);
        this.windowController = windowController;
        this.changeSeterSpeed = seterSpeed;
    }

    @Override
    public void behaviourWhenCollides() {
        this.windowController.setTimeScale(this.changeSeterSpeed);
    }
}
