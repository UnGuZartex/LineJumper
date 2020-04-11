package Predicates;

public class HasDirtPredicate extends LineJumperPredicate {
    @Override
    public String getName() {
        return "Has Dirt";
    }

    @Override
    public boolean evaluate() {
        return lineJumper.getPlayer().hasDirt();
    }
}
