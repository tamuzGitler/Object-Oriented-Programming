package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * This class helps Strategies classes.
 * it defined the behaviour of this gameObject when it hits the paddle
 * @author Tamuz Gitler
 */
public abstract class StatusDefiner extends GameObject {

    //================ fields =========================

    private GameObjectCollection gameObjectCollection;


    //================ constructor ====================
    /**
     * Constructor
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param gameObjectCollection global game object collection managed by game manager
     * @param windowController controls visual rendering of the game window and object renderables
     * @param seterSpeed sets the gameObject speed
     *
     */
    public StatusDefiner(Vector2 topLeftCorner,
                         Vector2 dimensions,
                         Renderable renderable,
                         GameObjectCollection gameObjectCollection,
                         WindowController windowController,
                         float seterSpeed) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjectCollection = gameObjectCollection;

    }

    //================ public methods =================

    /**
     * checks if this should collide with other
     * @param other object to check if it should collide with this
     * @return
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return (other instanceof Paddle);
    }

    /**
     * defines the action when this Seter collides with another object.
     * @param other GameObject with which a collision occurred.
     * @param collision Information regarding this collision. A reasonable elastic behavior can be achieved
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        //checks if seter collided with Paddle
        if (this.shouldCollideWith(other)){
            behaviourWhenCollides();
            this.gameObjectCollection.removeGameObject(this);
        }
    }

    /**
     * class that use this abstract class has to implement the specialBehaviour when this gameObject collides
     */
    public abstract void behaviourWhenCollides();

}
