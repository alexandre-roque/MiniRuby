package interpreter.command;

import interpreter.expr.BoolExpr;

public class IfCommand extends Command{
    private BoolExpr cond;
    private Command thenCmds;
    private Command elseCmds;

    public IfCommand(int line, BoolExpr cond, Command thenCmds, Command elseCmds){
        super(line);
        this.cond = cond;
        this.thenCmds = thenCmds;
        this.elseCmds = elseCmds;
    }

    public void setElseCommands(Command elseCommand){

    }

    @Override
    public void execute() { //TODO
        
    }
    
}
