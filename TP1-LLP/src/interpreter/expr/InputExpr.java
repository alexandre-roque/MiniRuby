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
        if(op == InputOp.RandOp){
            return (new IntegerValue(rand.nextInt()));
        }
        else if(op == InputOp.GetsOp){
            return (new StringValue(scanner.nextLine().trim()));
        }
        else{
            Utils.abort(super.getLine());
            return null;
        }
    } 
}
