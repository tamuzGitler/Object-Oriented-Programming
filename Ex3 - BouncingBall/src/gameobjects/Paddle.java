package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * One of the main game objects. Repels the ball against the bricks.
 * @author Tamuz Gitler
 */
public class Paddle extends GameObject {

    //================ private constants ================

    private static int MOVEMENT_SPEED = 300;
    private final int minDistanceFromEdge;
    private static final int PADDLE_HEIGHT = 30;
    private final Vector2 dimensions;


    //================ fields ================

    private UserInputListener inputListener;
    private Vector2 windowDimensions;


    /**
     * Constructor.
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     * @param dimensions Width and height in window coordinates
     * @param renderable The renderable representing the object
     * @param inputListener an InputListener instance for reading user input.
     * @param windowDimensions pixel dimensions for game window height x width
     * @param minDistanceFromEdge border for paddle movement
     */
    public Paddle(danogl.util.Vector2 topLeftCorner,
                  danogl.util.Vector2 dimensions,
                  danogl.gui.rendering.Renderable renderable,
                  danogl.gui.UserInputListener inputListener,
                  danogl.util.Vector2 windowDimensions,
                  int minDistanceFromEdge){

        super(topLeftCorner, dimensions, renderable);
        this.dimensions = dimensions;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    //================ public methods =================

    /**
     * Overrides update, checks if user pressed a key to move the paddle.
     * if he did pressed, paddle will move to the wanted direction, without getting out of boundaries.
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

        if (getTopLeftCorner().x() < this.minDistanceFromEdge) {
            this.setCenter(new Vector2(this.dimensions.x()/2 + this.minDistanceFromEdge,
                    windowDimensions.y() - PADDLE_HEIGHT));
        }
       if(getTopLeftCorner().x() > windowDimensions.x() - this.minDistanceFromEdge -
                        getDimensions().x()){
           this.setCenter(new Vector2(windowDimensions.x() - this.dimensions.x()/2 - this.minDistanceFromEdge,
                   windowDimensions.y() - PADDLE_HEIGHT));
       }
    }
}



