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
            if(value instanceof ArrayValue){
                Utils.abort(super.getLine());
            }
            else{
                return value;
            }
        }
        else{
            if(value instanceof ArrayValue){
                Utils.abort(super.getLine());
            }
            else{
                int indexAux = 0;
                Value<?> indexValue = this.index.expr();
                Vector<Value<?>> values = (((ArrayValue) value).value());
                if(indexValue instanceof StringValue || indexValue instanceof IntegerValue){
                    indexAux = Integer.parseInt(indexValue.toString());
                }
                else{
                    Utils.abort(super.getLine());
                }
                return values.get(indexAux);
            }
        }
        return value;
    }

    @Override
    public void setValue(Value<?> value) {
        Value<?> v = this.base.expr();
        SetExpr sExpr = (SetExpr) base;
        if(this.index != null){
            if(!(value instanceof ArrayValue)){
                Utils.abort(super.getLine());
            }
            else{
                int indexAux = 0;
                Value<?> indexValue = this.index.expr();
                Vector<Value<?>> values = (((ArrayValue) v).value());
                if(indexValue instanceof IntegerValue || indexValue instanceof StringValue){
                    indexAux = Integer.parseInt(indexValue.toString());

                }
                else{
                    Utils.abort(super.getLine());
                }
                if(indexAux >= values.size()){
                    if(indexAux != values.size()){
                        for(int i = values.size(); i <= indexAux; i++){
                            if(indexAux != values.size()){
                                values.add(new StringValue(""));
                            }
                            else{
                                values.add(value);
                            }
                        }
                    }
                    else{
                        values.add(value);
                    }
                }
                else{
                    values.set(indexAux, value);
                }
                sExpr.setValue(new ArrayValue(values));
            }
        }
        else{
            if(value instanceof StringValue){
                StringValue sValue = new StringValue(((StringValue) v).value());
                sExpr.setValue(sValue);
            }
            else{
                if(value instanceof IntegerValue){
                    IntegerValue iValue = new IntegerValue(((IntegerValue)value).value());
                    sExpr.setValue(iValue);
                }
                else{
                    sExpr.setValue(value);
                }
            }
        }
    }
    
}
