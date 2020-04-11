import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorld.Result;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.Predicate;
import GameWorldAPI.History.Snapshot;

import java.awt.*;

public class LineJumper implements GameWorld {
    @Override
    public Result executeAction(Action action) {
        return null;
    }

    @Override
    public boolean evaluatePredicate(Predicate predicate) {
        return false;
    }

    @Override
    public Snapshot createSnapshot() {
        return null;
    }

    @Override
    public void loadSnapshot(Snapshot snapshot) {

    }

    @Override
    public void undo() {

    }

    @Override
    public void redo() {

    }

    @Override
    public void reset() {

    }

    @Override
    public void paint(Graphics graphics) {

    }
}
