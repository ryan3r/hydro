
/**
 * Command to conditionaly jump to a place in the code
 * 
 * ONLY JUMPS IF THE VALUE ON THE STACK IS FALSE
 */
public class ConditionalGoto implements Command {
    private int jumpTo;
    
    public ConditionalGoto(int j) {
        jumpTo = j;
    }
    
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        HydroObject cond = stack.pop();
        
        if(cond.typeof().equals("boolean")) {
            if(!(boolean) cond.getInnerValue("boolean")) {
                eval.goTo(jumpTo);
            }
        }
        else {
            final Evaluator $eval = eval;
            
            cond.get("toBoolean")
            .exec(eval, cond, new HydroObject[] {})

            .then(new PromiseHandler() {
                public HydroObject handle(HydroObject cond) {
                    if(!(boolean) cond.getInnerValue("boolean")) {
                        $eval.goTo(jumpTo);
                    }
                    
                    return null;
                }
            });
        }
    }
}