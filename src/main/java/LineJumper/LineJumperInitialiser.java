package LineJumper;

import Actions.FillAction;
import Actions.JumpAction;
import Actions.LineJumperAction;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldAPI.GameWorldType.Predicate;
import Predicates.HasDirtPredicate;
import Predicates.LineJumperPredicate;
import Predicates.PitInFrontPredicate;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineJumperInitialiser implements GameWorldType {

    private final ArrayList<LineJumperAction> actionList = new ArrayList<>(
            Arrays.asList(
                    new FillAction(),
                    new JumpAction()
            )
    );

    private final List<LineJumperPredicate> predicateList = new ArrayList<>(
            Arrays.asList(
                    new HasDirtPredicate(),
                    new PitInFrontPredicate()
            )
    );

    @Override
    public List<Action> getAllActions() {
        return new ArrayList<>(actionList);
    }

    @Override
    public List<Predicate> getAllPredicates() {
        return new ArrayList<>(predicateList);
    }

    @Override
    public GameWorld loadFromFile(File file) {
        return null;
    }

    @Override
    public GameWorld createNewGameWorld() {
        return new LineJumper(5000,500, 0);
    }
}
