package golite.pretty;

import golite.parser.*;
import golite.lexer.*;
import golite.node.*;
import golite.analysis.*;
import java.util.*;

public class PrettyPrinter extends DepthFirstAdapter {
    
    private int tabCount = 0;
    private boolean noNewline = false;
    private boolean noNewBraces = false;
    
    public static void print(Node node)
    {
        node.apply(new PrettyPrinter());
    }
    
    // prints comma-separated list
    private void printList(List<Node> list)
    {
        Iterator<Node> itr = list.iterator();
        if (itr.hasNext()) {
            Node next = itr.next();
            next.apply(this);
        }
        while (itr.hasNext()) {
            Node next = itr.next();
            puts(", ");
            next.apply(this);
        }
    }
    
    private void printBinaryExp(Node left, String op, Node right)
    {
        puts("(");
        left.apply(this);
        puts(op);
        right.apply(this);
        puts(")");
    }
    
    private void puts(String s)
    {
        System.out.print(s);
        System.out.flush();
    }
    
    private void tab()
    {
        for (int i=0; i<tabCount; i++)  {
            puts("\t");
        }
    }
    
    private void newline()
    {
        if (!noNewline) {
            puts("\n");
        }
    }
    
    /* program */
    public void inAProgram(AProgram node)
    {
        puts("package ");
        puts(node.getId().getText());
        puts("\n\n");
    }
    
    /* var declaration */
    public void caseAVarDecStmt(AVarDecStmt node)
    {
        puts("var ");
        List<Node> idList = new ArrayList<Node>(node.getId());
        printList(idList);
        puts(" ");
        if(node.getType() != null)
        {
            node.getType().apply(this);
            puts(" ");
        }
        List<Node> expList = new ArrayList<Node>(node.getVal());
        if (!expList.isEmpty()) {
            puts("= ");
            printList(expList);
        }
        newline();
    }
    
    /* type declaration */
    public void caseATypeDecStmt(ATypeDecStmt node)
    {
        puts("type ");
        node.getExp().apply(this);
        puts(" ");
        node.getType().apply(this);
        newline();
    }
    
    /* func declaration */
    public void caseAFuncDecStmt(AFuncDecStmt node)
    {
        newline();
        puts("func ");
        node.getExp().apply(this);
        puts("(");
        List<Node> paramList = new ArrayList<Node>(node.getIdsType());
        printList(paramList);
        puts(") ");
        if(node.getType() != null)
        {
            node.getType().apply(this);
            puts(" ");
        }
        noNewBraces = true;
        puts("{\n");
        List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
        for(PStmt e : body)
        {
            e.apply(this);
        }
        puts("}");
        newline();
        noNewBraces = false;
    }
    
    /* ids_type */
    public void caseAIdsType(AIdsType node)
    {
        List<Node> idList = new ArrayList<Node>(node.getExp());
        printList(idList);
        puts(" ");
        node.getType().apply(this);
    }
    
