package interpreter.expr;

import interpreter.value.Value;

public class BinaryExpr extends Expr{

    public BinaryExpr(int line, Expr left, BinaryOp op, Expr right) {
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }

    private Expr left;
    private BinaryOp op;
    private Expr right;
    

    @Override
    public Value<?> expr() {
        Value<?> v = null;
        if(this.op == BinaryOp.AddOp){
            
        }
        


        return v;
    }
    
    
}
