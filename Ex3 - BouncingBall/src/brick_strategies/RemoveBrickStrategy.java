package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Concrete brick strategy implmenting CollisionStrategy interface.
 * Removes holding brick on collision.
 * @author Tamuz Gitler
 */
public class RemoveBrickStrategy implements CollisionStrategy{

    //================ fields =========================

    private GameObjectCollection gameObjectCollection;

    //================ constructor ====================

    /**
     * Constructor
     * @param gameObjectCollection gameObjectCollection global game object collection managed by game manager
     */
    public RemoveBrickStrategy(GameObjectCollection gameObjectCollection){
        this.gameObjectCollection = gameObjectCollection;
    }

    //================ public methods =================
    /**
     * defines the behavior when otherObj collides with thisObj.
     * in our case thisObj will be removed from gameObjectCollection
     * @param thisObj the object that we will define its behaviour when collided
     * @param otherObj the object that collides with thisObj
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        this.gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
        counter.decrement();
    }

    /**
     * Overrides getGameObjectCollection to return this.gameObjectCollection.
     * @return gameObjectCollection
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjectCollection;
    }
}
