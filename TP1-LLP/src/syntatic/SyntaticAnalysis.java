package syntatic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import interpreter.command.AssignCommand;
import interpreter.command.BlocksCommand;
import interpreter.command.Command;
import interpreter.command.ForCommand;
import interpreter.command.IfCommand;
import interpreter.command.OutputCommand;
import interpreter.command.OutputOp;
import interpreter.command.UnlessCommand;
import interpreter.command.UntilCommand;
import interpreter.command.WhileCommand;
import interpreter.expr.AccessExpr;
import interpreter.expr.ArrayExpr;
import interpreter.expr.BinaryExpr;
import interpreter.expr.BinaryOp;
import interpreter.expr.BoolExpr;
import interpreter.expr.BoolOp;
import interpreter.expr.CompositeBoolExpr;
import interpreter.expr.ConstExpr;
import interpreter.expr.ConvExpr;
import interpreter.expr.ConvOp;
import interpreter.expr.Expr;
import interpreter.expr.FunctionExpr;
import interpreter.expr.FunctionOp;
import interpreter.expr.InputExpr;
import interpreter.expr.InputOp;
import interpreter.expr.NotBoolExpr;
import interpreter.expr.RelOp;
import interpreter.expr.SingleBoolExpr;
import interpreter.expr.Variable;
import interpreter.value.IntegerValue;
import interpreter.value.StringValue;
import lexical.Lexeme;
import lexical.LexicalAnalysis;
import lexical.LexicalException;
import lexical.TokenType;

public class SyntaticAnalysis {

    private LexicalAnalysis lex;
    private Lexeme current;

    public SyntaticAnalysis(LexicalAnalysis lex) throws LexicalException, IOException {
        this.lex = lex;
        this.current = lex.nextToken();
    }

    public Command start() throws LexicalException, IOException {
        Command cmd = procCode();
        eat(TokenType.END_OF_FILE);
    
        return cmd;
    }

    private void advance() throws LexicalException, IOException {
        // System.out.println("Advanced (\"" + current.token + "\", " +
        //     current.type + ")");
        current = lex.nextToken();
    }

    private void eat(TokenType type) throws LexicalException, IOException {
        // System.out.println("Expected (..., " + type + "), found (\"" + 
        //     current.token + "\", " + current.type + ")");
        if (type == current.type) {
            current = lex.nextToken();
        } else {
            showError();
        }
    }

    private void showError() {
        System.out.printf("%02d: ", lex.getLine());

        switch (current.type) {
            case INVALID_TOKEN:
                try { throw new Exception(); } catch(Exception e) { e.printStackTrace(); }
                System.out.printf("Lexema inválido [%s]\n", current.token);
                break;
            case UNEXPECTED_EOF:
            case END_OF_FILE:
                try { throw new Exception(); } catch(Exception e) { e.printStackTrace(); }
                System.out.printf("Fim de arquivo inesperado\n");
                break;
            default:
                try { throw new Exception(); } catch(Exception e) { e.printStackTrace(); }
                System.out.printf("Lexema não esperado [%s]\n", current.token);
                break;
        }

        System.exit(1);
    }

    // <code>     ::= { <cmd> }
    private BlocksCommand procCode() throws LexicalException, IOException {
        int line = lex.getLine();

        List<Command> cmds = new ArrayList<>();
        while (current.type == TokenType.IF ||
                current.type == TokenType.UNLESS ||
                current.type == TokenType.WHILE || 
                current.type == TokenType.UNTIL ||
                current.type == TokenType.FOR ||
                current.type == TokenType.PUTS ||
                current.type == TokenType.PRINT ||
                current.type == TokenType.ID /*||
                current.type == TokenType.OPEN_PAR*/) {
            Command cmd = procCmd();
            cmds.add(cmd);
        }

        return (new BlocksCommand(line, cmds));
    }

    // <cmd>      ::= <if> | <unless> | <while> | <until> | <for> | <output> | <assign>
    private Command procCmd() throws LexicalException, IOException {
       
        Command cmd = null;
        if (current.type == TokenType.IF) {
            cmd = procIf();
        } else if (current.type == TokenType.UNLESS) {
            cmd = procUnless();
        } else if (current.type == TokenType.WHILE) {
            cmd = procWhile();
        } else if (current.type == TokenType.UNTIL) {
            cmd = procUntil();
        } else if (current.type == TokenType.FOR) {
            cmd = procFor();
        } else if (current.type == TokenType.PUTS || current.type == TokenType.PRINT) {
            cmd = procOutput();
        } else if(current.type == TokenType.ID || current.type == TokenType.OPEN_PAR){
            cmd = procAssign();
        }else{
            showError();
        }

        return cmd;
    }

