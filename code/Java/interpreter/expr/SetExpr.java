package interpreter.expr;

import interpreter.value.Value;

public abstract class SetExpr extends Expr{

    protected SetExpr(int line) {
        super(line);
        //TODO 
    }

    @Override
    public abstract Value<?> expr();

    public abstract void setValue(Value<?> value);
    
}
