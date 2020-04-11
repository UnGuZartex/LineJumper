package Actions;

import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineJumper;

public abstract class LineJumperAction implements Action {

    protected LineJumper lineJumper;

    public abstract String getName();

    public abstract void execute();

    public void setLineJumper(LineJumper lineJumper) {
        this.lineJumper = lineJumper;
    }

}
