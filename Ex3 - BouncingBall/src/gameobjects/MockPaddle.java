package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * Repels the ball against the bricks.
 * MockPaddle wont be instantiated more than once every time
 * after 3 hits with the ball it will disappear
 * @author Tamuz Gitler
 */
public class MockPaddle extends Paddle {

    //================ private constants ==============

    private final int MOVEMENT_SPEED = 300;
    private final int SPACE = 5;



    public static boolean isInstantiated;

    //================ fields =========================

    private final GameObjectCollection gameObjectCollection;
    private final Vector2 dimensions;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private final int minDistanceFromEdge;
    private int numCollisionsToDisappear;

    //================ constructor ================

    /**
     * Constructor
     * @param topLeftCorner Position of the object, in window coordinates (pixels)
     *                     Note that (0,0) is the top-left corner of the window
     * @param dimensions  Width and height in window coordinates
     * @param renderable The renderable representing the object. Can be null, in which case
     * @param inputListener listener object for user input
     * @param windowDimensions dimensions of game window
     * @param gameObjectCollection global game object collection managed by game manager
     * @param minDistanceFromEdge border for paddle movement
     * @param numCollisionsToDisappear  number of collision allowed before disappear this obj.\
     */
    public MockPaddle(danogl.util.Vector2 topLeftCorner,
                      danogl.util.Vector2 dimensions,
                      danogl.gui.rendering.Renderable renderable,
                      danogl.gui.UserInputListener inputListener,
                      danogl.util.Vector2 windowDimensions,
                      danogl.collisions.GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge, int numCollisionsToDisappear){
        super(topLeftCorner, dimensions , renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.dimensions = dimensions;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }
    //================ public methods ================

    /**
     * defines the action when MockPaddle hits another object.
     * @param other GameObject with which a collision occurred.
     * @param collision Information regarding this collision. A reasonable elastic behavior can be achieved
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.numCollisionsToDisappear --;

        if (this.numCollisionsToDisappear == 0){
            this.gameObjectCollection.removeGameObject(this);
            isInstantiated = false;
        }
    }

    /**
     * Overrides update, checks if user pressed a key to move the paddle.
     * if he did pressed, paddle will move to the wanted direction, without getting out of boundaries.
     * note - Mock Paddle will never reach the border
     * @param deltaTime time between updates. For internal use by game engine. You do not need to call this method
     *                  yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
        if (getTopLeftCorner().x() < this.minDistanceFromEdge + SPACE) {
            this.setCenter(new Vector2(this.dimensions.x()/2 + this.minDistanceFromEdge + SPACE,
                    windowDimensions.y()/2 ));
        }
        if(getTopLeftCorner().x() > windowDimensions.x() - this.minDistanceFromEdge -
                getDimensions().x() - SPACE){
            this.setCenter(new Vector2(windowDimensions.x() - this.dimensions.x()/2 -
                    this.minDistanceFromEdge - SPACE, windowDimensions.y()/2 ));
        }
    }
}
