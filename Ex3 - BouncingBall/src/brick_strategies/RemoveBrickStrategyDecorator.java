package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * Abstract decorator to add functionality to the remove brick strategy, following the decorator pattern.
 * All strategy decorators should inherit from this class.
 * @author Tamuz Gitler
 */
public abstract class RemoveBrickStrategyDecorator
        extends java.lang.Object
        implements CollisionStrategy{

    //================ private constants ==============

    private final CollisionStrategy toBeDecorated;

    //================ constructor ====================

    /**
     * Constructor
     * @param toBeDecorated Collision strategy object to be decorated
     */
    RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated)
    {
        this.toBeDecorated = toBeDecorated;
    }

    //================ public methods =================
    /**
     * defines the behavior when otherObj collides with thisObj.
     * @param thisObj the object that we will define its behaviour when collided
     * @param otherObj the object that collides with thisObj
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {

        this.toBeDecorated.onCollision(thisObj,otherObj, counter);
    }

    /**
     * Overrides getGameObjectCollection to return this.toBeDecorated.gameObjectCollection.
     * @return gameObjectCollection
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.toBeDecorated.getGameObjectCollection();
    }
}
