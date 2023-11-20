package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;

import danogl.collisions.Layer;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Display a graphic object on the game window showing a numeric count of lives left.
 * @author Tamuz Gitler
 */
public class NumericLifeCounter extends GameObject {

    //================ fields =========================

    private final Counter livesCounter;

    //================ constructor ====================

    /**
     * Constructor
     * @param livesCounter global lives counter of game.
     * @param topLeftCorner top left corner of left most life widgets. Other widgets will be displayed to its
     *                      right, aligned in hight.
     * @param dimensions of widgets to be displayed.
     * @param gameObjectCollection  global game object collection managed by game manager.
     */
    public NumericLifeCounter(Counter livesCounter,
                              Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {

        super(topLeftCorner, dimensions, new TextRenderable("Lives " + String.valueOf(livesCounter.value())));
        this.livesCounter = livesCounter;
    }

    //================ public methods =================

    /**
     * Overrides update, removes text from gameObjectCollection if livesCounter got decremented, and adds
     * the updated livesCounter
     * @param deltaTime time between updates. For internal use by game engine. You do not need to call this method
     *        yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        TextRenderable text = new TextRenderable("Lives " +String.valueOf(livesCounter.value()));
        text.setColor(Color.RED);
        this.renderer().setRenderable(text);
    }

}
