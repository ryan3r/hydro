
/**
 * A symbol representing a string
 */
public class StringSymbol extends Symbol {
    public StringSymbol() {
        super("string", 80);
    }
    
    /**
     * Parse a stream of tokens starting with an number
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        return new StringNode(self.getValue());
    }
}
