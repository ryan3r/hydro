/**
 * The prototype for all functions
 */
public abstract class FunctionProto {
    public static HydroObject proto = HydroObject.fromNative(FunctionProto.class, ObjectProto.proto);
    
    @ExposeAs("toString")
    public static HydroObject $toString() {
        return HydroString.create("[Function]");
    }
}