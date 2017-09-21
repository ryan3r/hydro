
/**
 * Command for a return
 */
class ReturnCommand implements Command {
    public void exec(Stack stack, Scope scope, Evaluator eval) {
        eval.goTo(-1);
    }
}