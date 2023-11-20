package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;


/**
 * CollisionStrategy defines the strategy that will occur when the otherObj collides a thisObj
 * @author Tamuz Gitler
 */
public interface CollisionStrategy {

    //================ public methods ================

    public abstract void onCollision(GameObject thisObj, GameObject otherObj, danogl.util.Counter counter);
    public abstract GameObjectCollection getGameObjectCollection();

}
