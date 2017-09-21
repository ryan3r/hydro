
/**
 * A command to be execute during evaluation
 */
public interface Command {
    public void exec(Stack stack, Scope scope, Evaluator eval);
}