    // <if>       ::= if <boolexpr> [ then ] <code> { elsif <boolexpr> [ then ] <code> } [ else <code> ] end
        private IfCommand procIf() throws LexicalException, IOException {
            int quantidadeIfs = 0;
            ArrayList<IfCommand> ifscommands = new ArrayList<>();
            
            Command thenCmds = null;
            Command elsecmds = null;
            eat(TokenType.IF);
            BoolExpr cond =  procBoolExpr();          
            int line = lex.getLine();
            
            if (current.type == TokenType.THEN) {
                eat(TokenType.THEN);
            }

            thenCmds = procCode();

            while (current.type == TokenType.ELSIF) {
                eat(TokenType.ELSIF);
                Command thenCmdsElsfif = null;
                Command elseCmdsElsfif = null;
                
                BoolExpr condElsif =  procBoolExpr();          
                
                if (current.type == TokenType.THEN) {
                    advance();
                    thenCmdsElsfif = procCode();                    
                }
                
                IfCommand elsif = new IfCommand(line, condElsif, thenCmdsElsfif, elseCmdsElsfif);
                ifscommands.add(elsif);
                if(ifscommands.size() > 1){
                    IfCommand aux = ifscommands.get(quantidadeIfs-1);
                    ifscommands.get(quantidadeIfs-1).setElseCmds(aux);
                }
                quantidadeIfs++;
            }

            if(current.type == TokenType.ELSE){
                advance();
                elsecmds = procCode();
            }
            
            IfCommand ifcmd = new IfCommand(line,cond,thenCmds,elsecmds);
            
            if(ifscommands.isEmpty()){
                ifcmd.setElseCmds(elsecmds);
            }
            else{
                ifscommands.get(ifscommands.size()-1).setElseCmds(elsecmds);
                ifcmd.setElseCmds(ifscommands.get(0));
                for(int i = 0; i < ifscommands.size() - 1; i++){
                    if(ifscommands.get(i+1) != null){
                       ifscommands.get(i).setElseCmds(ifscommands.get(i+1)); 
                    }                    
                }
            }
            advance();
            return ifcmd;
        }

    // <unless> ::= unless <boolexpr> [ then ] <code> [ else <code> ] end
    private UnlessCommand procUnless() throws LexicalException, IOException {
        eat(TokenType.UNLESS);
        BoolExpr cond = procBoolExpr();

        if (current.type == TokenType.THEN) {
            advance();
        }

        BlocksCommand thenCmds = procCode();

        if (current.type == TokenType.ELSE) {
            advance();
            BlocksCommand elseCmds = procCode();
            int line = lex.getLine();
            return (new UnlessCommand(line, cond, thenCmds, elseCmds));
        }

        eat(TokenType.END);
        int line = lex.getLine();
        return (new UnlessCommand(line, cond, thenCmds, null));
    }

    // <while>    ::= while <boolexpr> [ do ] <code> end
    private WhileCommand procWhile() throws LexicalException, IOException {
        int line = lex.getLine();
        eat(TokenType.WHILE);
 
        BoolExpr cond = procBoolExpr();

        if (current.type == TokenType.DO){
            advance();
        }

        BlocksCommand cmds = procCode();
        eat(TokenType.END);

        return (new WhileCommand(line, cond, cmds));
    }

    // <until> ::= until <boolexpr> [ do ] <code> end
    private UntilCommand procUntil() throws LexicalException, IOException {

        eat(TokenType.UNTIL);
        BoolExpr cond = procBoolExpr();

        if (current.type == TokenType.DO) {
            advance();
        }

        BlocksCommand cmds = procCode();
        eat(TokenType.END);

        int line = lex.getLine();

        return (new UntilCommand(line,cond,cmds));
    }

    // <for> ::= for <id> in <expr> [ do ] <code> end
    private ForCommand procFor() throws LexicalException, IOException {

        eat(TokenType.FOR);
        Variable var = procId();

        eat(TokenType.IN);
        Expr expr = procExpr();

        if (current.type == TokenType.DO) {
            advance();
        }

        BlocksCommand cmds = procCode();
        eat(TokenType.END);

        int line = lex.getLine();

        return (new ForCommand(line, var, expr, cmds));
    }

