
/**
 * A symbol representing an return
 */
public class ReturnSymbol extends Symbol {
    /**
     * Sets this symbol for = tokens with a low binding power
     */
    public ReturnSymbol() {
        super("keyword-return", 40);
    }
    
    /**
     * Parse a stream of tokens starting with an id
     * 
     * Restrict the left hand side to Identifiers
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        AstNode node = parser.expression(0);
        
        return new ReturnNode(node);
    }
}
