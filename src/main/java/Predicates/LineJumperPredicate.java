package Predicates;

import GameWorldAPI.GameWorldType.Predicate;
import LineJumper.LineJumper;

public abstract class LineJumperPredicate implements Predicate {

    protected LineJumper lineJumper;

    public abstract String getName();

    public abstract boolean evaluate();

    public void setLineJumper(LineJumper lineJumper) {
        this.lineJumper = lineJumper;
    }
}
