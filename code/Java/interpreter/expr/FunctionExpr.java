package interpreter.expr;

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

        if(!(op == FunctionOp.LengthOp)){
            
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
