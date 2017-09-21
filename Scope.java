import java.util.HashMap;

/**
 * Scope for a block
 */
public class Scope {
    private HydroObject scope;
    private Scope parent;
    
    /**
     * Create a new scope with a parent scope
     */
    public Scope(Scope p) {
        parent = p;
        scope = new HydroObject(p.toObject());
    }
    
    /**
     * Create a scope with no parent
     */
    public Scope() {
        parent = null;
        scope = new HydroObject();
    }
    
    /**
     * Get a variable from the scope
     */
    public HydroObject get(String name) {
        // search this scope
        return scope.get(name);
    }
    
    /**
     * Store a value in this scope
     */
    public void set(String name, HydroObject value) {
        // store the value on this scope if it has been defined here or
        // if it has not been defined in any parent scope
        if(scope.hasOwn(name) || !scope.has(name)) {
            scope.set(name, value);
        }
        
        // other wise store it in a parent scope
        if(parent != null) {
            parent.set(name, value);
        }
    }
    
    /**
     * Create a child of this scope
     */
    public Scope create() {
        return new Scope(this);
    }
    
    /**
     * Get the internal store
     */
    public HydroObject toObject() {
        return scope;
    }
}