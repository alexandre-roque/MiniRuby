package interpreter.command;

import interpreter.expr.BoolExpr;

public class WhileCommand extends Command {
    private BoolExpr cond;
    private Command cmds;

    public WhileCommand(int line, BoolExpr cond, Command cmds){
        super(line);
        this.cond = cond;
        this.cmds = cmds;
    }

    @Override
    public void execute() { 
        while(cond.expr()){
            cmds.execute();
        }
    }
}
    // Se até a condição ser falsa
