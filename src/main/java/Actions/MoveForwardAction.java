package Actions;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineJumper;

public class MoveForwardAction implements Action {
    @Override
    public String getName() {
        return "Move Forward";
    }

    @Override
    public void execute(GameWorld gameWorld) {
        ((LineJumper) gameWorld).getPlayer().moveForward();
    }
}
