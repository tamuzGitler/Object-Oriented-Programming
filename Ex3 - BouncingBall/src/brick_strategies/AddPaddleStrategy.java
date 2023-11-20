package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;
import src.gameobjects.Paddle;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Introduces extra paddle to game window which remains until colliding
 * NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE with other game objects.
 * @author Tamuz Gitler
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator{

    //================ private constants ==============

    private static final int PADDLE_WIDTH = 100;
    private static final int PADDLE_HEIGHT = 15;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    private final int NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE = 3;
    private static final String PADDLE_PATH = "assets/paddle.png";


    //================ fields =========================

    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    //================ constructor ====================

    /**
     * Constructor
     * @param toBeDecorated Collision strategy object to be decorated
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects
     * @param inputListener an InputListener instance for reading user input
     * @param windowDimensions pixel dimensions for game window height x widt
     */
    AddPaddleStrategy(CollisionStrategy toBeDecorated,
                      danogl.gui.ImageReader imageReader,
                      danogl.gui.UserInputListener inputListener,
                      danogl.util.Vector2 windowDimensions){

        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;

    }

    //================ public methods =================

    /**
     *  adds a Mock Paddle  when thisObj collides with otherObj
     * @param otherObj GameObject with which a collision occurred
     * @param counter global brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if(!MockPaddle.isInstantiated){
            Renderable paddleImage = this.imageReader.readImage(PADDLE_PATH, true);
            Paddle mockPaddle = new MockPaddle(Vector2.ZERO,
                    new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT),
                    paddleImage,
                    inputListener,
                    windowDimensions,
                    this.getGameObjectCollection(),
                    MIN_DISTANCE_FROM_SCREEN_EDGE,
                    NUM_COLLISIONS_FOR_MOCK_PADDLE_DISAPPEARANCE
            );
            mockPaddle.setCenter(new Vector2(windowDimensions.x()/2 , windowDimensions.y()/2 ));
            this.getGameObjectCollection().addGameObject(mockPaddle);
            MockPaddle.isInstantiated = true;

        }
    }
}
