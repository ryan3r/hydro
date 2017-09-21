import java.util.ArrayList;

/**
 * A symbol representing an if expression
 */
public class IfSymbol extends Symbol {
    public IfSymbol() {
        super("keyword-if", 60);
    }
    
    /**
     * Parse a stream of tokens starting with an if
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        AstNode cond = parser.expression(0, new String[] { "{" });
        ArrayList<AstNode> nodes = parser.block();
        ArrayList<AstNode> elseNodes = null;
        
        if(tokens.peek().getName().equals("keyword-else")) {
            tokens.next();
            
            elseNodes = parser.block();
        }
        
        return new IfNode(cond, nodes, elseNodes);
    }
}