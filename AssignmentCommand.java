
/**
 * Command for an assignment 
 */
class AssignmentCommand implements Command {
    private String name;
    
    public AssignmentCommand(String n) {
        name = n;
    }
    
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        HydroObject value = stack.pop();
        
        scope.set(name, value);
        
        stack.push(value);
    }
}