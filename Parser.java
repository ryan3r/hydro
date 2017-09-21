import java.util.ArrayList;

public class Parser {
    private Consumable<Token> tokens;
    private ArrayList<AstNode> nodes;
    private int index;
    
    private static final Symbol[] NONE_SYMBOLS = {
        new IdNoneSymbol(),
        new NumberSymbol(),
        new StringSymbol(),
        new FunctionSymbol(),
        new IfSymbol(),
        new ReturnSymbol(),
        new PerenSymbol()
    };
    
    private static final Symbol[] LEFT_SYMBOLS = {
        new IdLeftSymbol(),
        new AssignmentSymbol(),
        new FnCallSymbol(),
        new DotSymbol()
    };
    
    public Parser(ArrayList<Token> t) {
        tokens = new Consumable<Token>(t);
        index = 0;
        nodes = new ArrayList<AstNode>();
    }
    
    /**
     * Parse the tokens
     */
    public ArrayList<AstNode> parse() {
        while(!tokens.isEnded()) {
            AstNode result = expression(0);
            
            if(result == null) {
                throw new HydroError("Expected an expression", tokens.last());
            }
            
            nodes.add(result);
            
            if(!tokens.isEnded() && tokens.peek().getName().equals(";")) {
                tokens.next();
            }
            else {
                throw new HydroError("Unexpected end of input", tokens.last());
            }
        }
        
        return nodes;
    }
    
    /**
     * Parse a block of code
     */
    public ArrayList<AstNode> block() {
        ArrayList<AstNode> nodes = new ArrayList<AstNode>();
        
        if(!tokens.hasNext() || !tokens.next().getName().equals("{")) {
            throw new HydroError("Expected a {", tokens.peek());
        }
        
        while(!tokens.isEnded() && !tokens.peek().getName().equals("}")) {
            AstNode result = expression(0);
            
            if(result == null) {
                throw new HydroError("Expected an expression", tokens.last());
            }
            
            nodes.add(result);
            
            if(!tokens.isEnded() && tokens.peek().getName().equals(";")) {
                tokens.next();
            }
            else {
                throw new HydroError("Unexpected end of input", tokens.last());
            }
        }
        
        if(!tokens.hasNext() || !tokens.next().getName().equals("}")) {
            throw new HydroError("Expected a }", tokens.isEnded() ? tokens.last() : tokens.peek());
        }
        
        return nodes;
    }
   
    /**
     * Parse a single expression
     */
    public AstNode expression(int bindingPower) {
        return expression(bindingPower, new String[] { ";" });
    }
    
    /**
     * Parse a single expression
     */
    public AstNode expression(int bindingPower, String[] endToken) {
        Symbol symbol = none();
        
        // pass this along
        if(symbol == null) return null;
        
        // get the line number
        Token lineNumber = tokens.peek();
        
        AstNode left = symbol.parse(null, tokens.next(), tokens, this);
        
        // put the line number on the AstNode
        left.setLineNumber(lineNumber);
        
        // get the next left if we have tokens remaining
        symbol = this.left();
        
        while(
            symbol != null &&
            !arrayContains(endToken, tokens.peek().getName()) && 
            (symbol.getRightToLeft() ? symbol.getPower() >= bindingPower : symbol.getPower() > bindingPower)
        ) {
            // get the line number
            lineNumber = tokens.peek();
            
            left = symbol.parse(left, tokens.next(), tokens, this);
            
            symbol = this.left();
            
            // put the line number on the AstNode
            left.setLineNumber(lineNumber);
        }
        
        return left;
    }
    
    /**
     * Look up the none symbol for the current token
     * (does not consume the token)
     */
    public Symbol none() {
        if(tokens.isEnded()) return null;
        
        String current = tokens.peek().getName();
        
        for(int i = 0; i < NONE_SYMBOLS.length; ++i) {
            if(NONE_SYMBOLS[i].getName().equals(current)) {
                return NONE_SYMBOLS[i];
            }
        }
        
        return null;
    }
    
    /**
     * Look up the left symbol for the current token
     * (does not consume the token)
     */
    public Symbol left() {
        if(tokens.isEnded()) return null;
        
        String current = tokens.peek().getName();
        
        for(int i = 0; i < LEFT_SYMBOLS.length; ++i) {
            if(LEFT_SYMBOLS[i].getName().equals(current)) {
                return LEFT_SYMBOLS[i];
            }
        }
        
        return null;
    }
    
    /**
     * Check if an array contains a value
     */
    private static boolean arrayContains(Object[] arr, Object val) {
        for(Object obj : arr) {
            if(obj.equals(val)) {
                return true;
            }
        }
        
        return false;
    }
}
