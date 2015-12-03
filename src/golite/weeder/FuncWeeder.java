package golite.weeder;

import golite.*;
import golite.node.*;
import golite.analysis.*;
import java.util.*;

// checks for return statements

public class FuncWeeder extends DepthFirstAdapter {
    
    private static boolean hasRet;
    
    public static boolean hasReturn(Node node) {
        hasRet = false;
        node.apply(new FuncWeeder());
        return hasRet;
    }
	
    /* func declaration */
    public void caseAFuncDecStmt(AFuncDecStmt node)
    {
        Node lastStmt = null;
        List<Node> list = new ArrayList<Node>(node.getStmt());
        Iterator<Node> itr = list.iterator();
        while (itr.hasNext()) {
            hasRet = false;
            lastStmt = itr.next();
            lastStmt.apply(this);
        }
        // if last statement in list is not return or block or if-else,
        // then there is a path without a return statement
        if (lastStmt!=null && !(lastStmt instanceof AReturnStmt || lastStmt instanceof AIfStmt
              || lastStmt instanceof ABlockStmt)) {
            hasRet = false;
        }
    }
    
    
    /* return */
    public void caseAReturnStmt(AReturnStmt node)
    {
        hasRet = true;
    }
    
    /* block */
    public void caseABlockStmt(ABlockStmt node)
    {
        Node lastStmt = null;
        List<Node> list = new ArrayList<Node>(node.getStmt());
        Iterator<Node> itr = list.iterator();
        while (itr.hasNext()) {
            hasRet = false;
            lastStmt = itr.next();
            lastStmt.apply(this);
        }
        if (lastStmt!=null && !(lastStmt instanceof AReturnStmt || lastStmt instanceof AIfStmt
              || lastStmt instanceof ABlockStmt)) {
            hasRet = false;
        }
    }
    
    
    /* if */
    public void caseAIfStmt(AIfStmt node)
    {
        // check that if-body has return statement along every path
        Node lastStmt = null;
        boolean ifHasReturn = false;
        List<Node> list = new ArrayList<Node> (node.getTrue());
        Iterator<Node> itr = list.iterator();
        while (itr.hasNext()) {
            lastStmt = itr.next();
            ifHasReturn = hasReturn(lastStmt);
        }
        if (lastStmt!=null && !(lastStmt instanceof AReturnStmt || lastStmt instanceof AIfStmt
              || lastStmt instanceof ABlockStmt)) {
            ifHasReturn = false;
        }
        
        // check that each else-if-body has return statement along every path
        boolean ifElseHasReturn = true;
        list = new ArrayList<Node>(node.getElseIf());
        itr = list.iterator();
        if (itr.hasNext()) {
            ifElseHasReturn = false;
            lastStmt = itr.next();
            ifElseHasReturn = hasReturn(lastStmt);
        }
        while (itr.hasNext() && itr.next()!=null && ifElseHasReturn) {
            lastStmt = itr.next();
            ifElseHasReturn = ifElseHasReturn && hasReturn(lastStmt);
        }
        
        // check that else-body has return statement along every path
        boolean elseHasReturn = false;
        list = new ArrayList<Node>(node.getFalse());
        if(!list.isEmpty()) {
            itr = list.iterator();
            while (itr.hasNext()) {
                lastStmt = itr.next();
                elseHasReturn = hasReturn(lastStmt);
            }
            if (lastStmt!=null &&  !(lastStmt instanceof AReturnStmt || lastStmt instanceof AIfStmt
                  || lastStmt instanceof ABlockStmt)) {
                elseHasReturn = false;
            }
        }
        hasRet = ifHasReturn && ifElseHasReturn && elseHasReturn;
    }
    
    
    /* else if */
    public void caseAElseIf(AElseIf node)
    {
        Node lastStmt = null;
        List<Node> list = new ArrayList<Node>(node.getBody());
        Iterator<Node> itr = list.iterator();
        while (itr.hasNext()) {
            hasRet = false;
            lastStmt = itr.next();
            lastStmt.apply(this);
        }
        if (lastStmt!=null && !(lastStmt instanceof AReturnStmt || lastStmt instanceof AIfStmt
              || lastStmt instanceof ABlockStmt)) {
            hasRet = false;
        }
    }
    


}