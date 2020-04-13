package Predicates;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineJumper;

public class HasDirtPredicate implements Predicate {
    @Override
    public String getName() {
        return "Has Dirt";
    }

    @Override
    public boolean evaluate(GameWorld gameWorld) {
        return ((LineJumper) gameWorld).getPlayer().hasDirt();
    }
}
