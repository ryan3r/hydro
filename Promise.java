import java.util.ArrayList;

/**
 * A value which will be resolved in the future
 */
public class Promise {
    private HydroObject value;
    private boolean resolved;
    private ArrayList<PromiseHandler> handlers;
    private ArrayList<Promise> children;
    
    public Promise() {
        resolved = false;
        
        handlers = new ArrayList<PromiseHandler>();
        children = new ArrayList<Promise>();
    }
    
    public Promise(HydroObject obj) {
        handlers = new ArrayList<PromiseHandler>();
        children = new ArrayList<Promise>();
        
        resolve(obj);
    }
    
    public void resolve(HydroObject obj) {
        resolved = true;
        value = obj;
        
        while(handlers.size() > 0) {
            PromiseHandler handler = handlers.remove(0);
            Promise child = children.remove(0);
            
            child.resolve(handler.handle(value));
        }
    }
    
    public void resolve(Promise promise) {
        final Promise self = this;
        
        promise.then(new PromiseHandler() {
            public HydroObject handle(HydroObject value) {
                self.resolve(value);
                return null;
            }
        });
    }
    
    public Promise then(PromiseHandler handler) {
        if(resolved) {
            return new Promise(handler.handle(value));
        }
        else {
            Promise child = new Promise();
            
            handlers.add(handler);
            children.add(child);
            
            return child;
        }
    }
    
    public HydroObject unwrap() {
        if(resolved) {
            return value;
        }
        
        throw new Error("Unresolved promise unwrapped");
    }
}