import java.util.ArrayList;

/**
 * The Ast node for a string
 */
public class StringNode extends AstNode {
    private String value;
    
    public StringNode(String n) {
        super("number");
        value = n;
    }
    
    public void build(ArrayList<Command> commands) {
        commands.add(new LoadString(value));
    }
}