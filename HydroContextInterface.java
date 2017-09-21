import java.util.ArrayList;

/**
 * HydroContext wrapper to create and use contexts in Hydro
 */
public abstract class HydroContextInterface {
    public static HydroObject proto = HydroObject.fromNative(HydroContextInterface.class, ObjectProto.proto);
    
    /**
     * Create a wrapper from a context
     */
    public static HydroObject create() {
        HydroContext ctx = new HydroContext();
        HydroObject obj = new HydroObject(proto, "context");
        
        obj.setInnerValue("context", ctx);
        
        // expose the scope as a property
        obj.set("scope", ctx.getScope().toObject());
        
        return obj;
    }
    
    /**
     * Evaluate code
     */
    public static HydroObject eval(Evaluator eval, HydroObject self, HydroObject code) {
        HydroContext context = (HydroContext) self.getInnerValue("context");
        
        if(code.typeof().equals("string")) {
            try {
                HydroObject value = context.eval(HydroString.toJava(code));
                
                return HydroResult.success(value);
            }
            catch(HydroError error) {
                return HydroResult.error(error);
            }
        }
        else {
            throw new HydroError("HydroContext.eval must be called with a string");
        }
    }
}