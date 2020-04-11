package Predicates;

public class PitInFrontPredicate extends LineJumperPredicate {
    @Override
    public String getName() {
        return "Pit in Front";
    }

    @Override
    public boolean evaluate() {
        if (lineJumper != null) {
            return lineJumper.playerHasPitInFront();
        }
        throw new IllegalStateException("This predicate has no GameWorld");
    }
}
