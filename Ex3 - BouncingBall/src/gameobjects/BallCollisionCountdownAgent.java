package src.gameobjects;

import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * An object of this class is instantiated on collision of ball with a brick with
 * a change camera strategy. It checks ball's collision counter every frame,
 * and once the it finds the ball has collided countDownValue times since instantiation,
 * it calls the strategy to reset the camera to normal.
 * @author Tamuz Gitler
 */
public class BallCollisionCountdownAgent extends danogl.GameObject{

    //================ fields =========================

    private final Ball ball;
    private final ChangeCameraStrategy owner;
    private final int countDownValue;

    //================ constructor ====================

    /**
     * Constructor.
     * @param ball Ball object whose collisions are to be counted
     * @param owner Object asking for countdown notification
     * @param countDownValue  Number of ball collisions. Notify caller object that the ball collided
     *                       countDownValue times since instantiation
     */
    public BallCollisionCountdownAgent(Ball ball, ChangeCameraStrategy owner, int countDownValue) {
        super(Vector2.ZERO,Vector2.ZERO,null);
        this.ball = ball;
        this.owner = owner;
        this.countDownValue = countDownValue;
    }

    //================ public methods =================

    /**
     * Overrides update, turn off the camera after 4 ball hits.
     * @param deltaTime time between updates. For internal use by game engine.
     *        yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(ball.getCollisionCount() == this.countDownValue + 4){
            owner.turnOffCameraChange();
        }


    }
}
