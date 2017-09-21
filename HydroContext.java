import java.util.ArrayList;

/**
 * Context for a script/module
 */
public class HydroContext {
    private static Scope global = new Scope();
    
    // set global stuff
    static {
        global.set("global", global.toObject());
        global.set("Object", ObjectProto.proto);
        global.set("Number", HydroNumber.proto);
        global.set("String", HydroString.proto);
        global.set("Undefined", HydroUndefined.proto);
        global.set("true", HydroBoolean.create(true));
        global.set("false", HydroBoolean.create(false));
        global.set("System", HydroSystem.obj);
        global.set("Function", FunctionProto.proto);
        global.set("NaN", HydroNumber.create(null));
        global.set("require", HydroModule.obj.get("require"));
    }
    
    private Scope scope;
    // FIX THIS SHOULD NOT BE PUBLIC !!!
    public Evaluator evaluator;
    
    /**
     * Create a new context
     */
    public HydroContext() {
        evaluator = new Evaluator(global);
        scope = global.create();
    }
    
    /**
     * Get an ArrayList of tokens
     */
    public static ArrayList<Token> tokenize(String text, Source source) {
        Tokenizer tokenizer = new Tokenizer(text);
        return tokenizer.tokenize(source);
    }
    
    /**
     * Parse the given text to an ArrayList of commands
     */
    public static ArrayList<Command> parse(String text, Source source) {
        ArrayList<Token> tokens = tokenize(text, source);
        
        Parser parser = new Parser(tokens);
        ArrayList<Command> commands = new ArrayList<Command>();
        
        for(AstNode node : parser.parse())
            node.build(commands);
            
        return commands;
    }
    
    /**
     * Evaluate some code
     */
    public HydroObject eval(String text) {
        Source source = new Source();
        
        try {
            ArrayList<Command> commands = parse(text, source);
            
            StackFrame frame = new StackFrame(commands, scope, evaluator, "Main");
            evaluator.pushFunction(frame);
            
            Promise ret = frame.getReturn();
            
            evaluator.start();
            
            return ret.unwrap();
        }
        catch(HydroError error) {
            error.setSource(source);
            
            throw error;
        }
    }
    
    /**
     * Access the scope
     */
    public Scope getScope() {
        return scope;
    }
    
    /**
     * Access the global scope
     */
    public static Scope getGlobal() {
        return global;
    }
}
