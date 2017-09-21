import java.util.ArrayList;

/**
 * A basic wrapper for an array list that stores the index
 */
public class Consumable<T> {
    private ArrayList<T> list;
    private int index;
    
    public Consumable(ArrayList<T> li) {
        list = li;
        index = 0;
    }
    
    /**
     * View the current value don't advance
     */
    public T peek() {
        return list.get(index);
    }
    
    /**
     * Get the current value and move to the next
     */
    public T next() {
        return list.get(index++);
    }
    
    /**
     * Check if we can call next
     */
    public boolean hasNext() {
        return index < list.size() - 1;
    }
    
    /**
     * Check if we are at the end
     */
    public boolean isEnded() {
        return index == list.size();
    }
    
    /**
     * Get the last token in a consumable
     */
    public T last() {
        return list.get(list.size() - 1);
    }
    
    /**
     * Print 5 tokens with an ^ under the current token
     */
    public String toString() {
        String output = "", arrow = "";
        
        // add 2 tokens before
        if(index > 1) {
            String text = list.get(index - 2).toString() + " ";
            
            output += text;
            
            // pad the ^ s
            for(int i = 0; i < text.length(); ++i) {
                arrow += " ";
            }
        }
        
        // add one token before
        if(index > 0) {
            String text = list.get(index - 1).toString() + " ";
            
            output += text;
            
            // pad the ^ s
            for(int i = 0; i < text.length(); ++i) {
                arrow += " ";
            }
        }
        
        // add the current token
        String text = list.get(index).toString();
            
        output += text + " ";

        // add the ^ s
        for(int i = 0; i < text.length(); ++i) {
            arrow += "^";
        }
        
        // add the next token
        if(hasNext()) {
            output += list.get(index + 1) + " ";
        }
        
        // add the token 2 tokens ahead
        if(index < list.size() - 2) {
            output += list.get(index + 2);
        }
        
        return output + "\n" + arrow;
    }
}
