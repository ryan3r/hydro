
/**
 * Symbol for a dot operator
 */
public class DotSymbol extends Symbol {
    public DotSymbol() {
        super(".", 80);
    }
    
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        if(!tokens.peek().getName().equals("id")) {
            throw new HydroError("Expected an identifier", tokens.peek());
        }
        
        String value = tokens.next().getValue();
        
        return new DotNode(left, value);
    }
}