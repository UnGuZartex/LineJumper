package Actions;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineJumper;

/**
 * An action to make the player of a line jumper jump.
 *
 * @author Alpha-team.
 */
public class JumpAction implements Action {

    /**
     * Get the name of this action.
     *
      * @return The name "Jump".
     */
    @Override
    public String getName() {
        return "Jump";
    }

    /**
     * Execute this action on the given game world.
     *
     * @param gameWorld The game world to execute this action on.
     *
     * @effect The player of the given line jumper will jump.
     */
    @Override
    public void execute(GameWorld gameWorld) {
        ((LineJumper) gameWorld).getPlayer().jump();
    }
}
