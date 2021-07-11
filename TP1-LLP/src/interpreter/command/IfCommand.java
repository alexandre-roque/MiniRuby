package interpreter.command;

import interpreter.expr.BoolExpr;

public class IfCommand extends Command{
    private BoolExpr cond;
    private Command thenCmds;
    private Command elseCmds;
    
    public Command getElseCmds() {
        return elseCmds;
    }

    public IfCommand(int line, BoolExpr cond, Command thenCmds, Command elseCmds){
        super(line);
        this.cond = cond;
        this.thenCmds = thenCmds;
        this.elseCmds = elseCmds;
    }

    public void setElseCommands(Command elseCommand){
        this.elseCmds = elseCmds;
    }

    @Override
    public void execute() { 
        if(cond.expr() == true){
            thenCmds.execute();
        }
        else if(this.elseCmds != null){
            elseCmds.execute();
        }
    }
    
}
