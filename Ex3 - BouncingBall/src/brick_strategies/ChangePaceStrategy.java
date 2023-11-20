package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.WindowController;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.ChangePace;

import java.util.Random;

/**
 * drops a StatusDefiner when ball collides with brick.
 * this StatusDefiner when collided with paddle will change game time!
 * @author Tamuz Gitler
 */
public class ChangePaceStrategy extends RemoveBrickStrategyDecorator{

    //================ private constants ==============

    private final float SLOW_PACE =  0.9f;
    private final float QUICKEN_PACE =  1.1f;
    private static final int IMAGE_SPEED = 150;
    private static final String QUICKEN_PATH = "assets/quicken.png";
    private static final String SLOW_PATH = "assets/slow.png";

    //================ fields =========================

    private final WindowController windowController;
    private final ImageReader imageReader;

    //================ constructor ====================

    /**
     * Constructor
     * @param toBeDecorated Collision strategy object to be decorated
     * @param windowController controls visual rendering of the game window and object renderables.
     * @param imageReader an ImageReader instance for reading images from files for rendering of objects
     */
    public ChangePaceStrategy(CollisionStrategy toBeDecorated,
                              danogl.gui.WindowController windowController,
                              ImageReader imageReader){
        super(toBeDecorated);
        this.windowController = windowController;
        this.imageReader = imageReader;
    }

    //================ public  methods ================

    /**
     * drops a StatusSeter when thisObj collides with otherObj
     * the implementation is long but saves code duplication
     * @param otherObj GameObject with which a collision occurred
     * @param counter global brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        Random rand = new Random();
        Renderable paceImage;
        float pace;
        Renderable quickenImage = imageReader.readImage(QUICKEN_PATH, true);
        Renderable slowImage = imageReader.readImage(SLOW_PATH, true); //green
        if(this.windowController.getTimeScale() == 1) {
            //chooses randomly
            paceImage = quickenImage;
            pace = QUICKEN_PACE;
            if (rand.nextBoolean()){
                paceImage = slowImage;
                pace = SLOW_PACE;
            }
        }
        else {
            if (this.windowController.getTimeScale() == SLOW_PACE) {
                paceImage = quickenImage;
                pace = QUICKEN_PACE;
            } else {
                //quicken is active
                paceImage = slowImage;
                pace = SLOW_PACE;
            }
        }
        ChangePace changePace = new ChangePace(thisObj.getTopLeftCorner(),
            thisObj.getDimensions(),
            paceImage,
            this.getGameObjectCollection(),
            this.windowController,
            pace);
        changePace.setVelocity(new Vector2(0,IMAGE_SPEED));
        this.getGameObjectCollection().addGameObject(changePace);
    }
}
