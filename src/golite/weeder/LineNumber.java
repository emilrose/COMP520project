package golite.weeder;

import golite.parser.*;
import golite.lexer.*;
import golite.node.*;
import golite.analysis.*;
import java.util.*;

/* Finds line numbers of AST nodes for error messages.
 Because of the way the AST was constructed, there are a few rare
 cases where the line number is not retrievable (ex: "var()"), but 
 we will probably never need the line numbers for those cases.
 */

public class LineNumber extends DepthFirstAdapter {
    
    private static int lineNumber;
    private static boolean done;
    
    // returns -1 if line number not retrievable
    public static int getLineNumber(Node node)
    {
        lineNumber = 1;
        done = false;
        node.apply(new LineNumber());
        if (done) {
            return lineNumber;
        }
        else {
            return -1;
        }
    }
    
    
    /* var declaration */
    public void caseAVarDecStmt(AVarDecStmt node)
    {
        List<PExp> copy = new ArrayList<PExp>(node.getId());
        for(PExp e : copy)
        {
            if (!done) {
                e.apply(this);
            }
            else {
                break;
            }
        }
    }
    
    /* type declaration */
    public void caseATypeDecStmt(ATypeDecStmt node)
    {
        node.getExp().apply(this);
    }
    
    /* func declaration */
    public void caseAFuncDecStmt(AFuncDecStmt node)
    {
        node.getExp().apply(this);
    }
    
    /* ids_type */
    public void caseAIdsType(AIdsType node)
    {
        node.getType().apply(this);
    }
    
    /* exp statement */
    public void caseAExpStmt(AExpStmt node)
    {
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
    }
    
    /* assign_eq statement */
    public void caseAAssignEqStmt(AAssignEqStmt node)
    {
        List<PExp> copy = new ArrayList<PExp>(node.getL());
        for(PExp e : copy)
        {
            if (!done) {
                e.apply(this);
            }
            else {
                break;
            }
        }
    }
    
