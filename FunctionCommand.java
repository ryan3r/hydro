import java.util.ArrayList;

/**
 * The command to define a function
 */
public class FunctionCommand implements Command {
    private String[] args;
    private ArrayList<Command> commands;
    
    public FunctionCommand(String[] a, ArrayList<Command> c) {
        args = a;
        commands = c;
    }
    
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        stack.push(new HydroFunction("Unknown", args, commands, scope));
    }
}