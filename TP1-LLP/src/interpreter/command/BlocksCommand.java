package interpreter.command;

import java.util.List;

public class BlocksCommand extends Command{
    
    private List<Command> cmds;

    public BlocksCommand(int line, List<Command> cmds){
        super(line);
        this.cmds = cmds;
    }

    @Override
    public void execute(){
        for(Command cmd: cmds)
            cmd.execute();
        //System.out.println("Isso é um bloco de comandos");
    }
}
    // Feito em sala