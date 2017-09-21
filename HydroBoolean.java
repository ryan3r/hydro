/**
 * The prototype for all boolean values
 */
public abstract class HydroBoolean {
    public static HydroObject proto = HydroObject.fromNative(HydroBoolean.class, ObjectProto.proto);
    
    /**
     * Create a new HydroBoolean
     */
    public static HydroObject create(boolean bool) {
        HydroObject obj = new HydroObject(proto, "boolean");
        
        obj.setInnerValue("boolean", bool);
        
        return obj;
    }
    
    /**
     * Convert to a java boolean
     */
    public static boolean toJava(HydroObject obj) {
        if(obj.typeof().equals("boolean")) {
            return (boolean) obj.getInnerValue("boolean");
        }
        else {
            return false;
        }
    }
    
    /**
     * Print undefined
     */
    @ExposeAs("toString")
    public static HydroObject $toString(Evaluator eval, HydroObject self) {
        return HydroString.create(self.getInnerValue("boolean").toString());
    }
    
    /**
     * Logical and
     */
    @ExposeAs("&&")
    public static HydroObject and(Evaluator eval, HydroObject self, HydroObject other) {
        if(other.typeof() == "boolean") {
            return HydroBoolean.create(
                (boolean) self.getInnerValue("boolean") && (boolean) other.getInnerValue("boolean")
            );
        }
        else {
            return HydroBoolean.create(false);
        }
    }
    
    /**
     * Logical or
     */
    @ExposeAs("||")
    public static HydroObject or(Evaluator eval, HydroObject self, HydroObject other) {
        if(other.typeof() == "boolean") {
            return HydroBoolean.create(
                (boolean) self.getInnerValue("boolean") || (boolean) other.getInnerValue("boolean")
            );
        }
        else {
            return HydroBoolean.create(false);
        }
    }
    
    /**
     * Logical not
     */
    public static HydroObject not(Evaluator eval, HydroObject self, HydroObject other) {
        if(other.typeof() == "boolean") {
            return HydroBoolean.create(!((boolean) self.getInnerValue("boolean")));
        }
        else {
            return HydroBoolean.create(false);
        }
    }
}