    // <output>   ::= ( puts | print ) [ <expr> ] [ <post> ] ';'
    private OutputCommand procOutput() throws LexicalException, IOException {
        OutputOp op = null;
        if (current.type == TokenType.PUTS) {
            op = OutputOp.PutsOp;
            advance();
        } else if (current.type == TokenType.PRINT) {
            op = OutputOp.PrintOp;
            advance();
        } else {
            showError();
        }

        int line = lex.getLine();

        Expr expr = null;
        
        if (current.type == TokenType.ADD ||
                current.type == TokenType.SUB ||
                current.type == TokenType.INTEGER ||
                current.type == TokenType.STRING ||
                current.type == TokenType.OPEN_BRA ||
                current.type == TokenType.GETS ||
                current.type == TokenType.RAND ||
                current.type == TokenType.ID ||
                current.type == TokenType.OPEN_PAR) {
            expr = procExpr();
        }

        
        if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
            return (OutputCommand)procPost(new OutputCommand(line, op, expr));
        }
        

        eat(TokenType.SEMI_COLON);
        return (new OutputCommand(line, op, expr));

    }

    // <assign>   ::= <access> { ',' <access> } '=' <expr> { ',' <expr> } [ <post> ] ';'
    private Command procAssign() throws LexicalException, IOException {
        Command cmd = null;
        List<Expr> left = new ArrayList<>();
        List<Expr> right = new ArrayList<>();
        int line = lex.getLine();

        left.add(procAccess());

        while (current.type == TokenType.COMMA) {
            advance();
            left.add(procAccess());
        }

        eat(TokenType.ASSIGN);
        right.add(procExpr());

        while (current.type == TokenType.COMMA) {
            advance();
            right.add(procExpr());
        }
        AssignCommand assigncmd = new AssignCommand(line, left, right);

        if (current.type == TokenType.IF || current.type == TokenType.UNLESS) {
            cmd = procPost(assigncmd);
        }
        else{
            cmd = assigncmd;
        }

        eat(TokenType.SEMI_COLON);
        return cmd;
    }

    // <post>     ::= ( if | unless ) <boolexpr>
    private Command procPost(Command cmd) throws LexicalException, IOException {
        BoolExpr cond = null;
        int line = lex.getLine();


        if (current.type == TokenType.IF) {
            advance();
            cond = procBoolExpr();
            return (new IfCommand(line,cond,cmd,null));

        } else if (current.type == TokenType.UNLESS) {
            cond = procBoolExpr();
            advance();
            return (new UnlessCommand(line, cond, cmd, null));
        } else {
            showError();
            return null;
        }
    }

    // <boolexpr> ::= [ not ] <cmpexpr> [ (and | or) <boolexpr> ]
    private BoolExpr procBoolExpr() throws LexicalException, IOException {
        BoolExpr bexpr = null;
        BoolExpr left = null;
        BoolExpr right = null;
        int line = lex.getLine();
        BoolOp op = null;
        

        if (current.type == TokenType.NOT) {
            advance();
            left = procCmpexpr();
            left = new NotBoolExpr(line,left);
        }
        else{
            left = procCmpexpr();
        }

        if (current.type == TokenType.AND){
            op = BoolOp.And;
            advance();
        }else if(current.type == TokenType.OR) {
            op = BoolOp.Or;
            advance();
        } else {
            return left;
        }

        return (new CompositeBoolExpr(line, left, op, procBoolExpr()));
    }

    // <cmpexpr> ::= <expr> ( '==' | '!=' | '<' | '<=' | '>' | '>=' | '===' ) <expr>
    private BoolExpr procCmpexpr() throws LexicalException, IOException {
        RelOp op = null;
        Expr left = procExpr();
        int line = lex.getLine();

        if (current.type == TokenType.EQUALS){
            op = RelOp.EqualsOp;
            advance();
        }else if( current.type == TokenType.NOT_EQUALS){
            op = RelOp.NotEqualsOp;
            advance();
        }else if(current.type == TokenType.LOWER_EQ){
            op = RelOp.LowerEqualOp;
            advance();
        }else if(current.type == TokenType.LOWER){
            op = RelOp.LowerThanOp;
            advance();
        }else if(current.type == TokenType.GREATER){
            op = RelOp.GreaterThanOp;
            advance();
        }else if(current.type == TokenType.GREATER_EQ){
            op = RelOp.GreaterEqualOp;
            advance();
        }else if(current.type == TokenType.CONTAINS) {
            op = RelOp.ContainsOp;
            advance();
        } else {
            showError();
        }

        return (new SingleBoolExpr(line, left, op, procExpr()));
    }

    // <expr>     ::= <arith> [ ( '..' | '...' ) <arith> ]
    private Expr procExpr() throws LexicalException, IOException {
        Expr expr = null;
        Expr right = null;
        Expr left = procArith();
        int line = lex.getLine();
        BinaryOp op = null;

        if (current.type == TokenType.RANGE_WITH || current.type == TokenType.RANGE_WITHOUT) {
            if(current.type == TokenType.RANGE_WITH){
                op = BinaryOp.RangeWithOp;
            }else if(current.type == TokenType.RANGE_WITHOUT){
                op = BinaryOp.RangeWithoutOp;
            } else{
                showError();
            }

            advance();
            right = procArith();
            left = new BinaryExpr(line, left, op, right);
        }

        expr = left;
        return expr;
    }

    // <arith>    ::= <term> { ('+' | '-') <term> }
    private Expr procArith() throws LexicalException, IOException {
        Expr expr = null;
        Expr right = null;
        Expr left = procTerm();
        int line = lex.getLine();
        BinaryOp op = null;

        while (current.type == TokenType.ADD || current.type == TokenType.SUB) {
            if(current.type == TokenType.ADD){
                op = BinaryOp.AddOp;
            }
            else if(current.type == TokenType.SUB){
                op = BinaryOp.SubOp;
            }
            else{
                showError();
            }
            
            advance();
            right = procTerm();            
            left = new BinaryExpr(line, left, op, right);
        }

        expr = left;
        return expr;
    }

    // <term>     ::= <power> { ('*' | '/' | '%') <power> }
    private Expr procTerm() throws LexicalException, IOException {
        BinaryOp op = null;
        Expr expr = null;
        Expr right = null;
        Expr left = procPower();
        int line;

        while (current.type == TokenType.MUL || current.type == TokenType.DIV ||
                current.type == TokenType.MOD) {
            line = lex.getLine();
            if(current.type == TokenType.MUL){
                op = BinaryOp.MultOp;
            }
            else if(current.type == TokenType.DIV){
                op = BinaryOp.DivOp;
            }
            else if(current.type == TokenType.MOD){
                op = BinaryOp.ModOp;
            }
            advance();
            right = procPower();

            BinaryExpr binaryexpr = new BinaryExpr(line,left,op,right);
            left = binaryexpr;
        }
        expr = left;
        return expr;
    }

    // <power>    ::= <factor> { '**' <factor> }
    private Expr procPower() throws LexicalException, IOException {
        Expr expr = null;
        BinaryOp op = null;
        Expr left, right = null;
        int line;


        left = procFactor();

        while (current.type == TokenType.EXP) {
            line = lex.getLine();
            op = BinaryOp.ExpOp;
            advance();
            right = procFactor();

            BinaryExpr binaryexpr = new BinaryExpr(line, left, op, right);
            left = binaryexpr;
        }

        expr = left;
        return expr;
    }    
    
    // <factor> ::= [ '+' | '-' ] ( <const> | <input> | <access> ) [ <function> ] //duvida
    private Expr procFactor() throws LexicalException, IOException {
        Expr expr = null;
        ConvOp op = null;

        if (current.type == TokenType.ADD) {
            op = ConvOp.PlusOp;
            advance();
        }
        else if(current.type == TokenType.SUB){
            op = ConvOp.MinusOp;
            advance();
        }

        if (current.type == TokenType.INTEGER || current.type == TokenType.STRING || current.type == TokenType.OPEN_BRA){
            expr = procConst();
        }
        else if (current.type == TokenType.GETS || current.type == TokenType.RAND){
            expr = procInput();
        }
        else if (current.type == TokenType.ID || current.type == TokenType.OPEN_PAR){
            expr = procAccess();
        }
        else{
            showError();
        }

        if(current.type == TokenType.DOT){
            FunctionExpr fexpr = null;
            fexpr = procFunction(expr);
            return fexpr;
        }

        int line = lex.getLine();
        if(op != null){
            ConvExpr convexpr = null;
            convexpr = new ConvExpr(line,op,expr);
            return convexpr;
        }

        return expr;
    }
    
    

    // <const>    ::= <integer> | <string> | <array>
    private Expr procConst() throws LexicalException, IOException {
        Expr expr = null;
        if (current.type == TokenType.INTEGER) {
            expr = procInteger();
        } else if (current.type == TokenType.STRING) {
            expr = procString();
        } else {
            expr = procArray();
        }
        return expr;
    }

    // <input> ::= gets | rand
    private Expr procInput() throws LexicalException, IOException {
        Expr expr = null;
        InputOp op = null;
        int line = lex.getLine();
        
        if (current.type == TokenType.GETS) {
            line = lex.getLine();
            op = InputOp.GetsOp;
            advance();
        } else if (current.type == TokenType.RAND) {
            line = lex.getLine();
            op = InputOp.RandOp;
            advance();
        } else {
            showError();
        }

        InputExpr inputexpr = new InputExpr(line,op);
        expr = inputexpr;
        return expr;
    }

    // <array>    ::= '[' [ <expr> { ',' <expr> } ] ']'
    private ArrayExpr procArray() throws LexicalException, IOException {
        int line = lex.getLine();
        Vector<Expr> exprs = null;

        eat(TokenType.OPEN_BRA);

        if (current.type == TokenType.ADD ||
            current.type == TokenType.SUB ||
            current.type == TokenType.INTEGER ||
            current.type == TokenType.STRING ||
            current.type == TokenType.OPEN_BRA ||
            current.type == TokenType.GETS ||
            current.type == TokenType.RAND ||
            current.type == TokenType.ID ||
            current.type == TokenType.OPEN_PAR) {

            exprs.add(procExpr());

            while (current.type == TokenType.COMMA) {
                advance();

                exprs.add(procExpr());
            }
        }

        eat(TokenType.CLOSE_BRA);

        return (new ArrayExpr(line, exprs));
    }

    // <access> ::= ( <id> | '(' <expr> ')' ) [ '[' <expr> ']' ]
    private Expr procAccess() throws LexicalException, IOException {
        Expr expr = null;
        Expr index= null;
        Expr base = null;
        int line = lex.getLine();

        if (current.type == TokenType.ID) {
            base = procId();
        } else if(current.type == TokenType.OPEN_PAR){
            eat(TokenType.CLOSE_PAR);
        }
        else {
            showError();
        }

        if (current.type == TokenType.OPEN_BRA) {
            advance();
            index = procExpr();
            eat(TokenType.CLOSE_BRA);
            
        }
        
        AccessExpr accessexpr = new AccessExpr(line,base,index);
        expr = accessexpr;
        return expr;

    }

    // <function> ::= '.' ( length | to_i | to_s )
    private FunctionExpr procFunction(Expr expr) throws LexicalException, IOException {
        int line = lex.getLine();
        FunctionOp op = null;
        FunctionExpr fexpr = null;

        eat(TokenType.DOT);
        if (current.type == TokenType.LENGTH){
            op = FunctionOp.LengthOp;
            advance();
        } 
        else if (current.type == TokenType.TO_INT){
            op = FunctionOp.ToIntOp;
            advance();
        }
        else if (current.type == TokenType.TO_STR){
            op = FunctionOp.ToStringOp;
            advance();
        } 
        else {
            showError();
        }

        fexpr = new FunctionExpr(line,expr,op);

        return fexpr;
    }

    private ConstExpr procInteger() throws LexicalException, IOException {
        String tmp = current.token;
        eat(TokenType.INTEGER);
        int line = lex.getLine();

        int n;
        try {
            n = Integer.parseInt(tmp);
        } catch (Exception e) {
            n = 0;
        }

        IntegerValue iv = new IntegerValue(n);
        ConstExpr cexpr = new ConstExpr(line, iv);
        return cexpr;
    }

    private ConstExpr procString() throws LexicalException, IOException {
        String str = current.token;
        eat(TokenType.STRING);
        int line = lex.getLine();
        StringValue sv = new StringValue(str);
        ConstExpr cexpr = new ConstExpr(line, sv);
        return cexpr;
    }

    private Variable procId() throws LexicalException, IOException {
        String str = current.token;
        int line = lex.getLine();
        eat(TokenType.ID);
        return (new Variable(line,str));
    }

}
