package Actions;

public class JumpAction extends LineJumperAction {
    @Override
    public String getName() {
        return "Jump";
    }

    @Override
    public void execute() {
        if (lineJumper != null) {
            lineJumper.getPlayer().jump();
        }
        else {
            throw new IllegalStateException("This action is not bound with a GameWorld");
        }

    }
}
