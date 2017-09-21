import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {
    private ArrayList<Token> tokens;
    private String text;
    private int index;
    private Matcher[] matchers;
    
    
    private final char[] RAW_TOKENS = {
        ',',
        '(',
        ')',
        '[',
        ']',
        '.',
        ';',
        '{',
        '}'
    };
    
    private final String[] MATCHER_NAMES = {
        "number",
        "id",
        "new-line",
        "whitespace",
        "comment"
    };
    
    private final Pattern[] MATCHER_EXPRS = {
        Pattern.compile("[0-9]+(?:\\.[0-9]+)?"),
        Pattern.compile("[A-Za-z!%\\^*$&\\-_+=|/<>]+"),
        Pattern.compile("\\n"),
        Pattern.compile("[\\t ]"),
        Pattern.compile("#.*(?:\\n|$)?")
    };
    
    private final String[] REMOVE_TOKENS = {
        "whitespace",
        "comment",
        "new-line"
    };
    
    private final String[] KEYWORDS = {
        "func",
        "if",
        "else",
        "return"
    };
    
    public Tokenizer(String txt) {
        text = txt;
        tokens = new ArrayList<Token>();
        index = 0;
        
        // precompile regex matches
        matchers = new Matcher[MATCHER_EXPRS.length];
        
        for(int i = 0; i < MATCHER_EXPRS.length; ++i) {
            matchers[i] = MATCHER_EXPRS[i].matcher(text);
        }
    }
    
    public ArrayList<Token> tokenize(Source source) {
        ArrayList<Token> sourceTokens = new ArrayList<Token>();
        
        while(index < text.length()) {
            Token token = getToken();
            
            if(token == null) {
                throw new HydroError("Unexpected text: " + text.substring(index), token);
            }
            
            sourceTokens.add(token);
            
            // check if this token should be ignored
            if(shouldRemove(token)) continue;
            
            // find key words
            if(token.getName() == "id") {
                for(String keyword : KEYWORDS) {
                    if(token.getValue().equals(keyword)) {
                        token.setName("keyword-" + token.getValue());
                    }
                }
            }
            
            tokens.add(token);
        }
        
        source.setTokens(sourceTokens);
        
        return tokens;
    }
    
    private Token getToken() {
        // assignments can only have one =
        if(text.charAt(index) == '=' && index + 1 < text.length() && text.charAt(index + 1) != '=') {
            ++index;
            return new Token("=");
        }
        
        for(char tokenText : RAW_TOKENS) {
            if(text.charAt(index) == tokenText) {
                ++index;
                return new Token(tokenText + "");
            }
        }
        
        for(int i = 0; i < matchers.length; ++i) {
            Matcher matcher = matchers[i];
            
            if(matcher.find(index) && matcher.start() == index) {
                int length = matcher.end() - matcher.start();
                index += length;
                
                String match = text.substring(matcher.start(), matcher.end());
                
                return new Token(MATCHER_NAMES[i], match);
            }
        }
        
        if(text.charAt(index) == '"') {
            return getString();
        }
        
        return null;
    
    }
    
    private Token getString() {
        ++index; // opening "
        String string = "";
        
        while(index < text.length() && text.charAt(index) != '"') {
            char ch = text.charAt(index);
            
            string += ch;
            ++index;
        }
        
        if(index == text.length()) {
            throw new HydroError("Unclosed string (end of file)");
        }
        
        ++index; // closing "
        
        return new Token("string", string);
    }
    
    /**
     * Check if a token should be removed
     */
    private boolean shouldRemove(Token token) {
        for(int i = 0; i < REMOVE_TOKENS.length; ++i) {
            if(REMOVE_TOKENS[i].equals(token.getName())) {
                return true;
            }
        }
        
        return false;
    }
}