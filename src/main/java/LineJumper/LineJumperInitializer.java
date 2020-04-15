package LineJumper;

import Actions.FillAction;
import Actions.JumpAction;
import Actions.MoveForwardAction;
import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import GameWorldAPI.GameWorldType.GameWorldType;
import GameWorldAPI.GameWorldType.Predicate;
import Predicates.HasDirtPredicate;
import Predicates.PitInFrontPredicate;

import java.io.File;
import java.security.acl.NotOwnerException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LineJumperInitializer implements GameWorldType {

    /**
     * Variable referring to the actions possible. This is a list with 3
     * different actions: fill, jump and move forward.
     */
    private final List<Action> actionList = new ArrayList<>(
            Arrays.asList(
                    new FillAction(),
                    new JumpAction(),
                    new MoveForwardAction()
            )
    );
    /**
     * Variable referring to the predicates possible. This is a list with 2
     * predicates: has dirt and pit in front.
     */
    private final List<Predicate> predicateList = new ArrayList<>(
            Arrays.asList(
                    new HasDirtPredicate(),
                    new PitInFrontPredicate()
            )
    );

    /**
     * Get all the actions possible.
     *
     * @return A copy of the actions list.
     */
    @Override
    public List<Action> getAllActions() {
        return new ArrayList<>(actionList);
    }

    /**
     * Get all predicates possible.
     *
     * @return A copy of the predicates list.
     */
    @Override
    public List<Predicate> getAllPredicates() {
        return new ArrayList<>(predicateList);
    }

    /**
     * Return null, this method is not implemented yet.
     */
    @Override
    public GameWorld loadFromFile(File file) {
        throw new IllegalStateException();
    }

    /**
     * TODO
     * @return
     */
    @Override
    public GameWorld createNewGameWorld() {
        return new LineJumper(30,5, 4);
    }
}
