package interpreter.expr;

import java.util.Vector;
import interpreter.util.Utils;
import interpreter.value.Value;
import interpreter.value.ArrayValue;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;


public class AccessExpr extends SetExpr{

    private Expr base;
    private Expr index;


    public AccessExpr(int line, Expr base, Expr index) {
        super(line);
        this.base = base;
        this.index = index;
    }
    

    @Override
    public Value<?> expr() {
        Value<?> value = base.expr();
        if(this.index == null){
            return value;
        }
        else{
            int indexAux = 0;
            Value<?> indexValue = this.index.expr();
            Vector<Value<?>> values = (((ArrayValue) value).value());
            if(indexValue instanceof StringValue){
                indexAux = Integer.parseInt(((StringValue)indexValue).value());
            }
            if(indexValue instanceof IntegerValue){
                indexAux = ((IntegerValue) indexValue).value();
            }
            else{
                Utils.abort(super.getLine());
            }
            return values.get(indexAux);
        }
    }

    @Override
    public void setValue(Value<?> value) {
        Value<?> v = this.base.expr();
        SetExpr sExpr = (SetExpr) base;
        if(this.index != null){
            if(!(v instanceof ArrayValue)){
                Utils.abort(super.getLine());
            }
            else{
                int indexNum = 0;
                Value<?> indexValue  = this.index.expr();
                Vector<Value<?>> values = ((ArrayValue) v).value();
                if(indexValue instanceof StringValue){
                    indexNum = Integer.parseInt(((StringValue)indexValue).value());
                }
                else if(indexValue instanceof IntegerValue){
                    indexNum = ((IntegerValue)indexValue).value();
                }
                else{
                    Utils.abort(super.getLine());
                }
                if(indexNum < values.size()){
                    values.set(indexNum, value);
                }
                else{
                    if(values.size() == indexNum){
                        values.add(value);
                    }
                    else{
                        for(int i = values.size(); i<= indexNum; i++){
                            if(values.size() == indexNum){
                                values.add(value);
                            }
                            else{
                                values.add(new StringValue(""));
                            }
                        }
                    }
                }
            }
            
        }
        else{
            if(value instanceof IntegerValue){
                sExpr.setValue((IntegerValue)value);
            }
            else if(value instanceof StringValue){
                sExpr.setValue((StringValue)value);
            }
            else{
                sExpr.setValue(value);
            }
        }
        
        
        
        
        
    }
}
