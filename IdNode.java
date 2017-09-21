import java.util.*;

/**
 * The ast node for an Identifier
 */
public class IdNode extends AstNode {
    private String value;
    
    public IdNode(String v) {
        super("id");
        value = v;
    }
    
    public String getValue() {
        return value;
    }
    
    public String toString() {
        return value;
    }
    
    public void build(ArrayList<Command> commands) {
        commands.add(new LoadVar(value));
    }
}