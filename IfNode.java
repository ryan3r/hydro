import java.util.ArrayList;

/**
 * The node for the if statement
 */
public class IfNode extends AstNode {
    private AstNode cond;
    private ArrayList<AstNode> nodes;
    private ArrayList<AstNode> elseNodes;
    
    public IfNode(AstNode c, ArrayList<AstNode> n, ArrayList<AstNode> e) {
        super("if");
        cond = c;
        nodes = n;
        elseNodes = e;
    }
    
    public void build(ArrayList<Command> commands) {
        cond.build(commands);
        
        int condAt = commands.size();
        
        for(AstNode node : nodes) {
            node.build(commands);
        }
        
        // if there is no else block we just are adding the conitional so offset by 1
        // if there is an else there will also be a goto to skip the else block so offset by 2
        int jumpTo = commands.size() + (elseNodes != null ? 2 : 1);
        
        commands.add(condAt, new ConditionalGoto(jumpTo));
        
        // add an else block
        if(elseNodes != null) {
            int gotoEndAt = commands.size();
            
            for(AstNode node : elseNodes) {
                node.build(commands);
            }
            
            // after the if block skip the else block
            commands.add(gotoEndAt, new Goto(commands.size() + 1));
        }
    }
}