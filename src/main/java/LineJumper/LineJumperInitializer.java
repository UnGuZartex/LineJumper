package LineJumper;

import Actions.FillAction;
import Actions.JumpAction;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldAPI.GameWorldType.Predicate;
import Predicates.HasDirtPredicate;
import Predicates.PitInFrontPredicate;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineJumperInitializer implements GameWorldType {

    private final ArrayList<Action> actionList = new ArrayList<>(
            Arrays.asList(
                    new FillAction(),
                    new JumpAction()
            )
    );

    private final List<Predicate> predicateList = new ArrayList<>(
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
        return new LineJumper(30,5, 4);
    }
}
