package Actions;

public class FillAction extends LineJumperAction {

    @Override
    public String getName() {
        return "Fill pit";
    }

    @Override
    public void execute() {
        if (lineJumper != null) {
            lineJumper.fillInFront();
        }
        else {
            throw new IllegalStateException("This action is not bound with a GameWorld");
        }
    }
}
