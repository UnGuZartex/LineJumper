package Predicates;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineJumper;

/**
 * A predicate to check whether or not there is dirt in front
 * of the player in a line jumper.
 *
 * @author Alpha-team
 */
public class HasDirtPredicate implements Predicate {

    /**
     * Get the name of this predicate.
     *
     * @return The name "Has Dirt".
     */
    @Override
    public String getName() {
        return "Has Dirt";
    }

    /**
     * Evaluate this predicate on the given game world.
     *
     * @param gameWorld The game world to check.
     *
     * @return True if and only if the player in the given
     *         line jumper has dirt in front of him.
     */
    @Override
    public boolean evaluate(GameWorld gameWorld) {
        return ((LineJumper) gameWorld).getPlayer().hasDirt();
    }
}
