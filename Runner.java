
/**
 * Entry point for the application runs the specified file
 */
public class Runner {
    public static void main(String[] args) {
        String file;
        
        // load the repl by default
        if(args.length == 0) {
            file = "demos.txt";
        }
        else {
            file = args[0];
        }
        
        // load the standard library
        HydroModule.load("stdlib.txt");
        // load the file
        HydroModule.load(file);
    }
}