import java.util.Scanner;

/**
 * The system namespace
 */
public abstract class HydroSystem {
    public static HydroObject obj = HydroObject.fromNative(HydroSystem.class, ObjectProto.proto);
    
    // the scanner instance used by read line (lazily loaded)
    private static Scanner scanner = null;
    
    /**
     * Exit the program
     */
    public static HydroObject exit() {
        System.exit(0);
        return HydroUndefined.create();
    }
    
    /**
     * Print text to the console
     */
    public static Promise print(Evaluator eval, HydroObject self, HydroObject obj) {
        if(obj == null) {
		System.out.println();
		return new Promise(HydroUndefined.create());
	}
	
        return obj.get("toString")
            .exec(eval, obj, null)
            
            .then(new PromiseHandler() {
                public HydroObject handle(HydroObject value) {
                    System.out.println(HydroString.toJava(value));
                    return HydroUndefined.create();
                }
            });
    }
    
    /**
     * Read a line from the console
     */
    public static HydroObject readLine(Evaluator eval, HydroObject self, HydroObject obj) {
        if(scanner == null) {
            scanner = new Scanner(System.in);
        }
        
        // print a prompt that is passed in
        if(obj.typeof().equals("string")) {
            System.out.print(HydroString.toJava(obj));
        }
        
        return HydroString.create(scanner.nextLine());
    }
    
    /**
     * Create a HydroContext
     */
    public static HydroObject createContext() {
        return HydroContextInterface.create();
    }
    
    /**
     * Generate a random number
     */
    public static HydroObject random(Evaluator eval, HydroObject self, HydroObject lowerObj, HydroObject upperObj) {
        Double lower = HydroNumber.toJava(lowerObj);
        Double upper = HydroNumber.toJava(upperObj);
        
        if(lower == null || upper == null) {
            return HydroNumber.create(null);
        }
        
        return HydroNumber.create((int) (Math.random() * upper) + lower);
    }
}
