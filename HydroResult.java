
/**
 * A result that could contain an error
 */
public abstract class HydroResult {
    public static HydroObject proto = HydroObject.fromNative(HydroResult.class, ObjectProto.proto);
    
    /**
     * Create a new result
     */
    private static HydroObject create(boolean success, Object value) {
        HydroObject obj = new HydroObject(proto, "result");
        
        obj.set("success", HydroBoolean.create(success));
        obj.setInnerValue("value", value);
        
        return obj;
    }
    
    /**
     * Easy create methods
     */
    public static HydroObject success(HydroObject value) {
        return create(true, value);
    }
    
    public static HydroObject error(HydroError error) {
        return create(false, error);
    }
    
    // ========================= Error tools ========================= //
    
    /**
     * Get the error message
     */
    public static HydroObject getMessage(Evaluator eval, HydroObject self) {
        if(HydroBoolean.toJava(self.get("success"))) {
            throw new HydroError("Result is not an error can not call getMessage()");
        }
        else {
            HydroError error = (HydroError) self.getInnerValue("value");
            
            return HydroString.create(error.getMessage());
        }
    }
    
    /**
     * Print out the error message
     */
    public static HydroObject printError(Evaluator eval, HydroObject self) {
        if(HydroBoolean.toJava(self.get("success"))) {
            throw new HydroError("Result is not an error can not call printError()");
        }
        else {
            HydroError error = (HydroError) self.getInnerValue("value");
            
            error.print();
            
            return HydroUndefined.create();
        }
    }
    
    // ========================= Success tools ========================= //
    
    /**
     * Extract the value
     */
    public static HydroObject unwrap(Evaluator eval, HydroObject self) {
        if(HydroBoolean.toJava(self.get("success"))) {
            return (HydroObject) self.getInnerValue("value");
        }
        else {
            throw new HydroError("Result is an error can not extract the value");
        }
    }
}