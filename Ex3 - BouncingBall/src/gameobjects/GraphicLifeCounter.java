package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Display a graphic object on the game window showing as many widgets as lives left.
 * @author Tamuz Gitler
 */
public class GraphicLifeCounter extends GameObject {

    //================ private constants ==============

    private static final int NEXT_HEART_DISTANCE = 15;

    //================ fields =========================

    private final int numOfLives;
    private final Counter livesCounter;
    private final Vector2 widgetTopLeftCorner;
    private final Vector2 widgetDimensions;
    private final Renderable widgetRenderable;
    private final GameObjectCollection gameObjectCollection;
    private final GameObject[] heartsArr ;

    //================ constructor ====================

    /**
     *
     * @param widgetTopLeftCorner  top left corner of left most life widgets. Other widgets will be displayed to its
     *                           right, aligned in hight.
     * @param widgetDimensions dimensions of widgets to be displayed.
     * @param livesCounter global lives counter of game.
     * @param widgetRenderable image to use for widgets.
     * @param gameObjectsCollection global game object collection managed by game manager.
     * @param numOfLives global setting of number of lives a player will have in a game.
     */
    public GraphicLifeCounter(Vector2 widgetTopLeftCorner,
                              Vector2 widgetDimensions,
                              Counter livesCounter,
                              Renderable widgetRenderable,
                              GameObjectCollection gameObjectsCollection,
                              int numOfLives){
        super(widgetTopLeftCorner, widgetDimensions, widgetRenderable);
        this.numOfLives = numOfLives;
        this.heartsArr =  new GameObject[this.numOfLives];
        this.widgetTopLeftCorner = widgetTopLeftCorner;
        this.widgetDimensions = widgetDimensions;
        this.widgetRenderable = widgetRenderable;
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectsCollection;
        createLifes();
    }

    //================ public methods =================

    /**
     * Overrides update, removes heart from gameObjectCollection if livesCounter got decremented.
     * @param deltaTime time between updates. For internal use by game engine.
     *        yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (livesCounter.value() < numOfLives){
            this.gameObjectCollection.removeGameObject(this.heartsArr[this.livesCounter.value()], Layer.UI);
        }
    }

    //================ private methods ================

    /**
     * creates hearts objects on screen as the number of numOfLives
     */
    private void createLifes() {
        Vector2 nextHeartLocation = this.widgetTopLeftCorner;
        for(int i = 0; i < this.numOfLives; i++){
            GameObject life = new GameObject(nextHeartLocation, this.widgetDimensions, this.widgetRenderable);
            this.heartsArr[i] = life;
            nextHeartLocation = nextHeartLocation.add(new Vector2( NEXT_HEART_DISTANCE, 0));
            this.gameObjectCollection.addGameObject(life, Layer.UI);
        }
    }
}
