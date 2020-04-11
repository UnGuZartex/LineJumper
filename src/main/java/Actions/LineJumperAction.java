package Actions;

import GameWorldAPI.GameWorldType.Action;
import LineJumper.LineJumper;

public abstract class LineJumperAction implements Action {

    /**
     * Variable referencing to the LineJumper GameWorld this action will be executed on
     */
    protected LineJumper lineJumper;

    /**
     * Return the name of the action
     * @return the name of the action
     */
    public abstract String getName();

    /**
     * Execute the action
     */
    public abstract void execute();

    /**
     * Sets the parameter of this action to the given LineJumper
     * @param lineJumper the LineJumper this action needs to operate on
     *
     * @post The LineJumper for this action is changed to the given LineJumper
     */
    public void setLineJumper(LineJumper lineJumper) {
        this.lineJumper = lineJumper;
    }

}
