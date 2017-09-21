import java.util.ArrayList;

/**
 * The node for the return expression
 */

public class ReturnNode extends AstNode {
    private AstNode node;
    
    public ReturnNode(AstNode n) {
        super("return");
        node = n;
    }
    
    public void build(ArrayList<Command> commands) {
        // run the expression first if one exists
        if(node != null) {
            node.build(commands);
        }
        
        commands.add(new ReturnCommand());
    }
}