/**
 * The prototype for all objects
 */
public abstract class ObjectProto {
    public static HydroObject proto = HydroObject.fromNative(ObjectProto.class, null);
    
    /**
     * Create an object that inherits from this one
     */
    public static HydroObject create(Evaluator eval, HydroObject self) {
        return new HydroObject(self);
    }
    
    @ExposeAs("==")
    public static HydroObject equal(Evaluator eval, final HydroObject self, HydroObject other) {
        return HydroBoolean.create(self == other);
    }
    
    /**
     * Convert the object to a string or boolean
     */
    @ExposeAs("toString")
    public static HydroObject $toString() {
        return HydroString.create("[Object]");
    }
    
    public static HydroObject toBoolean() {
        return HydroBoolean.create(true);
    }
    
    // get the type of this object
    public static HydroObject typeof(Evaluator eval, HydroObject self) {
        return HydroString.create(self.typeof());
    }
    
    /**
     * Expose get, set, and has
     */
    public static Promise get(Evaluator eval, final HydroObject self, HydroObject name) {
        // the name must be a string
        return name.get("toString")
            .exec(eval, name, new HydroObject[] {})
            
            .then(new PromiseHandler() {
                public HydroObject handle(HydroObject nameStr) {
                    String name = HydroString.toJava(nameStr);
                    
                    return self.get(name);
                }
            });
    }
    
    
    public static Promise has(Evaluator eval, final HydroObject self, HydroObject name) {
        // the name must be a string
        return name.get("toString")
            .exec(eval, name, new HydroObject[] {})
            
            .then(new PromiseHandler() {
                public HydroObject handle(HydroObject nameStr) {
                    String name = HydroString.toJava(nameStr);
                    
                    return HydroBoolean.create(self.has(name));
                }
            });
    }
    
    public static Promise set(Evaluator eval, final HydroObject self, HydroObject name, final HydroObject value) {
        // the name must be a string
        return name.get("toString")
            .exec(eval, name, new HydroObject[] {})
            
            .then(new PromiseHandler() {
                public HydroObject handle(HydroObject nameStr) {
                    String name = HydroString.toJava(nameStr);
                    
                    self.set(name, value);
                    
                    return HydroUndefined.create();
                }
            });
    }
    
    public static HydroObject keys(Evaluator eval, HydroObject self) {
        return self.keys();
    }
    
    public static HydroObject ownKeys(Evaluator eval, HydroObject self) {
        return self.ownKeys();
    }
}