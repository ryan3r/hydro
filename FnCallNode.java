import java.util.ArrayList;

/**
 * The node for a function call
 */
public class FnCallNode extends AstNode {
    private AstNode target;
    private ArrayList<AstNode> args;
    
    public FnCallNode(AstNode t, ArrayList<AstNode> a) {
        super("FnCall");
        target = t;
        args = a;
    }
    
    public void build(ArrayList<Command> commands) {
        if(target.getName().equals("dot")) {
            DotNode dotTarget = (DotNode) target;
            
            dotTarget.buildOperand(commands);
            
            for(AstNode arg : args) {
                arg.build(commands);
            }
            
            commands.add(new FunctionCallCommand(args.size(), dotTarget.getValue(), true, target.getLineNumber()));
        }
        else {
            target.build(commands);

            for(AstNode arg : args) {
                arg.build(commands);
            }
            
            commands.add(new FunctionCallCommand(args.size(), null, false, target.getLineNumber()));
        }
    }
}