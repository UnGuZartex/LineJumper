package Actions;

public class FillAction extends LineJumperAction {

    @Override
    public String getName() {
        return "Fill pit";
    }

    @Override
    public void execute() {
        lineJumper.fillInFront();
    }
}
