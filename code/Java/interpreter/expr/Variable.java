package interpreter.expr;

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
        // TODO
    }


    @Override
    public Value<?> expr() {
        // TODO 
        return null;
    }
    
    
}
