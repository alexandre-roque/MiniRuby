package interpreter.command;

import java.util.ArrayList;
import java.util.List;

import interpreter.expr.Expr;
import interpreter.expr.SetExpr;
import interpreter.util.Utils;
import interpreter.value.Value;

public class AssignCommand extends Command{
    private List<Expr> left;
    private List<Expr> right;

    public AssignCommand(int line, List<Expr> left, List<Expr> right){
        super(line);
        this.left = left;
        this.right = right;
    }

    @Override
    public void execute() {
        if(left.size()!=right.size()){
            Utils.abort(super.getLine());
        }
        else{
            List<Value<?>> temp;
            temp = new ArrayList<>();

            for(Expr expr:right){
                temp.add(expr.expr());
            }

            int i =0;
            for(Expr expr:left){
                if(!(expr instanceof SetExpr)){
                    Utils.abort(super.getLine());
                }
                else{
                    SetExpr sexpr = (SetExpr) expr;
                    sexpr.setValue(temp.get(i));
                }
                i++;
            }
        }
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