import Actions.FillAction;
import Actions.JumpAction;
import Actions.MoveForwardAction;
import LineJumper.LineJumper;
import LineJumper.LineJumperInitialiser;
import Predicates.HasDirtPredicate;
import Predicates.PitInFrontPredicate;

//TODO DELETE DEZE CLASS
public class Main {
    public static void main(String[] args) {
        LineJumperInitialiser initialiser = new LineJumperInitialiser();

        System.out.println(initialiser.createNewGameWorld());

        boolean[] testArray = new boolean[] {true, false, false, true, false, false, true, true};

        LineJumper jumper = new LineJumper(testArray, 2 , 2);


        System.out.println(jumper);

        System.out.println(jumper.evaluatePredicate(new HasDirtPredicate()));

        System.out.println(jumper.evaluatePredicate(new PitInFrontPredicate()));

        System.out.println(jumper.executeAction(new FillAction()));
        System.out.println(jumper);

        System.out.println(jumper.executeAction(new MoveForwardAction()));
        System.out.println(jumper);

        System.out.println(jumper.executeAction(new JumpAction()));
        System.out.println(jumper);

        System.out.println(jumper.executeAction(new FillAction()));
        System.out.println(jumper);

        System.out.println(jumper.executeAction(new MoveForwardAction()));
        System.out.println(jumper);

        System.out.println(jumper.evaluatePredicate(new HasDirtPredicate()));

        System.out.println(jumper.executeAction(new JumpAction()));
        System.out.println(jumper);
        System.out.println(jumper.evaluatePredicate(new PitInFrontPredicate()));



        System.out.println(jumper.executeAction(new JumpAction()));
        System.out.println(jumper);
    }
}
