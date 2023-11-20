package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Puck;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Introduces several pucks instead of brick once removed.
 * @author Tamuz Gitler
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator{

    //================ private constants ==============

    private static final int NUM_OF_BALLS = 3;
    private static final String BALL_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String MOCK_BALL_PATH = "assets/mockBall.png";

    //================ fields =========================

    private final ImageReader imageReader;
    private final SoundReader soundReader;

    //================ constructor ====================

    /**
     * Constructor
     * @param toBeDecorated Collision strategy object to be decorated
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects
     * @param soundReader a SoundReader instance for reading soundclips from files for rendering event sounds.
     */
    PuckStrategy(CollisionStrategy toBeDecorated,
                 danogl.gui.ImageReader imageReader,
                 danogl.gui.SoundReader soundReader){
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.soundReader = soundReader;
    }

    //================ public methods =================

    /**
     * creates 3 Puck balls when thisObj collides with otherObj
     * @param otherObj GameObject with which a collision occurred
     * @param counter global brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        float ballRadius = thisObj.getDimensions().x()/3;
        Vector2 ballLocation = thisObj.getCenter().add(new Vector2( - ballRadius,0));
        Sound collisionSounds = soundReader.readSound(BALL_SOUND_PATH);
        Renderable ballImage = imageReader.readImage(MOCK_BALL_PATH, true);
        Vector2 puckDimension = new Vector2(thisObj.getDimensions().x()/3,thisObj.getDimensions().x()/3);
        for(int i = 0; i < NUM_OF_BALLS; i++){
            Puck newBall = new Puck(ballLocation, puckDimension , ballImage, collisionSounds);
            ballLocation.add(new Vector2(ballRadius,0));
            this.getGameObjectCollection().addGameObject(newBall);
        }
    }
}
