import java.util.ArrayList;
import java.util.Stack;

/**
 * An evaluation context
 */
public class Evaluator {
    private Stack<StackFrame> callStack;
    private Scope global;
    
    public Evaluator(Scope g) {
        global = g;
        callStack = new Stack<StackFrame>();
    }
    
    /**
     * Get the global scope
     */
    public Scope getGlobal() {
        return global;
    }
    
    /**
     * Push a stack frame to the call stack
     */
    public void pushFunction(StackFrame frame) {
        // if the current StackFrame has finished executing swap it out
        if(!callStack.empty() && callStack.peek().isEnded()) {
            callStack.pop();
        }
        
        callStack.push(frame);
    }
    
    /**
     * Jump to the specified location in the current stack frame
     * 
     * location = -1  jumps to the end of the stack frame
     */
    public void goTo(int location) {
        callStack.peek().goTo(location);
    }
    
    /**
     * Start executing code
     */
    public void start() {
        try {
            while(!callStack.empty()) {
                callStack.peek().runCommand();

                // check if the StackFrame is done running
                if(callStack.peek().isEnded()) {
                    callStack.pop();
                }
            }
        }
        finally {
            while(!callStack.empty()) {
                callStack.pop();
            }
        }
    }
}