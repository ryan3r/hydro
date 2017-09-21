
public abstract class Symbol {
    private String name;
    private int power;
    private boolean rightToLeft;
    
    public Symbol(String n, int pow, boolean rtl) {
        name = n;
        power = pow;
        rightToLeft = rtl;
    }
    
    public Symbol(String n, int pow) {
        this(n, pow, false);
    }
    
    public int getPower() {
        return power;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean getRightToLeft() {
        return rightToLeft;
    }
    
    public abstract AstNode parse(AstNode left, Token self, Consumable<Token> tokens, Parser parser);
}
