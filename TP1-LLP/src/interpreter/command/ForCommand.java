package interpreter.command;


import interpreter.expr.Expr;
import interpreter.expr.Variable;
import interpreter.util.Utils;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.Value;

public class ForCommand extends Command {
    private Variable var;
    private Expr expr;
    private Command cmds;

    public ForCommand(int line, Variable var, Expr expr, Command cmds){
        super(line);
        this.var = var;
        this.expr = expr;
        this.cmds = cmds;
    }


    @Override
    public void execute() { 
        Value<?> v = var.expr();
        if(expr.expr() instanceof ArrayValue){
            ArrayValue av = (ArrayValue)expr.expr();
            IntegerValue iv = (IntegerValue) av.value().get(0);
            int inicio = iv.value();
            IntegerValue iv2 = (IntegerValue) av.value().get(av.value().size() -1);
            int fim = iv2.value();

            if(inicio<fim){
                for(int i = inicio; i<=fim; i++){
                    this.var.setValue(new IntegerValue(i));
                    cmds.execute();
                }
            }
            else{
                for(int i = inicio; i>=fim; i--){
                    this.var.setValue(new IntegerValue(i));
                    cmds.execute();
                }
            }
        }
        else{
            Utils.abort(super.getLine());
        }
    }
}
