import java.util.HashMap;
import java.lang.reflect.*;

/**
 * Object representaion in hydro
 */
public class HydroObject {
    private HashMap<String, HydroObject> fields;
    private HydroObject prototype;
    private String innerValueName;
    private Object innerValue;
    private String type;
    
    public HydroObject(HydroObject proto, String t) {
        prototype = proto;
        fields = new HashMap<String, HydroObject>();
        type = t;
    }
    
    public HydroObject(HydroObject proto) {
        this(proto, proto == null ? "object" : proto.typeof());
    }
    
    public HydroObject() {
        this(null);
    }
    
    /**
     * Check if the specified field exists on this object or our prototype
     */
    public boolean has(String name) {
        if(fields.containsKey(name)) {
            return true;
        }
        
        // search the prototype
        if(prototype != null) {
            return prototype.has(name);
        }
        
        return false;
    }
    
    /**
     * Check if the specified field exists on this object
     */
    public boolean hasOwn(String name) {
        return fields.containsKey(name);
    }
    
    /**
     * Retreve the value for a given key
     */
    public HydroObject get(String name) {
        if(fields.containsKey(name)) {
            return fields.get(name);
        }
        
        // search the prototype
        if(prototype != null) {
            return prototype.get(name);
        }
        
        return HydroUndefined.create();
    }
    
    /**
     * Store a value on the object
     */
    public void set(String name, HydroObject value) {
        fields.put(name, value);
    }
    
    /**
     * Get all the keys on this object as a hydro object
     */
    public HydroObject ownKeys() {
        HydroObject obj = new HydroObject(ObjectProto.proto);
        
        int length = 0;
        
        for(String key : fields.keySet()) {
            obj.set(length + "", HydroString.create(key));
            
            ++length;
        }
        
        obj.set("length", HydroNumber.create((double) length));
        
        return obj;
    }
    
    /**
     * Get all the keys on this object and its parent
     */
    public HydroObject keys() {
        // no prototype to check
        if(prototype == null) {
            return this.ownKeys();
        }
        
        HydroObject obj = prototype.keys();
        
        int length = (int) HydroNumber.toJava(obj.get("length")).doubleValue();
        
        for(String key : fields.keySet()) {
            obj.set(length + "", HydroString.create(key));
            
            ++length;
        }
        
        obj.set("length", HydroNumber.create((double) length));
        
        return obj;
    }
    
    /**
     * NEVER CALL exec() UNLESS isExecutable IS TRUE
     */
    public Promise exec(Evaluator eval, HydroObject self, HydroObject[] params) {
        // call the exec on the prototype
        if(prototype != null) {
            return prototype.exec(eval, self, params);
        }
        
        // this should never happen but if it does handle it gracefuly
        return new Promise(HydroUndefined.create());
    }
    
    /**
     * Get a java specific value
     */
    public Object getInnerValue(String name) {
        if(innerValueName != null && innerValueName.equals(name)) {
            return innerValue;
        }
        
        if(prototype != null) {
            return prototype.getInnerValue(name);
        }
        
        return null;
    }
    
    /**
     * Set a java specific value
     */
    public void setInnerValue(String name, Object value) {
        innerValueName = name;
        innerValue = value;
    }
    
    /**
     * Get the type for this object
     */
    public String typeof() {
        return type;
    }
    
    /**
     * Create an object with the static methods
     */
    @SuppressWarnings("unsafe")
    public static HydroObject fromNative(Class klass, HydroObject prototype) {
        HydroObject object = new HydroObject(prototype);
        Method[] methods = klass.getDeclaredMethods();
        
        methodLoop: for(Method method : methods) {
            // must take and return only HydroObjects or promises
            if(!method.getReturnType().equals(HydroObject.class) && !method.getReturnType().equals(Promise.class)) {
                continue;
            }
            
            Class[] methodParams = method.getParameterTypes();
            
            // first parameter should be an evaluator
            if(methodParams.length > 0 && !methodParams[0].equals(Evaluator.class)) {
                continue methodLoop;
            }
            
            // check the parameter types
            for(int i = 1; i < methodParams.length; ++i) {
                Class type = methodParams[i];
                
                if(!type.equals(HydroObject.class)) {
                    continue methodLoop;
                }
            }
            
            if(Modifier.isStatic(method.getModifiers())) {
                String name;

                // explicity expose a method with a special name
                if(method.isAnnotationPresent(ExposeAs.class)) {
                    ExposeAs anno = method.getAnnotation(ExposeAs.class);
                    name = anno.value();
                }
                else {
                    name = method.getName();
                }

                object.set(name, new NativeFunction(method));
            }
        }
        
        return object;
    }
}