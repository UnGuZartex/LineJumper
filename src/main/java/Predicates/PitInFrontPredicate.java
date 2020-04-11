package Predicates;

public class PitInFrontPredicate extends LineJumperPredicate {
    @Override
    public String getName() {
        return "Pit in Front";
    }

    @Override
    public boolean evaluate() {
        return lineJumper.playerHasPitInFront();
    }
}
