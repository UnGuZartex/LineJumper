package Actions;

public class MoveForwardAction extends LineJumperAction {
    @Override
    public String getName() {
        return "Move Forward";
    }

    @Override
    public void execute() {
        if (lineJumper != null) {
            lineJumper.getPlayer().moveForward();
        }
        else {
            throw new IllegalStateException("This action is not bound with a GameWorld");
        }    }
}
