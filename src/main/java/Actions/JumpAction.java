package Actions;

public class JumpAction extends LineJumperAction {
    @Override
    public String getName() {
        return "Jump";
    }

    @Override
    public void execute() {
        lineJumper.getPlayer().jump();
    }
}
