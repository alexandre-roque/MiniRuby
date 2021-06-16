package interpreter.expr;

import interpreter.value.Value;

public class InputExpr extends Expr{
    private InputOp op;

    public InputExpr(int line, InputOp op) {
        super(line);
        this.op = op;
    }
    
    @Override
    public Value<?> expr() {
        // TODO 
        return null;
    }

    
}
