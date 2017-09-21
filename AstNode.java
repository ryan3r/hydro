import java.util.ArrayList;

public abstract class AstNode {
    private String name;
    private Token line;
    
    public AstNode(String n) {
        name = n;
    }
    
    public String getName() {
        return name;
    }
    
    public String toString() {
        return name;
    }
    
    public Token getLineNumber() {
        return line;
    }
    
    public void setLineNumber(Token l) {
        line = l;
    }
    
    public abstract void build(ArrayList<Command> commands);
}
