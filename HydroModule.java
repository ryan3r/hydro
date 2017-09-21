import java.io.*;

/**
 * A module is a single file
 */
public class HydroModule {
    public static HydroObject obj = HydroObject.fromNative(HydroModule.class, ObjectProto.proto);
    
    /**
     * Reads a file
     */
    public static String readFile(String path) {
        try {
            FileReader reader = new FileReader(path);
            BufferedReader buffer = new BufferedReader(reader);

            String line, text = "";

            do {
                line = buffer.readLine();

                if(line != null) {
                    text += line + "\n";
                }
            } while(line != null);

            return text;
        }
        catch(Exception error) {
            throw new HydroError("Could not load " + path + " because " + error.getMessage());
        }
    }
    
    /**
     * Load and evaluate a module
     */
    public static HydroObject load(String path) {
        try {
            String file = readFile(path);
            HydroContext context = new HydroContext();

            // set the exports object
            Scope scope = context.getScope();
            scope.set("exports", new HydroObject(ObjectProto.proto));

            context.eval(file);

            // get the resulting exports
            return scope.get("exports");
        }
        catch(HydroError error) {
            error.print();
            System.exit(1);
            return null;
        }
    }
    
    /**
     * Expose load to hydro as require
     */
    public static HydroObject require(Evaluator eval, HydroObject self, HydroObject path) {
        if(path.typeof().equals("string")) {
            return load(HydroString.toJava(path));
        }
        else {
            throw new HydroError("Require must be called with a string");
        }
    }
}