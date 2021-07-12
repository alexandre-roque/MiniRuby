package interpreter.expr;

import interpreter.util.Utils;
import interpreter.value.Value;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.ArrayValue;
import java.util.Vector;

public class SingleBoolExpr extends BoolExpr{
    
    private Expr left;
    private RelOp op;
    private Expr right;

    public SingleBoolExpr(int line, Expr left, RelOp op, Expr right){
        super(line);
        this.left = left;
        this.op = op;
        this.right = right;
    }

    @Override
    public boolean expr(){
        Value<?> v1 = left.expr();
        Value<?> v2 = right.expr();
        Vector<Value<?>> values = new Vector<>();

        if((left.expr() instanceof IntegerValue) && (right.expr() instanceof IntegerValue)){
            IntegerValue iv1 = (IntegerValue) v1;
            IntegerValue iv2 = (IntegerValue) v2;
            int valor1, valor2;
            valor1 = iv1.value();
            valor2 = iv2.value();

            switch (op) {
                case EqualsOp:
                    return (valor1 == valor2);
                case NotEqualsOp:
                    return (valor1 != valor2);
                case LowerThanOp:
                    return (valor1 < valor2);
                case LowerEqualOp:
                    return (valor1 <= valor2);
                case GreaterThanOp:
                    return (valor1 > valor2);
                case GreaterEqualOp:
                    return (valor1 >= valor2);
                default:
                    Utils.abort(super.getLine());
                    return true;
            }
        }

        if((left.expr() instanceof StringValue) && (right.expr() instanceof StringValue)){
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s1, s2;
            s1 = sv1.value();
            s2 = sv2.value();
            switch (op) {
                case EqualsOp:
                    return (s1.equals(s2));
                case NotEqualsOp:
                    return (!(s1.equals(s2)));
                case ContainsOp:
                    return (s1.contains(s2));
                default:
                    Utils.abort(super.getLine());
                    return true;
            }
        }

        if(left.expr() instanceof ArrayValue){
            switch (op) {
                case ContainsOp:
                    values = ((ArrayValue) left.expr()).value();
                    for(Value<?> value:values){
                        if(value.value().equals(right.expr().value())){
                            return true;
                        }
                    }
                    return false;
            }
        }
        
        if(right.expr() instanceof ArrayValue){
            switch (op) {
                case ContainsOp:
                    values = ((ArrayValue) right.expr()).value();
                    for(Value<?> value:values){
                        if(value.value().equals(left.expr().value())){
                            return true;
                        }
                    }
                    return false;
            }
        }

        return false;
    }
}

    // EqualsOp, Integer, String
    // NotEqualsOp, Integer, String
    // LowerThanOp, Integer
    // LowerEqualOp, Integer
    // GreaterThanOp, Integer
    // GreaterEqualOp, Integer
    // ContainsOp; String, ArrayValue