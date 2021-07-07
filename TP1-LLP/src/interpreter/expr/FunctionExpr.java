package interpreter.expr;

import java.util.Vector;

import interpreter.util.Utils;
import interpreter.value.ArrayValue;
import interpreter.value.StringValue;
import interpreter.value.IntegerValue;
import interpreter.value.Value;

public class FunctionExpr extends Expr{
    private Expr expr;
    private FunctionOp op;
    
    public FunctionExpr(int line, Expr expr, FunctionOp op) {
        super(line);
        this.expr = expr;
        this.op = op;
    }
    
    @Override
    public Value<?> expr() {
        Value<?> v = expr.expr();

        if((op == FunctionOp.ToIntOp)){
            if(!(v instanceof StringValue)){
                Utils.abort(super.getLine());
            }
            else{
                int n;
                n = Integer.parseInt(v.toString());
                IntegerValue iv = new IntegerValue(n);
                v = iv;
            }
        }
        else if((op == FunctionOp.LengthOp)){
            if(!(v instanceof ArrayValue)){
                Utils.abort(super.getLine());
            }
            else{
                ArrayValue av;
                av = (ArrayValue) v;
                Vector<Value<?>> aux = av.value();
                int tam = aux.size();
                IntegerValue auxtam = new IntegerValue(tam);
                v = auxtam;
            }
        }
        else if((op == FunctionOp.ToStringOp)){
            String s = v.toString();
            StringValue sv = new StringValue(s);
            v = sv;
        }

        return v;
    }

    // FunctionExpr
    // ============

    // v = resultado de avaliar a expressao
    // se op for LengthOp
    // se v não for arranjo
    //     abortar com operacao invalida

    // pegar o arranjo do v
    // contar a quantidade
    // criar IntegerValue com essa quantidade
    // se op for ToIntOp
    // se v nao for string
    //     abortar com operacao invalida

    // pegar a string de v
    // converter a string para inteiro
    // criar IntegerValue com o valor resultante
    // se op for ToStringOp
    // pegar a string de v
    // criar StringValue com essa string
}
