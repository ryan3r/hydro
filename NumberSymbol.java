
/**
 * A symbol representing a number
 */
public class NumberSymbol extends Symbol {
    public NumberSymbol() {
        super("number", 80);
    }
    
    /**
     * Parse a stream of tokens starting with an number
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        return new NumberNode(self.getValue());
    }
}
