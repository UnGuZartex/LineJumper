package Actions;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineJumper;

public class JumpAction implements Action {
    @Override
    public String getName() {
        return "Jump";
    }

    @Override
    public void execute(GameWorld gameWorld) {
        ((LineJumper) gameWorld).getPlayer().jump();
    }
}
