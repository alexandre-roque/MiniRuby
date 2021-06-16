package interpreter.expr;

public class CompositeBoolExpr extends BoolExpr{
    private Expr left;
    private BoolOp op;
    private Expr right;

    public CompositeBoolExpr(int line, Expr left, BoolOp op, Expr right){
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public boolean expr(){ //TODO
        boolean bool = false;

        return bool;
    }
    
}
