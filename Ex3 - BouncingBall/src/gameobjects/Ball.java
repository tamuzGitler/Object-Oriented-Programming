package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.util.Random;

/**
 * Ball class inherits from gameObject and defines the wanted behaviour when the ball hits another gameObject.
 * @author Tamuz Gitler
 */
public class Ball extends GameObject {

    //================ private constants ================

    private static final int BALL_SPEED = 150;

    //================ fields =========================

    private final Sound collisionSound;
    private int collisionCount = 0;

    //================ constructor ====================

    /**
     * Constructor
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param collisionSound sound to play when ball hits another object
     *
     */
    public Ball(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.setStartingVelocity();
    }

    //================ public methods =================

    /**
     * defines the action when ball hits another object,
     * @param other GameObject with which a collision occurred.
     * @param collision Information regarding this collision. A reasonable elastic behavior can be achieved
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        this.collisionCount ++;
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
    }

    /**
     * number of times ball collided with an object since start of game.
     * @return collisionCount
     */
    public int getCollisionCount(){
        return this.collisionCount;
    }

    /**
     * sets starting ball velocity
     */
    private void setStartingVelocity() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random random = new Random();
        if(random.nextBoolean()){
            ballVelX *= -1;
        }
        if(random.nextBoolean()){
            ballVelY *= -1;
        }
        this.setVelocity(new Vector2(ballVelX, ballVelY));
    }
}
