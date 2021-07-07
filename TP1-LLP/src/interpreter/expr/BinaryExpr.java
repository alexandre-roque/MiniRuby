package interpreter.expr;

import java.util.Vector;

import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
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
        Value<?> v1 = left.expr();
        Value<?> v2 = right.expr();

        if((left.expr() instanceof IntegerValue) && (right.expr() instanceof IntegerValue)){
            IntegerValue iv1 = (IntegerValue) v1;
            IntegerValue iv2 = (IntegerValue) v2;
            int valor1, valor2;
            valor1 = iv1.value();
            valor2 = iv2.value();
            Vector<Value<?>> values = new Vector<>();

            switch (op) {
                case RangeWithOp:
                    values = new Vector<>();
                    if(valor1>valor2){
                        for(int i = valor1; i < valor2; i++){
                            IntegerValue auxI = new IntegerValue(i);
                            values.add(auxI);
                        }
                    }
                    else{
                        for(int i = valor2; i > valor2; i--){
                            IntegerValue auxI = new IntegerValue(i);
                            values.add(auxI);
                        }
                    }
                    return (new ArrayValue(values));
                case RangeWithoutOp:
                    values = new Vector<>();
                    if(valor1>valor2){
                        for(int i = valor1; i <= valor2; i++){
                            IntegerValue auxI = new IntegerValue(i);
                            values.add(auxI);
                        }
                    }
                    else{
                        for(int i = valor2; i >= valor2; i--){
                            IntegerValue auxI = new IntegerValue(i);
                            values.add(auxI);
                        }
                    }
                   return (new ArrayValue(values));
                    case AddOp: 
                        return (new IntegerValue(valor1+valor2));
                    case SubOp:
                        return (new IntegerValue(valor1-valor2));
                    case MultOp:
                        return (new IntegerValue(valor1*valor2));   
                    case DivOp:
                        return (new IntegerValue(valor1/valor2)); 
                    case ModOp:
                        return (new IntegerValue(valor1%valor2)); 
                    case ExpOp:
                    default:
                        return (new IntegerValue(valor1^valor2));                         
            }
        }

        if((left.expr() instanceof StringValue) && (right.expr() instanceof StringValue)){
            StringValue sv1 = (StringValue) v1;
            StringValue sv2 = (StringValue) v2;
            String s1, s2;
            s1 = sv1.value();
            s2 = sv2.value();
            switch (op) {
                case AddOp: 
                    return (new StringValue(s1.concat(s2)));
            }
        }
        
        return null;
    }
}
    


    // RangeWithOp,
    // RangeWithoutOp,
    // AddOp,
    // SubOp,
    // MultOp,
    // DivOp,
    // ModOp,
    // ExpOp;  