package Predicates;

public class HasDirtPredicate extends LineJumperPredicate {
    @Override
    public String getName() {
        return "Has Dirt";
    }

    @Override
    public boolean evaluate() {
        if (lineJumper != null) {
            return lineJumper.getPlayer().hasDirt();
        }
        throw new IllegalStateException("This predicate has no GameWorld");
    }
}
