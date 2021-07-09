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
            for(Value<?> value: av.value()){
                if(value instanceof IntegerValue && v instanceof IntegerValue){
                    IntegerValue iv = (IntegerValue) v;
                    int idValue = iv.value();
                    IntegerValue iv2 = (IntegerValue) value;
                    int valueDoArray = iv2.value();
                    if(idValue > valueDoArray){
                        for(int i = idValue; i < valueDoArray; i++){
                            cmds.execute();
                        }
                    }
                    else{
                        for(int i = idValue; i > valueDoArray; i--){
                            cmds.execute();
                        }
                    }
                }
            }
        }
        else{
            Utils.abort(super.getLine());
        }
    }
}
