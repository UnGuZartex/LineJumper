package Actions;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineWorld;

/**
 * An action which moves the player in a line jumper one position forward.
 */
public class MoveForwardAction implements Action {

    /**
     * Get the name of this action.
     *
     * @return The name "Move Forward";
     */
    @Override
    public String getName() {
        return "Move Forward";
    }

    /**
     * Execute this action on the given game world.
     *
     * @param gameWorld The game world to execute this action on.
     *
     * @effect Moves the player in the given line jumper one position forward.
     */
    @Override
    public void execute(GameWorld gameWorld) {
        ((LineWorld) gameWorld).getPlayer().moveForward();
    }
}
