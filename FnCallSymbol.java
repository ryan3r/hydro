import java.util.ArrayList;

/**
 * The symbol for a function definition
 */
public class FnCallSymbol extends Symbol {
    public FnCallSymbol() {
        super("(", 70);
    }
    
    /**
     * Parse a stream of tokens starting with a function definition
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        ArrayList<AstNode> arguments = new ArrayList<AstNode>();
        
        // take an identifier and a coma
        while(tokens.hasNext()) {
            AstNode expr = parser.expression(0, new String[] { ",", ")" });
            
            if(expr != null) {
                arguments.add(expr);
            }
            
            if(tokens.peek().getName().equals(")")) {
                break;
            }
            
            if(tokens.peek().getName().equals(",")) {
                tokens.next();
            }
            else {
                throw new HydroError("Expected a ,", tokens.peek());
            }
        }
        
        if(!tokens.peek().getName().equals(")")) {
            throw new HydroError("Expected a ) but found a " + tokens.peek().getName(), tokens.peek());
        }
        
        tokens.next();
        
        return new FnCallNode(left, arguments);
    }
}
