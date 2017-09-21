
/**
 * A symbol representing an assignment
 */
public class AssignmentSymbol extends Symbol {
    /**
     * Sets this symbol for = tokens with a low binding power
     */
    public AssignmentSymbol() {
        super("=", 50, true);
    }
    
    /**
     * Parse a stream of tokens starting with an id
     * 
     * Restrict the left hand side to Identifiers
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        if(!left.getName().equals("id") && !left.getName().equals("dot")) {
            throw new HydroError("Invalid left hand side of assignment", left.getLineNumber());
        }
        
        AstNode node = parser.expression(50);
        
        if(node == null) {
            throw new HydroError("Unexpected end of input", tokens.last());
        }
        
        return new AssignmentNode(left, node);
    }
}
