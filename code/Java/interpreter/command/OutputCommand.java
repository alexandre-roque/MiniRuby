package interpreter.command;

import interpreter.expr.Expr;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import interpreter.value.Value;

public class OutputCommand extends Command {

    private OutputOp op;
    private Expr expr;

    public OutputCommand(int line, OutputOp op) {
        this(line, op, null);
    }

    public OutputCommand(int line, OutputOp op, Expr expr) {
        super(line);

        this.op = op;
        this.expr = expr;
    }

    @Override
    public void execute() {
        if (expr != null) {
            Value<?> v = expr.expr();
            System.out.print(v.toString());
    
            /* if (v instanceof IntegerValue) {
                IntegerValue iv = (IntegerValue) v;
                int n = iv.value();
                System.out.print(n);
            } else if (v instanceof StringValue) {
                StringValue sv = (StringValue) v;
                String str = sv.value();
                System.out.print(str);
            } else {
                // ...
            }*/ 
        }

        if (op == OutputOp.PutsOp)
            System.out.println();
    }
    
}
