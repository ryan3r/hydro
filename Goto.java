
/**
 * Command to conditionaly jump to a place in the code
 * 
 * ONLY JUMPS IF THE VALUE ON THE STACK IS FALSE
 */
public class Goto implements Command {
    private int jumpTo;
    
    public Goto(int j) {
        jumpTo = j;
    }
    
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        eval.goTo(jumpTo);
    }
}