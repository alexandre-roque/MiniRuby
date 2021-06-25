package interpreter.expr;
import interpreter.util.Utils;
import interpreter.value.IntegerValue;
import interpreter.value.Value;

public class ConvExpr extends Expr{

    private Expr expr;
    private ConvOp op;

    public ConvExpr(int line, ConvOp op, Expr expr) {
        super(line);
        this.op = op;
        this.expr = expr;
    }

    @Override
    public Value<?> expr() {
        Value<?> v = expr.expr();
        if(!(v instanceof IntegerValue)){
            Utils.abort(super.getLine());
        }

        if(op == ConvOp.MinusOp){
            IntegerValue iv = (IntegerValue) v;
            int n = iv.value();
            IntegerValue neg = new IntegerValue(-n); 
            v = neg;
        }

        return v;
    }

}
