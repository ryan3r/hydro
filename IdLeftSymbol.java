
/**
 * A symbol representing and Id in the none position (in other words an operator)
 */
public class IdLeftSymbol extends Symbol {
    /**
     * Sets this symbol for id tokens with a the highest binding power
     */
    public IdLeftSymbol() {
        super("id", 60);
    }
    
    /**
     * Parse a stream of tokens starting with an id
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        AstNode node = parser.expression(60);
        
        if(node == null) {
            throw new HydroError("Unexpected end of input", tokens.last());
        }
        
        return new OperatorNode(self.getValue(), left, node);
    }
}
