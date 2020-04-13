package Predicates;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineJumper;

public class PitInFrontPredicate implements Predicate {
    @Override
    public String getName() {
        return "Pit in Front";
    }

    @Override
    public boolean evaluate(GameWorld gameWorld) {
        return ((LineJumper) gameWorld).playerHasPitInFront();
    }
}
