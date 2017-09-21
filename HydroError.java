
/**
 * A syntax error and the location where it occured
 */
public class HydroError extends Error {
    private Token location;
    private Source source;
    
    public HydroError(String msg, Token loc) {
        super(msg);
        location = loc;
    }
    
    public HydroError(String msg) {
        this(msg, null);
    }
    
    /**
     * Set the text for the error
     */
    public void setSource(Source s) {
        source = s;
    }
    
    /**
     * Print out the error message and the location from the source code
     */
    public void print() {
        // print the error message with a line location
        if(location != null) {
            String text = source.display(location);
        
            System.out.println("Error: " + getMessage() + "\n\n" + text);
        }
        // just print the message
        else {
            System.out.println("Error: " + getMessage());
        }
    }
}
