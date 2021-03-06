package interpreter.expr;

import interpreter.util.Memory;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class Variable extends SetExpr{
    private String name;

    public Variable(int line, String name) {
        super(line);
        this.name = name;
    }


    public String getName() {
        return this.name;
    }


    @Override
    public void setValue(Value<?> value) {
        Memory.write(name, value);
    }


    @Override
    public Value<?> expr() {
        Value<?> value = Memory.read(name);
        if(value == null){
            value = new StringValue("");
        }
        return value;
    }   
}
    // Feito em sala
