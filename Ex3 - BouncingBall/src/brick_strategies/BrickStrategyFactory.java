package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

/**
 * Factory class for creating Collision strategies
 * @author Tamuz Gitler
 */
public class BrickStrategyFactory extends java.lang.Object {


    //================ private constants ==============

    private static final int DOUBLE_BEHAVIOUR = 1;
    private static final int MAX_STRATEGY = 4;

    //================ fields ================

    private final GameObjectCollection gameObjectCollection;
    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    //================ constructor ================

    /**
     * Constructor
     *
     * @param gameObjectCollection gameObjectCollection global game object collection managed by game manager
     * @param gameManager          Bricker game mangaer
     * @param imageReader          an ImageReader instance for reading images from files for rendering of objects
     * @param soundReader          a SoundReader instance for reading soundclips from files for rendering event sounds.
     * @param inputListener        an InputListener instance for reading user input
     * @param windowController     controls visual rendering of the game window and object renderables.
     * @param windowDimensions     pixel dimensions for game window height x widt
     */
    public BrickStrategyFactory(danogl.collisions.GameObjectCollection gameObjectCollection,
                                BrickerGameManager gameManager,
                                danogl.gui.ImageReader imageReader,
                                danogl.gui.SoundReader soundReader,
                                danogl.gui.UserInputListener inputListener,
                                danogl.gui.WindowController windowController,
                                danogl.util.Vector2 windowDimensions) {
        this.gameObjectCollection = gameObjectCollection;
        this.gameManager = gameManager;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
    }

    //================ public methods ================

    /**
     * this function randomly chooses a number of Strategy's to applie when brick collides.
     *
     * @return strategy's
     */
    public CollisionStrategy getStrategy() {
        Random rand = new Random();
        int numOfStrategies = rand.nextInt(6); //number between 0-5

        CollisionStrategy collisionStrategy = new RemoveBrickStrategy(this.gameObjectCollection);
        if (numOfStrategies <= 4) { //chance of 5/6 to get one Strategy
            return chooseBehaviour(collisionStrategy, 0);
        } else { //chance of 1/6 to get double behaviour, numOfStrategies == 5
            int doubleStrategy = rand.nextInt(5);
            if (doubleStrategy <= 3) {
                return chooseBehaviour(chooseBehaviour(collisionStrategy, DOUBLE_BEHAVIOUR), DOUBLE_BEHAVIOUR);
            } else { //chance of 1/30 to get triple strategy
                return chooseBehaviour(chooseBehaviour(chooseBehaviour(collisionStrategy, DOUBLE_BEHAVIOUR), DOUBLE_BEHAVIOUR), DOUBLE_BEHAVIOUR); //99.966-100
            }
        }
    }
    //================ private methods ================

    /**
     * this function decorates collisionStrategy with new randomly strategy.
     * @param collisionStrategy defines the strategy that will occur when the otherObj collides a thisObj
     * @param min               index
     * @return decorated Strategy
     */
    private CollisionStrategy chooseBehaviour(CollisionStrategy collisionStrategy, int min) {
        Random rand = new Random();
        int choosenStrategy = rand.nextInt((MAX_STRATEGY - min) + 1) + min;
        switch (choosenStrategy) {
            case 0:
                return collisionStrategy; //normal RemoveBrickStrategy
            case 1:
                return new PuckStrategy(collisionStrategy,
                        this.imageReader,
                        this.soundReader);
            case 2:
                return new AddPaddleStrategy(
                        collisionStrategy,
                        this.imageReader,
                        this.inputListener,
                        this.windowDimensions);
            case 3:
                return new ChangeCameraStrategy(collisionStrategy,
                        this.windowController, this.gameManager);
            case 4:
                return new ChangePaceStrategy(collisionStrategy, this.windowController,
                        this.imageReader);
            default:
                return null;
        }
    }
}