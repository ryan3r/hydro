/**
 * The prototype for all undefined values
 */
public abstract class HydroUndefined {
    public static HydroObject proto = HydroObject.fromNative(HydroUndefined.class, ObjectProto.proto);
    
    /**
     * Create a new HydroUndefined
     */
    public static HydroObject create() {
        return new HydroObject(proto, "undefined");
    }
    
    /**
     * Print undefined
     */
    @ExposeAs("toString")
    public static HydroObject $toString() {
        return HydroString.create("undefined");
    }
    
    public static HydroObject toBoolean() {
        return HydroBoolean.create(false);
    }
}