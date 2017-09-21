
/**
 * Get the value of a field
 */
public class FieldLookupCommand implements Command {
    private String fieldName;
    
    public FieldLookupCommand(String f) {
        fieldName = f;
    }
    
    /**
     * Run the command
     */
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        HydroObject obj = stack.pop();
        stack.push(obj.get(fieldName));
    }
    
    public String toString() {
        return "lookup(" + fieldName + ")";
    }
}
