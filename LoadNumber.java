
/**
 * Load a variable on to the execution stack
 */
public class LoadNumber implements Command {
    private double value;
    
    public LoadNumber(double v) {
        value = v;
    }
    
    /**
     * Run the command
     */
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        stack.push(HydroNumber.create(value));
    }
    
    public String toString() {
        return "number(" + value + ")";
    }
}
