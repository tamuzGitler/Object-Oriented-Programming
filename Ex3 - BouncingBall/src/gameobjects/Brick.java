package src.gameobjects;

import danogl.util.Counter;
import src.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;

/**
 * Brick is a gameObject that will be displayed on screen.
 * Brick redefines the behaviour when otherObj collides with a brick.
 * @author Tamuz Gitler
 */
public class Brick extends GameObject {
    private boolean brickCollided = false;

    //================ fields =========================

    private CollisionStrategy collisionStrategy;
    private Counter counter;

    //================ constructor ====================

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param counter of bricks in game
     */
    public Brick(danogl.util.Vector2 topLeftCorner,
          danogl.util.Vector2 dimensions,
          danogl.gui.rendering.Renderable renderable,
          CollisionStrategy collisionStrategy,
          danogl.util.Counter counter){
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.counter = counter;
    }

    //================ public methods =================

    /**
     * redefines the behaviour when otherObj collides with a brick
     * @param other GameObject with which a collision occurred.
     * @param collision Information regarding this collision. A reasonable elastic behavior can be achieved
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if(! this.brickCollided){
            collisionStrategy.onCollision(this,other, this.counter);
            this.brickCollided = true;
        }

    }
}
