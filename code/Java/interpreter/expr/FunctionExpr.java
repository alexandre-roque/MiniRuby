package interpreter.expr;

import interpreter.value.Value;

public class FunctionExpr extends Expr{
    private Expr expr;
    private FunctionOp op;
    
    public FunctionExpr(int line, Expr expr, FunctionOp op) {
        super(line);
        this.expr = expr;
        this.op = op;
    }
    
    @Override
    public Value<?> expr() {
        // TODO Auto-generated method stub
        return null;
    }

}
