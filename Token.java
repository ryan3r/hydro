
public class Token {
    private String name;
    private String value;
    
    public Token(String n, String v) {
        name = n;
        value = v;
    }
    
    public Token(String n) {
        this(n, n);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String n) {
         name = n;
    }
    
    public String getValue() {
        return value;
    }
    
    public String toString() {
        return name;
    }
}
