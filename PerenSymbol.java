
/**
 * The symbol for a parenthese
 */
public class PerenSymbol extends Symbol {
    public PerenSymbol() {
        super("(", 80);
    }
    
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        AstNode node = parser.expression(0, new String[] { ")" });
        
        // consume the closing )
        tokens.next();
        
        return node;
    }
}