    /* exp statement */
    public void caseAExpStmt(AExpStmt node)
    {
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
            newline();
        }
    }
    
    /* assign_eq statement */
    public void caseAAssignEqStmt(AAssignEqStmt node)
    {
        List<Node> idList = new ArrayList<Node>(node.getL());
        printList(idList);
        puts(" = ");
        List<Node> expList = new ArrayList<Node>(node.getR());
        printList(expList);
        newline();
    }
    
    /* plus_eq statement */
    public void caseAPlusEqStmt(APlusEqStmt node)
    {
        node.getL().apply(this);
        puts(" += ");
        node.getR().apply(this);
        newline();
    }
    
    /* minus_eq statement */
    public void caseAMinusEqStmt(AMinusEqStmt node)
    {
        node.getL().apply(this);
        puts(" -= ");
        node.getR().apply(this);
        newline();
    }
    
    /* star_eq statement */
    public void caseAStarEqStmt(AStarEqStmt node)
    {
        node.getL().apply(this);
        puts(" *= ");
        node.getR().apply(this);
        newline();
    }
    
    /* slash_eq statement */
    public void caseASlashEqStmt(ASlashEqStmt node)
    {
        node.getL().apply(this);
        puts(" /= ");
        node.getR().apply(this);
        newline();
    }
    
    /* mod_eq statement */
    public void caseAModEqStmt(AModEqStmt node)
    {
        node.getL().apply(this);
        puts(" %= ");
        node.getR().apply(this);
        newline();
    }
    
    /* amp_eq statement */
    public void caseAAmpEqStmt(AAmpEqStmt node)
    {
        node.getL().apply(this);
        puts(" &= ");
        node.getR().apply(this);
        newline();
    }
    
    /* l_shift_eq statement */
    public void caseALShiftEqStmt(ALShiftEqStmt node)
    {
        node.getL().apply(this);
        puts(" <<= ");
        node.getR().apply(this);
        newline();
    }
    
    /* r_shift_eq statement */
    public void caseARShiftEqStmt(ARShiftEqStmt node)
    {
        node.getL().apply(this);
        puts(" >>= ");
        node.getR().apply(this);
        newline();
    }
    
    /* amp_caret_eq statement */
    public void caseAAmpCaretEqStmt(AAmpCaretEqStmt node)
    {
        node.getL().apply(this);
        puts(" &^= ");
        node.getR().apply(this);
        newline();
    }
    
    /* pipe_eq statement */
    public void caseAPipeEqStmt(APipeEqStmt node)
    {
        node.getL().apply(this);
        puts(" |= ");
        node.getR().apply(this);
        newline();
    }
    
    /* caret_eq statement */
    public void caseACaretEqStmt(ACaretEqStmt node)
    {
        node.getL().apply(this);
        puts(" ^= ");
        node.getR().apply(this);
        newline();
    }
    
    /* short declaration */
    public void caseAShortDeclStmt(AShortDeclStmt node)
    {
        List<Node> idList = new ArrayList<Node>(node.getL());
        printList(idList);
        puts(" := ");
        List<Node> expList = new ArrayList<Node>(node.getR());
        printList(expList);
        newline();
    }
    
    /* increment */
    public void caseAIncStmt(AIncStmt node)
    {
        node.getExp().apply(this);
        puts("++");
        newline();
    }
    
    /* decrement */
    public void caseADecStmt(ADecStmt node)
    {
        node.getExp().apply(this);
        puts("--");
        newline();
    }
    
    /* print */
    public void caseAPrintStmt(APrintStmt node)
    {
        puts("print(");
        List<Node> expList = new ArrayList<Node>(node.getExp());
        printList(expList);
        puts(")");
        newline();
    }
    
    /* println */
    public void caseAPrintlnStmt(APrintlnStmt node)
    {
        puts("println(");
        List<Node> expList = new ArrayList<Node>(node.getExp());
        printList(expList);
        puts(")");
        newline();
    }
    
    /* return */
    public void caseAReturnStmt(AReturnStmt node)
    {
        puts("return ");
        if(node.getExp() != null)
        {
            puts("(");
            node.getExp().apply(this);
            puts(")");
        }
        newline();
    }
    
    /* if */
    public void caseAIfStmt(AIfStmt node)
    {
        puts("if ");
        if((node.getStmt() != null) && !(node.getStmt() instanceof AEmptyStmt))
        {
            node.getStmt().apply(this);
            tab();
        }
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
            puts(" ");
        }
        noNewBraces = true;
        puts("{\n");
        List<PStmt> body = new ArrayList<PStmt>(node.getTrue());
        for(PStmt e : body)
        {
            e.apply(this);
        }
        tab();
        puts("} ");
        noNewBraces = false;
        List<PElseIf> elifStmts = new ArrayList<PElseIf>(node.getElseIf());
        for(PElseIf e : elifStmts)
        {
            e.apply(this);
        }
        puts(" ");
        List<PStmt> elseBody = new ArrayList<PStmt>(node.getFalse());
        if(!elseBody.isEmpty()) {
            noNewBraces = true;
            puts("else {\n");
            for(PStmt e : elseBody)
            {
                e.apply(this);
            }
            tab();
            puts("}");
            noNewBraces = false;
        }
        newline();
    }
    
    /* else if */
    public void caseAElseIf(AElseIf node)
    {
        puts("else if ");
        if(node.getStmt() != null && !(node.getStmt() instanceof AEmptyStmt))
        {
            node.getStmt().apply(this);
            tab();
        }
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
            puts(" ");
        }
        noNewBraces = true;
        puts("{\n");
        List<PStmt> body = new ArrayList<PStmt>(node.getBody());
        for(PStmt e : body)
        {
            e.apply(this);
        }
        tab();
        puts("} ");
        noNewBraces = false;
    }
    
    /* switch statement */
    public void caseASwitchStmt(ASwitchStmt node)
    {
        puts("switch ");
        if(node.getStmt() != null && !(node.getStmt() instanceof AEmptyStmt))
        {
            node.getStmt().apply(this);
            tab();
        }
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
            puts(" ");
        }
        puts("{\n");
        List<PSwitchExp> cases = new ArrayList<PSwitchExp>(node.getSwitchExp());
        for(PSwitchExp e : cases) {
            tab();
            e.apply(this);
        }
        tab();
        puts("}");
        newline();
    }
    
    /* switch expression */
    public void caseASwitchExp(ASwitchExp node)
    {
        List<Node> expList = new ArrayList<Node>(node.getExp());
        if(!expList.isEmpty()) {
            noNewBraces = true;
            puts("case ");
            printList(expList);
            puts(":\n");
            List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
            for(PStmt e : body)
            {
                e.apply(this);
            }
            noNewBraces = false;
        }
        else {
            puts("default:\n");
            noNewBraces = true;
            List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
            for(PStmt e : body)
            {
                e.apply(this);
            }
            noNewBraces = false;
        }
    }
    
    /* infinite loop */
    public void caseAInfiniteLoopStmt(AInfiniteLoopStmt node)
    {
        noNewBraces = true;
        puts("for {\n");
        List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
        for(PStmt e : body)
        {
            e.apply(this);
        }
        tab();
        puts("}");
        newline();
        noNewBraces = false;
    }
    
    /* while */
    public void caseAWhileStmt(AWhileStmt node)
    {
        puts("for ");
        node.getExp().apply(this);
        noNewBraces = true;
        puts(" {\n");
        List<PStmt> body = new ArrayList<PStmt>(node.getStmt());
        for(PStmt e : body)
        {
            e.apply(this);
        }
        tab();
        puts("}");
        newline();
        noNewBraces = false;
    }
    
    /* for */
    public void caseAForStmt(AForStmt node)
    {
        puts("for ");
        noNewline = true;
        if(node.getInit() != null)
        {
            node.getInit().apply(this);
        }
        puts("; ");
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        puts("; ");
        if(node.getCond() != null)
        {
            node.getCond().apply(this);
        }
        noNewline = false;
        noNewBraces = true;
        puts(" {\n");
        List<PStmt> body = new ArrayList<PStmt>(node.getBody());
        for(PStmt e : body)
        {
            e.apply(this);
        }
        tab();
        puts("}");
        newline();
        noNewBraces = false;
    }
    
    /* block */
    public void caseABlockStmt(ABlockStmt node)
    {
        List<PStmt> copy = new ArrayList<PStmt>(node.getStmt());
        if (noNewBraces) {
            noNewBraces = false;
            for(PStmt e : copy)
            {
                tabCount++;
                tab();
                e.apply(this);
                tabCount--;
            }
            noNewBraces = true;
        }
        else {
            puts("{\n");
            for(PStmt e : copy)
            {
                tabCount++;
                tab();
                e.apply(this);
                tabCount--;
            }
            tab();
            puts("}");
            newline();
        }
    }
    
    /* break */
    public void caseABreakStmt(ABreakStmt node)
    {
        puts("break\n");
    }
    
    /* continue */
    public void caseAContinueStmt(AContinueStmt node)
    {
        puts("continue\n");
    }
    
    /* int type */
    public void caseAIntType(AIntType node)
    {
        puts("int");
    }
    
    /* float type */
    public void caseAFloatType(AFloatType node)
    {
        puts("float64");
    }
    
    /* bool type */
    public void caseABoolType(ABoolType node)
    {
        puts("bool");
    }
    
    /* rune type */
    public void caseARuneType(ARuneType node)
    {
        puts("rune");
    }
    
    /* string type */
    public void caseAStringType(AStringType node)
    {
        puts("string");
    }
    
    /* slice type */
    public void caseASliceType(ASliceType node)
    {
        puts("[]");
        node.getType().apply(this);
    }
    
    /* array type */
    public void caseAArrayType(AArrayType node)
    {
        puts("[");
        puts(node.getIntLit().getText());
        puts("]");
        node.getType().apply(this);
    }
    
    /* struct type */
    public void caseAStructType(AStructType node)
    {
        puts("struct {\n");
        List<PField> fields = new ArrayList<PField>(node.getField());
        for(PField e : fields)
        {
            tabCount++;
            tab();
            e.apply(this);
            tabCount--;
        }
        tab();
        puts("} ");
    }
    
    /* field */
    public void caseAField(AField node)
    {
        List<Node> idList = new ArrayList<Node>(node.getExp());
        printList(idList);
        puts(" ");
        node.getType().apply(this);
        newline();
    }
    
    /* id type */
    public void caseAAliasType(AAliasType node)
    {
        puts(node.getId().getText());
    }
    
    /* || */
    public void caseABinOrExp(ABinOrExp node)
    {
        printBinaryExp(node.getL(), " || ", node.getR());
    }
    
    /* && */
    public void caseABinAndExp(ABinAndExp node)
    {
        printBinaryExp(node.getL(), " && ", node.getR());
    }
    
    /* == */
    public void caseAEqExp(AEqExp node)
    {
        printBinaryExp(node.getL(), " == ", node.getR());
    }
    
    /* != */
    public void caseANeqExp(ANeqExp node)
    {
        printBinaryExp(node.getL(), " != ", node.getR());
    }
    
    /* < */
    public void caseALtExp(ALtExp node)
    {
        printBinaryExp(node.getL(), " < ", node.getR());
    }
    
    /* <= */
    public void caseALeqExp(ALeqExp node)
    {
        printBinaryExp(node.getL(), " <= ", node.getR());
    }
    
    /* > */
    public void caseAGtExp(AGtExp node)
    {
        printBinaryExp(node.getL(), " > ", node.getR());
    }
    
    /* >= */
    public void caseAGeqExp(AGeqExp node)
    {
        printBinaryExp(node.getL(), " >= ", node.getR());
    }
    
    /* plus */
    public void caseAPlusExp(APlusExp node)
    {
        printBinaryExp(node.getL(), " + ", node.getR());
    }
    
    /* minus */
    public void caseAMinusExp(AMinusExp node)
    {
        printBinaryExp(node.getL(), " - ", node.getR());
    }
    
    /* pipe */
    public void caseAPipeExp(APipeExp node)
    {
        printBinaryExp(node.getL(), " | ", node.getR());
    }
    
    /* caret */
    public void caseACaretExp(ACaretExp node)
    {
        printBinaryExp(node.getL(), " ^ ", node.getR());
    }
    
    /* star */
    public void caseAStarExp(AStarExp node)
    {
        printBinaryExp(node.getL(), " * ", node.getR());
    }
    
    /* slash */
    public void caseASlashExp(ASlashExp node)
    {
        printBinaryExp(node.getL(), " / ", node.getR());
    }
    
    /* mod */
    public void caseAModExp(AModExp node)
    {
        printBinaryExp(node.getL(), " % ", node.getR());
    }
    
    /* l_shift */
    public void caseALShiftExp(ALShiftExp node)
    {
        printBinaryExp(node.getL(), " << ", node.getR());
    }
    
    /* r_shift */
    public void caseARShiftExp(ARShiftExp node)
    {
        printBinaryExp(node.getL(), " >> ", node.getR());
    }
    
    /* amp */
    public void caseAAmpExp(AAmpExp node)
    {
        printBinaryExp(node.getL(), " & ", node.getR());
    }
    
    /* amp_caret */
    public void caseAAmpCaretExp(AAmpCaretExp node)
    {
        printBinaryExp(node.getL(), " &^ ", node.getR());
    }
    
    /* u_plus */
    public void caseAUPlusExp(AUPlusExp node)
    {
        puts("(+");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* u_minus */
    public void caseAUMinusExp(AUMinusExp node)
    {
        puts("(-");
        node.getExp().apply(this);
        puts(")");
        
    }
    
    /* u_bang */
    public void caseAUBangExp(AUBangExp node)
    {
        puts("(!");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* u_caret */
    public void caseAUCaretExp(AUCaretExp node)
    {
        puts("(^");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* typecast */
    public void caseATypeCastExp(ATypeCastExp node)
    {
        node.getType().apply(this);
        puts("(");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* selector */
    public void caseASelectorExp(ASelectorExp node)
    {
        node.getExp().apply(this);
        puts(".");
        puts(node.getId().getText());
    }
    
    /* index */
    public void caseAIndexingExp(AIndexingExp node)
    {
        node.getArray().apply(this);
        puts("[");
        node.getIndex().apply(this);
        puts("]");
    }
    
    /* func call */
    public void caseAFuncCallExp(AFuncCallExp node)
    {
        node.getName().apply(this);
        puts("(");
        List<Node> args = new ArrayList<Node>(node.getParam());
        printList(args);
        puts(")");
    }
    
    /* append */
    public void caseAAppendExp(AAppendExp node)
    {
        puts("append");
        puts("(");
        puts(node.getId().getText());
        puts(",");
        node.getExp().apply(this);
        puts(")");
    }
    
    /* int */
    public void caseAIntExp(AIntExp node)
    {
        puts(node.getIntLit().getText());
    }
    
    /* float */
    public void caseAFloatExp(AFloatExp node)
    {
        puts(node.getFloatLit().getText());
    }
    
    /* rune */
    public void caseARuneExp(ARuneExp node)
    {
        puts(node.getRuneLit().getText());
    }
    
    /* string */
    public void caseAStrExp(AStrExp node)
    {
        puts(node.getStrLit().getText());
    }
    
    /* identifier */
    public void caseAIdExp(AIdExp node)
    {
        puts(node.getId().getText());
    }
    
    /* blank identifier */
    public void caseABlankExp(ABlankExp node)
    {
        puts("_");
    }
    
    
}