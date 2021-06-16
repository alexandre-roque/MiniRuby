package interpreter.expr;

import interpreter.value.Value;

public class NegExpr extends Expr{

    private Expr expr;

    public NegExpr(int line, Expr expr) {
        super(line);
        this.expr = expr;
    }

    @Override
    public Value<?> expr() {
        // TODO 
        return null;
    }

}
