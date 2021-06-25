package interpreter.command;

import java.util.List;

import interpreter.expr.Expr;

public class AssignCommand extends Command{
    private List<Expr> left;
    private List<Expr> right;

    public AssignCommand(int line, List<Expr> left, List<Expr> right){
        super(line);
        this.left = left;
        this.right = right;
    }

    @Override
    public void execute() { //TODO
        
    }
    
}

        // AssignCommand
        // =============

        // se o tamanho da lista left for diferente do tamanho da lista right
        // abortar com operacao invalida

        // List<Value<?>> temp = percorrer a lista right avaliando as expressoes
        // i = 0;
        // percorrer a lista left onde, para cada expr, fazer
        // se expr não for SetExpr
        //     abortar com operacao invalida

        // SetExpr sexpr = converter expr
        // sexpr->setValue(temp[i]);
        // i++;