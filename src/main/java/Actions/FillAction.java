package Actions;

import GameWorldAPI.GameWorld.GameWorld;
import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineJumper;

public class FillAction implements Action {

    @Override
    public String getName() {
        return "Fill pit";
    }

    @Override
    public void execute(GameWorld gameWorld) {
        ((LineJumper) gameWorld).fillInFront();
    }
}
