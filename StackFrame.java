import java.util.ArrayList;

/**
 * A frame in the call stack
 */
public class StackFrame {
    private Stack stack;
    private int commandPtr;
    private ArrayList<Command> commands;
    private Scope scope;
    private Evaluator evaluator;
    private Promise returnValue;
    private String name;
    
    private static boolean DEBUG_CALL_STACK = false;
    
    /**
     * Create a stack frame
     */
    public StackFrame(ArrayList<Command> com, Scope s, Evaluator eval, String n) {
        commands = com;
        commandPtr = 0;
        stack = new Stack();
        scope = s;
        evaluator = eval;
        returnValue = new Promise();
        name = n;
        
        if(DEBUG_CALL_STACK) System.out.println("Push: " + name);
    }
    
    /**
     * Check if we have executed all the commands
     */
    public boolean isEnded() {
        return commands.size() <= commandPtr;
    }
    
    /**
     * Jump to a position in the code
     */
    public void goTo(int location) {
        // jump to the end
        if(location == -1) {
            commandPtr = commands.size();
        }
        else {
            commandPtr = location;
        }
    }
    
    /**
     * Run a single command and move the pointer
     */
    public void runCommand() {
        if(DEBUG_CALL_STACK) System.out.println("Run: " + name + " at " + commandPtr);
        
        // check if we still have commands to run
        if(!isEnded()) {
            // DO NOT CHANGE commandPtr++ the pointer must be updated for Evaluator.pushFunction
            commands.get(commandPtr++).exec(stack, scope, evaluator);
        }
        
        // resolve return Promise
        if(isEnded()) {
            if(DEBUG_CALL_STACK) System.out.println("Resolve: " + name);
            
            if(stack.empty()) {
                returnValue.resolve(HydroUndefined.create());
            }
            else {
                returnValue.resolve(stack.popPromise());
            }
        }
    }
    
    /**
     * Get a promise to the return value for this stack
     */
    public Promise getReturn() {
        // if no code is present resolve the promise now
        if(commands.size() == 0) {
            returnValue.resolve(HydroUndefined.create());
        }
        
        return returnValue;
    }
}