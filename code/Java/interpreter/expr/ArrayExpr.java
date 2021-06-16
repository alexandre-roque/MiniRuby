package interpreter.expr;

import java.util.List;

import interpreter.value.Value;

public class ArrayExpr extends Expr{
    private List<Expr> exprs;

    public ArrayExpr(int line, List<Expr> exprs) {
        super(line);
        this.exprs = exprs;
    }

    @Override
    public Value<?> expr() {
        // TODO 
        return null;
    }

}
