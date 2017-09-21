
/**
 * Load a variable on to the execution stack
 */
public class LoadString implements Command {
    private String value;
    
    public LoadString(String v) {
        value = v;
    }
    
    /**
     * Run the command
     */
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        stack.push(HydroString.create(value));
    }
    
    public String toString() {
        return "string(" + value + ")";
    }
}
