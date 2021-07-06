package interpreter.expr;

import java.util.Scanner;
import java.util.Random;
import interpreter.value.Value;
import interpreter.util.Utils;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;

public class InputExpr extends Expr{
    private static Scanner scanner = new Scanner(System.in);
    private static Random rand = new Random();
    private InputOp op;

    public InputExpr(int line, InputOp op) {
        super(line);
        this.op = op;
    }
    
    @Override
    public Value<?> expr() {
        Value<?> v =null;
        if(op == InputOp.RandOp){
            int aux = rand.nextInt();
            IntegerValue valor = new IntegerValue(aux);
            v = valor;
        }
        else if(op == InputOp.GetsOp){
            String s = scanner.nextLine().trim();
            StringValue valor =  new StringValue(s);
            v = valor;
        }
        else{
            Utils.abort(super.getLine());
        }
        return v;
    } 
}
