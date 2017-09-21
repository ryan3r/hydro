import java.util.ArrayList;

/**
 * A representaion of a source file
 */
public class Source {
    private ArrayList<ArrayList<Token>> lines;
    
    public void setTokens(ArrayList<Token> tokens) {
        lines = new ArrayList<ArrayList<Token>>();
        
        // first line
        lines.add(new ArrayList<Token>());
        
        for(Token token : tokens) {
            // advance the line
            if(token.getName().equals("new-line")) {
                lines.add(new ArrayList<Token>());
            }
            // add the token and the comment
            else if(token.getName().equals("comment")) {
                lines.get(lines.size() - 1).add(token);
                lines.add(new ArrayList<Token>());
            }
            // add the token
            else {
                lines.get(lines.size() - 1).add(token);
            }
        }
        
        // check if there is an extra line
        if(lines.get(lines.size() - 1).size() == 0) {
            lines.remove(lines.size() - 1);
        }
    }
    
    // retreve a line
    public String getLine(int lineNo) {
        ArrayList<Token> line = lines.get(lineNo);
        String text = "";
        
        for(Token token : line) {
            String val = token.getValue();
            
            // remove the trailing new line from a comment
            if(token.getName().equals("comment")) {
                val = val.substring(0, val.length() - 1);
            }
            
            text += val;
        }
        
        return text;
    }
    
    // create an arrow to line up with a specific token
    public String getArrow(int lineNo, Token target) {
        ArrayList<Token> line = lines.get(lineNo);
        String text = "";
        
        for(Token token : line) {
            int length = token.getValue().length();
            
            for(int i = 0; i < length; ++i) {
                text += token == target ? "^" : " ";
            }
            
            if(token == target) {
                break;
            }
        }
        
        return text;
    }
    
    // find the line for the specified token
    public int findToken(Token target) {
        for(int i = 0; i < lines.size(); ++i) {
            ArrayList<Token> line = lines.get(i);
            
            if(line.contains(target)) {
                return i;
            }
        }
        
        return -1;
    }
    
    /**
     * Display the code around an error
     */
    public String display(Token target) {
        int line = findToken(target);
        int gutterSize = (Math.min(line + 2, lines.size()) + "").length();
        String text = "";
        
        // could not find the token
        if(line == -1) {
            System.out.println("Token not found: " + target);
        }
        
        // display two lines ahead
        if(line > 1) {
            text += createGutter(line - 2, gutterSize);
            text += getLine(line - 2);
            text += "\n";
        }
        
        // display one line ahead
        if(line > 0) {
            text += createGutter(line - 1, gutterSize);
            text += getLine(line - 1);
            text += "\n";
        }
        
        // draw the selected line
        text += createGutter(line, gutterSize);
        text += getLine(line);
        text += "\n";
        // draw the arrow
        text += createGutter(-1, gutterSize);
        text += getArrow(line, target);
        text += "\n";
        
        // display one line after
        if(line + 1 < lines.size()) {
            text += createGutter(line + 1, gutterSize);
            text += getLine(line + 1);
            text += "\n";
        }
        
        // display two lines after
        if(line + 2 < lines.size()) {
            text += createGutter(line + 2, gutterSize);
            text += getLine(line + 2);
            text += "\n";
        }
        
        return text;
    }
    
    /**
     * Create the gutter for the code
     */
    public String createGutter(int lineNo, int gutterSize) {
        // line numbers are one higher that our internal line numbers
        if(lineNo != -1) lineNo += 1;
        
        String gutter = " ";
        // size of the line number as a string
        int lineNoSize = lineNo == -1 ? 0 : (lineNo + "").length();
        
        // pad the line numbers so they are uniform
        for(int i = 0; i < (gutterSize - lineNoSize); ++i) {
            gutter += " ";
        }
        
        gutter += lineNo == -1 ? "" : lineNo;
        gutter += "| ";
        
        return gutter;
    }
}