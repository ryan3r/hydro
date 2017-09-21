import java.util.ArrayList;

/**
 * Execute a function on the call stack
 */
public class FunctionCallCommand implements Command {
    private int numArgs;
    private String fnName;
    private boolean onObject;
    private Token lineNumber;
    
    public FunctionCallCommand(int num, String n, boolean o, Token ln) {
        numArgs = num;
        fnName = n;
        onObject = o;
        lineNumber = ln;
    }
    
    /**
     * Run the command
     */
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        HydroObject[] params = new HydroObject[numArgs];
        
        // remove the arguments from the stack
        for(int i = 0; i < numArgs; ++i) {
            params[numArgs - i - 1] = stack.pop();
        }
        
        HydroObject fn = null, host = null;
        
        // decide what the self reference points to
        if(onObject) {
            host = stack.pop();
            
            if(host != null) {
                fn = host.get(fnName);
            }
        }
        else {
            fn = stack.pop();
        }
        
        if(host == null) {
            host = HydroUndefined.create();
        }
        
        if(fn != null && fn.typeof().equals("function")) {
            stack.push(fn.exec(eval, host, params));
        }
        else {
            throw new HydroError("Not a function", lineNumber);
        }
    }
    
    public String toString() {
        return "fnCall(" + numArgs + ")";
    }
}
