package src.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Puck class inherits from Ball.
 * @author Tamuz Gitler
 */
public class Puck extends Ball {

    //================ constructor ================

    /**
     * Constructor
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param collisionSound sound to play when ball hits another object
     */
    public Puck(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable, collisionSound);
    }


}