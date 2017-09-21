import java.util.ArrayList;

/**
 * Node for the dot operator
 */
public class DotNode extends AstNode {
    private AstNode operand;
    private String value;
    
    public DotNode(AstNode op, String v) {
        super("dot");
        operand = op;
        value = v;
    }
    
    /**
     * Get the operand
     */
    public void buildOperand(ArrayList<Command> commands) {
        operand.build(commands);
    }
    
    /**
     * Get the value
     */
    public String getValue() {
        return value;
    }
    
    public void build(ArrayList<Command> commands) {
        buildOperand(commands);
        commands.add(new FieldLookupCommand(value));
    }
}