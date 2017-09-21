import java.util.ArrayList;

/**
 * The ast node for an operator
 * Defined as: expression id expression
 */

public class OperatorNode extends AstNode {
    private String name;
    private AstNode[] operands;
    
    public OperatorNode(String n, AstNode op1, AstNode op2) {
        super("operator");
        name = n;
        operands = new AstNode[] {
            op1,
            op2
        };
    }
    
    public String toString() {
        return "(" + operands[0].toString() + " " + name + " " + operands[1].toString() + ")";
    }
    
    public void build(ArrayList<Command> commands) {
        operands[0].build(commands);
        operands[1].build(commands);
        commands.add(new FunctionCallCommand(1, name, true, getLineNumber()));
    }
}