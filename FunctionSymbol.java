import java.util.ArrayList;

/**
 * The symbol for a function definition
 */
public class FunctionSymbol extends Symbol {
    public FunctionSymbol() {
        super("keyword-func", 80);
    }
    
    /**
     * Parse a stream of tokens starting with a function definition
     */
    public AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser) {
        if(!tokens.hasNext() || !tokens.peek().getName().equals("(")) {
            throw new HydroError("Expected a (", self);
        }
        
        ArrayList<String> arguments = new ArrayList<String>();
        
        tokens.next();
        
        // take an identifier and a coma
        while(tokens.hasNext() && !tokens.peek().getName().equals(")")) {
            Token varName = tokens.peek();
            
            if(!varName.getName().equals("id")) {
                throw new HydroError("Expected an identifier", tokens.peek());
            }
            
            arguments.add(varName.getValue());
            
            if(!tokens.hasNext()) {
                throw new HydroError("Unexpected end of input", tokens.peek());
            }
            
            tokens.next();
            
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
        
        
        String[] args = arguments.toArray(new String[arguments.size()]);
        ArrayList<AstNode> nodes = parser.block();
        
        
        return new FunctionNode(args, nodes);
    }
}
