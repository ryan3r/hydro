
/**
 * Load a variable on to the execution stack
 */
public class LoadVar implements Command {
    private String varName;
    
    public LoadVar(String n) {
        varName = n;
    }
    
    /**
     * Run the command
     */
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        stack.push(scope.get(varName));
    }
    
    public String toString() {
        return "var(" + varName + ")";
    }
}
