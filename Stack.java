import java.util.ArrayList;

/**
 * Value stack for function values
 */
public class Stack {
    private ArrayList<Object> list;
    
    public Stack() {
        list = new ArrayList<Object>();
    }
    
    /**
     * Get the top value off the stack
     */
    public HydroObject peek() {
        if(list.size() < 1) {
            throw new Error("Stack empty");
        }
        
        HydroObject value;
        int index = list.size() - 1;
        
        if(list.get(index) instanceof Promise) {
            value = ((Promise) list.get(index)).unwrap();
        }
        else {
            value = (HydroObject) list.get(index);
        }
        
        return value;
    }
    
    /**
     * Get the top value off the stack
     */
    public HydroObject pop() {
        HydroObject value = peek();
        
        if(list.size() > 0) list.remove(list.size() - 1);
        
        return value;
    }
    
    /**
     * Get a promise for the value
     */
    public Promise peekPromise() {
         if(list.size() < 1) return null;
        
        Promise value;
        int index = list.size() - 1;
        
        if(list.get(index) instanceof Promise) {
            value = (Promise) list.get(index);
        }
        else {
            value = new Promise((HydroObject) list.get(index));
        }
        
        return value;
    }
    
     /**
     * Get the top value as a promise off the stack
     */
    public Promise popPromise() {
        Promise value = peekPromise();
        
        if(list.size() > 0) list.remove(list.size() - 1);
        
        return value;
    }
    
    public boolean empty() {
        return list.size() == 0;
    }
    
    /**
     * Add a value to the stack
     */
    public void push(HydroObject obj) {
        list.add(obj);
    }
    
    /**
     * Add a promise to the stack
     */
    public void push(Promise obj) {
        list.add(obj);
    }
}
