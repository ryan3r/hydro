import java.util.ArrayList;

/**
 * Representation of a function defined in Hydro
 */
public class HydroFunction extends HydroObject {
    private ArrayList<Command> commands;
    private Scope parent;
    private String[] paramNames;
    private String name;
    
    public HydroFunction(String n, String[] pNames, ArrayList<Command> c, Scope p) {
        super(FunctionProto.proto, "function");
        commands = c;
        parent = p != null ? p : HydroContext.getGlobal();
        paramNames = pNames;
        name = n;
    }
    
    public Promise exec(Evaluator eval, HydroObject self, HydroObject[] params) {
        Scope scope = parent.create();
        
        // assign the self object
        scope.set("self", self);
	
	// no params
	if(params == null) {
		params = new HydroObject[0];
	}
        
        int length = Math.min(params.length, paramNames.length);
        // assign the parameters
        for(int i = 0; i < length; ++i) {
            scope.set(paramNames[i], params[i]);
        }
        
        StackFrame frame = new StackFrame(commands, scope, eval, name);
        eval.pushFunction(frame);
        
        return frame.getReturn();
    }
}