    /* plus_eq statement */
    public void caseAPlusEqStmt(APlusEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* minus_eq statement */
    public void caseAMinusEqStmt(AMinusEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* star_eq statement */
    public void caseAStarEqStmt(AStarEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* slash_eq statement */
    public void caseASlashEqStmt(ASlashEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* mod_eq statement */
    public void caseAModEqStmt(AModEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* amp_eq statement */
    public void caseAAmpEqStmt(AAmpEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* l_shift_eq statement */
    public void caseALShiftEqStmt(ALShiftEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* r_shift_eq statement */
    public void caseARShiftEqStmt(ARShiftEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* amp_caret_eq statement */
    public void caseAAmpCaretEqStmt(AAmpCaretEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* pipe_eq statement */
    public void caseAPipeEqStmt(APipeEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* caret_eq statement */
    public void caseACaretEqStmt(ACaretEqStmt node)
    {
        node.getL().apply(this);
    }
    
    /* short declaration */
    public void caseAShortDeclStmt(AShortDeclStmt node)
    {
        List<PExp> copy = new ArrayList<PExp>(node.getL());
        for(PExp e : copy)
        {
            if (!done) {
                e.apply(this);
            }
            else {
                break;
            }
        }
    }
    
    /* increment */
    public void caseAIncStmt(AIncStmt node)
    {
        node.getExp().apply(this);
    }
    
    /* decrement */
    public void caseADecStmt(ADecStmt node)
    {
        node.getExp().apply(this);
    }
    
    /* print */
    public void caseAPrintStmt(APrintStmt node)
    {
        lineNumber = node.getPrint().getLine();
        done = true;
    }
    
    /* println */
    public void caseAPrintlnStmt(APrintlnStmt node)
    {
        lineNumber = node.getPrintln().getLine();
        done = true;
    }
    
    /* return */
    public void caseAReturnStmt(AReturnStmt node)
    {
        lineNumber = node.getReturn().getLine();
        done = true;
    }
    
    /* if */
    public void caseAIfStmt(AIfStmt node)
    {
        lineNumber = node.getIf().getLine();
        done = true;
    }
    
    /* else if */
    public void caseAElseIf(AElseIf node)
    {
        if(node.getStmt() != null && !(node.getStmt() instanceof AEmptyStmt))
        {
            node.getStmt().apply(this);
        }
        if(!done && node.getExp() != null)
        {
            node.getExp().apply(this);
        }
    }
    
    /* switch statement */
    public void caseASwitchStmt(ASwitchStmt node)
    {
        lineNumber = node.getSwitch().getLine();
        done = true;
    }
    
    /* switch expression */
    public void caseASwitchExp(ASwitchExp node)
    {
        List<PExp> copy = new ArrayList<PExp>(node.getExp());
        for(PExp e : copy)
        {
            if (!done) {
                e.apply(this);
            }
            else {
                break;
            }
        }
        List<PStmt> copy2 = new ArrayList<PStmt>(node.getStmt());
        for(PStmt e : copy2)
        {
            if (!done) {
                e.apply(this);
            }
            else {
                break;
            }
        }
    }
    
    /* infinite loop */
    public void caseAInfiniteLoopStmt(AInfiniteLoopStmt node)
    {
        lineNumber = node.getFor().getLine();
        done = true;
    }
    
    /* while */
    public void caseAWhileStmt(AWhileStmt node)
    {
        lineNumber = node.getFor().getLine();
        done = true;
    }
    
    /* for */
    public void caseAForStmt(AForStmt node)
    {
        lineNumber = node.getFor().getLine();
        done = true;
    }
    
    /* block */
    public void caseABlockStmt(ABlockStmt node)
    {
        List<PStmt> copy = new ArrayList<PStmt>(node.getStmt());
        for(PStmt e : copy)
        {
            if (!done) {
                e.apply(this);
            }
            else {
                break;
            }
        }
    }
    
    /* break */
    public void caseABreakStmt(ABreakStmt node)
    {
        lineNumber = node.getBreak().getLine();
        done = true;
    }
    
    /* continue */
    public void caseAContinueStmt(AContinueStmt node)
    {
        lineNumber = node.getContinue().getLine();
        done = true;
    }
    
    /* int type */
    public void caseAIntType(AIntType node)
    {
        lineNumber = node.getInt().getLine();
        done = true;
    }
    
    /* float type */
    public void caseAFloatType(AFloatType node)
    {
        lineNumber = node.getFloat64().getLine();
        done = true;
    }
    
    /* bool type */
    public void caseABoolType(ABoolType node)
    {
        lineNumber = node.getBool().getLine();
        done = true;
    }
    
    /* rune type */
    public void caseARuneType(ARuneType node)
    {
        lineNumber = node.getRune().getLine();
        done = true;
    }
    
    /* string type */
    public void caseAStringType(AStringType node)
    {
        lineNumber = node.getString().getLine();
        done = true;
    }
    
    /* slice type */
    public void caseASliceType(ASliceType node)
    {
        node.getType().apply(this);
    }
    
    /* array type */
    public void caseAArrayType(AArrayType node)
    {
        lineNumber = node.getIntLit().getLine();
    }
    
    /* struct type */
    public void caseAStructType(AStructType node)
    {
        List<PField> copy = new ArrayList<PField>(node.getField());
        for(PField e : copy)
        {
            if (!done) {
                e.apply(this);
            }
            else {
                break;
            }
        }
    }
    
    /* field */
    public void caseAField(AField node)
    {
        List<PExp> copy = new ArrayList<PExp>(node.getExp());
        for(PExp e : copy)
        {
            if (!done) {
                e.apply(this);
            }
            else {
                break;
            }
        }
        if(!done)
        {
            node.getType().apply(this);
        }
    }
    
    /* id type */
    public void caseAAliasType(AAliasType node)
    {
        lineNumber = node.getId().getLine();
        done = true;
    }
    
    /* || */
    public void caseABinOrExp(ABinOrExp node)
    {
        node.getL().apply(this);
    }
    
    /* && */
    public void caseABinAndExp(ABinAndExp node)
    {
        node.getL().apply(this);
    }
    
    /* == */
    public void caseAEqExp(AEqExp node)
    {
        node.getL().apply(this);
    }
    
    /* != */
    public void caseANeqExp(ANeqExp node)
    {
        node.getL().apply(this);
    }
    
    /* < */
    public void caseALtExp(ALtExp node)
    {
        node.getL().apply(this);
    }
    
    /* <= */
    public void caseALeqExp(ALeqExp node)
    {
        node.getL().apply(this);
    }
    
    /* > */
    public void caseAGtExp(AGtExp node)
    {
        node.getL().apply(this);
    }
    
    /* >= */
    public void caseAGeqExp(AGeqExp node)
    {
        node.getL().apply(this);
    }
    
    /* plus */
    public void caseAPlusExp(APlusExp node)
    {
        node.getL().apply(this);
    }
    
    /* minus */
    public void caseAMinusExp(AMinusExp node)
    {
        node.getL().apply(this);
    }
    
    /* pipe */
    public void caseAPipeExp(APipeExp node)
    {
        node.getL().apply(this);
    }
    
    /* caret */
    public void caseACaretExp(ACaretExp node)
    {
        node.getL().apply(this);
    }
    
    /* star */
    public void caseAStarExp(AStarExp node)
    {
        node.getL().apply(this);
    }
    
    /* slash */
    public void caseASlashExp(ASlashExp node)
    {
        node.getL().apply(this);
    }
    
    /* mod */
    public void caseAModExp(AModExp node)
    {
        node.getL().apply(this);
    }
    
    /* l_shift */
    public void caseALShiftExp(ALShiftExp node)
    {
        node.getL().apply(this);
    }
    
    /* r_shift */
    public void caseARShiftExp(ARShiftExp node)
    {
        node.getL().apply(this);
    }
    
    /* amp */
    public void caseAAmpExp(AAmpExp node)
    {
        node.getL().apply(this);
    }
    
    /* amp_caret */
    public void caseAAmpCaretExp(AAmpCaretExp node)
    {
        node.getL().apply(this);
    }
    
    /* u_plus */
    public void caseAUPlusExp(AUPlusExp node)
    {
        node.getExp().apply(this);
    }
    
    /* u_minus */
    public void caseAUMinusExp(AUMinusExp node)
    {
        node.getExp().apply(this);
    }
    
    /* u_bang */
    public void caseAUBangExp(AUBangExp node)
    {
        node.getExp().apply(this);
    }
    
    /* u_caret */
    public void caseAUCaretExp(AUCaretExp node)
    {
        node.getExp().apply(this);
    }
    
    /* typecast */
    public void caseATypeCastExp(ATypeCastExp node)
    {
        node.getType().apply(this);
    }
    
    /* selector */
    public void caseASelectorExp(ASelectorExp node)
    {
        node.getExp().apply(this);
    }
    
    /* index */
    public void caseAIndexingExp(AIndexingExp node)
    {
        node.getArray().apply(this);
    }
    
    /* func call */
    public void caseAFuncCallExp(AFuncCallExp node)
    {
        node.getName().apply(this);
    }
    
    /* append */
    public void caseAAppendExp(AAppendExp node)
    {
        lineNumber = node.getId().getLine();
        done = true;
    }
    
    /* int */
    public void caseAIntExp(AIntExp node)
    {
        lineNumber = node.getIntLit().getLine();
        done = true;
    }
    
    /* float */
    public void caseAFloatExp(AFloatExp node)
    {
        lineNumber = node.getFloatLit().getLine();
        done = true;
    }
    
    /* rune */
    public void caseARuneExp(ARuneExp node)
    {
        lineNumber = node.getRuneLit().getLine();
        done = true;
    }
    
    /* string */
    public void caseAStrExp(AStrExp node)
    {
        lineNumber = node.getStrLit().getLine();
        done = true;
    }
    
    /* identifier */
    public void caseAIdExp(AIdExp node)
    {
        lineNumber = node.getId().getLine();
        done = true;
    }
    
    /* blank identifier */
    public void caseABlankExp(ABlankExp node)
    {
        lineNumber = node.getBlank().getLine();
        done = true;
    }
    
    
}