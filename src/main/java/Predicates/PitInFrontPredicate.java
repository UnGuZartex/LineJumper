package Predicates;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineWorld;

/**
 * A predicate to check whether or not there is a pit in front
 * of the robot in a line jumper.
 *
 * @author Alpha-team
 */
public class PitInFrontPredicate implements Predicate {

    /**
     * Get the name of this predicate.
     *
     * @return The name "Pit in Front";
     */
    @Override
    public String getName() {
        return "Pit in Front";
    }

    /**
     * Evaluate this predicate on the given game world.
     *
     * @param gameWorld The game world to evaluate.
     *
     * @return True if and only if the player has a pit in front
     *         in the given line jumper.
     */
    @Override
    public boolean evaluate(GameWorld gameWorld) {
        return ((LineWorld) gameWorld).playerHasPitInFront();
    }
}
