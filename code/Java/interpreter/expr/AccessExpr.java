package interpreter.expr;

import interpreter.value.Value;

public class AccessExpr extends SetExpr{

    private Expr base;
    private Expr index;


    public AccessExpr(int line, Expr base, Expr index) {
        super(line);
        this.base = base;
        this.index = index;
    }
    

    @Override
    public Value<?> expr() {
        // TODO 
        return null;
    }

    @Override
    public void setValue(Value<?> value) {
        // TODO 
    }
    
}
