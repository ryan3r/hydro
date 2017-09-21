import java.util.ArrayList;

/**
 * The Ast node for a number
 */
public class NumberNode extends AstNode {
    private double value;
    
    public NumberNode(String num) {
        super("number");
        
        try {
            value = new Double(num);
        }
        catch(NumberFormatException e) {}
    }
    
    public void build(ArrayList<Command> commands) {
        commands.add(new LoadNumber(value));
    }
}