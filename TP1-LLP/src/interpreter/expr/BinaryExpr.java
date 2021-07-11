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
        if((left.expr() instanceof IntegerValue) && (right.expr() instanceof IntegerValue)){
            int valor1 = ((IntegerValue) left.expr()).value();
            int valor2 = ((IntegerValue) right.expr()).value();
            Vector<Value<?>> values;

            switch (op) {
                case RangeWithOp:
                    values = new Vector<>();
                    if(valor1<valor2){
                        for(int i = valor1; i <= valor2; i++){
                            IntegerValue auxI = new IntegerValue(i);
                            values.add(auxI);
                        }
                    }
                    else{
                        for(int i = valor1; i >= valor2; i--){
                            IntegerValue auxI = new IntegerValue(i);
                            values.add(auxI);
                        }
                    }
                    return (new ArrayValue(values));
                case RangeWithoutOp:
                    values = new Vector<>();
                    if(valor1<valor2){
                        for(int i = valor1; i < valor2; i++){
                            IntegerValue auxI = new IntegerValue(i);
                            values.add(auxI);
                        }
                    }
                    else{
                        for(int i = valor1; i > valor2; i--){
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
            switch (op) {
                case AddOp:
                    
                    return (new StringValue(((StringValue) left.expr()).value().concat(((StringValue) right.expr()).value())));
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