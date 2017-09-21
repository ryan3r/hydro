
/**
 * A symbol representing and Id in the none position
 */
public class IdNoneSymbol extends Symbol {
    /**
     * Sets this symbol for id tokens with a the highest binding power
     */
    public IdNoneSymbol() {
        super("id", 90);
    }
    
    /**
     * Parse a stream of tokens starting with an id
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        return new IdNode(self.getValue());
    }
}
