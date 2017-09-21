import java.util.ArrayList;
import java.lang.reflect.*;

/**
 * Mapping to a java function
 */
public class NativeFunction extends HydroObject {
    private Method method;
    
    public NativeFunction(Method m) {
        super(FunctionProto.proto, "function");
        method = m;
    }
    
    public Promise exec(Evaluator eval, HydroObject self, HydroObject[] params) {
        // the number of parameters expected
        int paramCount = method.getParameterTypes().length;
        
        Object[] javaParams = new Object[paramCount];
        
        if(paramCount != 0) {
            javaParams[0] = eval;
        }
        
        if(paramCount > 1) {
            javaParams[1] = self;
        }
        
        int i = 2;
        
        for(; i < paramCount && i <= params.length + 1; ++i) {
            javaParams[i] = params[i - 2];
        }
        
        // fill in missing parameters with undefined
        for(; i < paramCount; ++i) {
            javaParams[i] = HydroUndefined.create();
        }
        
        try {
            Object result = method.invoke(null, javaParams);
            
            // if it is not a promise wrap it in one
            if(result instanceof HydroObject) {
                result = new Promise((HydroObject) result);
            }
            
            return (Promise) result;
        }
        catch(InvocationTargetException error) {
            Throwable cause = error.getCause();
            
            // throw
            if(cause instanceof HydroError) {
                throw (HydroError) cause;
            }
            // convert to hydro error
            else {
                cause.printStackTrace();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        
        return new Promise(HydroUndefined.create());
    }
}
