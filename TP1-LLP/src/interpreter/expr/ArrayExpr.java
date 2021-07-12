package interpreter.expr;

import java.util.Vector;
import interpreter.value.ArrayValue;

import interpreter.value.Value;

public class ArrayExpr extends Expr{
    private Vector<Expr> exprs;

    public ArrayExpr(int line, Vector<Expr> exprs) {
        super(line);
        this.exprs = exprs;
    }

    @Override
    public Value<?> expr() {
        Vector<Value<?>> res = new Vector<>();

        for(Expr e:this.exprs){
                Value<?> v = e.expr();
                res.add(v);
        }
        
        ArrayValue av = new ArrayValue(res);
        return av;
    }

}

// Vector<Value<?>> res;
// foreach e in exprs:
//   Value<?> v = e.expr()
//   res.add(v)

// ArrayValue av = new ArrayValue(res);
// return av;