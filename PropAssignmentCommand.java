import java.util.ArrayList;

/**
 * Command for an assignment for an object
 */
class PropAssignmentCommand implements Command {
    private String name;
    
    public PropAssignmentCommand(String n) {
        name = n;
    }
    
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        HydroObject value = stack.pop();
        HydroObject obj = stack.pop();
        
        obj.set(name, value);
    }
}