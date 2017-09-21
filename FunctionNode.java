import java.util.ArrayList;

/**
 * Node for a function
 */
public class FunctionNode extends AstNode {
    private String[] args;
    private ArrayList<AstNode> nodes;
    
    
    public FunctionNode(String[] a, ArrayList<AstNode> n) {
        super("function");
        args = a;
        nodes = n;
    }
    
    public void build(ArrayList<Command> commands) {
        ArrayList<Command> fnCommands = new ArrayList<Command>();
        
        for(AstNode node : nodes) {
            node.build(fnCommands);
        }
        
        commands.add(new FunctionCommand(args, fnCommands));
    }
}
