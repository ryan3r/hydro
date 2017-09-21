import java.util.ArrayList;

/**
 * The node for the assignment operator
 * 
 * Assignments syntactialy work like any operator 
 * except they are right-to-left associated
 */

public class AssignmentNode extends AstNode {
    private AstNode operand;
    private AstNode name;
    
    public AssignmentNode(AstNode n, AstNode op) {
        super("assignment");
        
        name = n;
        operand = op;
    }
    
    public String toString() {
        return "(" + name.toString() + " = " + operand.toString() + ")";
    }
    
    public void build(ArrayList<Command> commands) {
        if(name.getName().equals("dot")) {
            DotNode op = (DotNode) name;
            
            op.buildOperand(commands);
            operand.build(commands);
            commands.add(new PropAssignmentCommand(op.getValue()));
        }
        else {
            operand.build(commands);
            commands.add(new AssignmentCommand(((IdNode) name).getValue()));
        }
    }
}