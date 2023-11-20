package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Changes camera focus from ground to ball until ball collides NUM_BALL_COLLISIONS_TO_TURN_OFF times.
 * @author Tamuz Gitler
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator{



    //================ fields =========================

    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private  BallCollisionCountdownAgent ballCollisionCountdownAgent = null;

    //================ constructor ====================

    /**
     * Constructor
     * @param toBeDecorated Collision strategy object to be decorated
     * @param windowController controls visual rendering of the game window and object renderables.
     * @param gameManager Bricker game mangaer
     */
    ChangeCameraStrategy(CollisionStrategy toBeDecorated, danogl.gui.WindowController windowController, BrickerGameManager gameManager){
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    //================ public methods =================

    /**
     * changes camera when thisObj collides with otherObj
     * @param otherObj GameObject with which a collision occurred
     * @param counter global brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Ball ball = getBall(); //no need to check if ball is null because he must exist
        if(gameManager.getCamera() == null){
            this.ballCollisionCountdownAgent = new
                    BallCollisionCountdownAgent(ball, this, ball.getCollisionCount());
            getGameObjectCollection().addGameObject(this.ballCollisionCountdownAgent);
            gameManager.setCamera(
                    new Camera(
                            ball, 			//object to follow
                            Vector2.ZERO, 	//follow the center of the object
                            windowController.getWindowDimensions().mult(1.2f),  //widen the frame a bit
                            windowController.getWindowDimensions()   //share the window dimensions
                    )
            );
        }
    }

    /**
     * Turns off the camera
     */
    public void turnOffCameraChange(){
        gameManager.setCamera(null);
        this.getGameObjectCollection().removeGameObject(this.ballCollisionCountdownAgent);
    }

    //================ private methods ================
    /**
     * Finds ball in GameObjectCollection
     * @return ball
     */
    private Ball getBall() {
        for (GameObject gameObj: getGameObjectCollection()){
            if( gameObj instanceof Ball && !(gameObj instanceof Puck)){
                return (Ball) gameObj;
            }
        }
        return null;
    }
}
