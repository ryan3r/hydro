/**
 * The prototype for all Strings
 */
public abstract class HydroString {
    public static HydroObject proto = HydroObject.fromNative(HydroString.class, ObjectProto.proto);
    
    /**
     * Create a new HydroString
     */
    public static HydroObject create(String value) {
        HydroObject obj = new HydroObject(proto, "string");
        
        obj.setInnerValue("string", value);
        
        return obj;
    }
    
    /**
     * Extract the string value from a HydroString
     */
    public static String toJava(HydroObject obj) {
        if(obj != null && obj.typeof().equals("string")) {
            return (String) obj.getInnerValue("string");
        }
        else {
            return null;
        }
    }
    
    /**
     * Print the string value
     */
    @ExposeAs("toString")
    public static HydroObject $toString(Evaluator eval, HydroObject self) {
        return self;
    }
    
    /**
     * Convert a string to a number
     */
    public static HydroObject toNumber(Evaluator eval, HydroObject self) {
        try {
            Double value = new Double(HydroString.toJava(self));
            
            return HydroNumber.create(value);
        }
        catch(NumberFormatException e) {
            return HydroNumber.create(null);
        }
    }
    
    /**
     * Get the length of the string
     */
    public static HydroObject length(Evaluator eval, HydroObject self) {
        String str = HydroString.toJava(self);
        double length = str == null ? 0 : str.length();
        
        return HydroNumber.create(length);
    }
    
    /**
     * Test equality between the values
     */
    @ExposeAs("==")
    public static HydroObject equals(Evaluator eval, HydroObject self, HydroObject other) {
        if(!self.typeof().equals("string") || !other.typeof().equals("string")) {
            return HydroBoolean.create(false);
        }
        
        return HydroBoolean.create(toJava(self).equals(toJava(other)));
    }
    
    /**
     * Concatinate a string and another value
     */
    @ExposeAs("++")
    public static Promise concat(Evaluator eval, HydroObject self, HydroObject other) {
        final String str = toJava(self);
        
        return other.get("toString")
            .exec(eval, other, new HydroObject[] {})
            
            .then(new PromiseHandler() {
                public HydroObject handle(HydroObject str2) {
                    return HydroString.create(str + toJava(str2));
                }
            });
    }
}