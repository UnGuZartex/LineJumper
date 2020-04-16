package Actions;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineWorld;

/**
 * An action to fill a pit in front of the robot in a
 * line jumper.
 *
 * @author Alpha-team.
 */
public class FillAction implements Action {

    /**
     * Get the name of this action.
     *
     * @return The name "Fill Pit".
     */
    @Override
    public String getName() {
        return "Fill Pit";
    }

    /**
     * Execute this action on the given game world.
     *
     * @param gameWorld The game world to execute this action on.
     *
     * @effect Make the player in the given line jumper fill the
     *         pit in front.
     */
    @Override
    public void execute(GameWorld gameWorld) {
        ((LineWorld) gameWorld).fillInFront();
    }
}
