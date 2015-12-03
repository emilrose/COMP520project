package golite.weeder;

import golite.*;
import golite.node.*;
import golite.analysis.*;
import java.util.*;

public class Weeder extends DepthFirstAdapter {
     public class WeedingException extends RuntimeException {
        public WeedingException(String message) {
            super(message);
        }
     }
    
    private static ArrayList<String> stringAliasList = new ArrayList<String>();
    
    
    public Weeder(){}
    
    public static void weed(Start tree) {
	tree.apply(new Weeder());

    }

    /* Adds the ID of every alias for string to our list */
    public void inATypeDecStmt(ATypeDecStmt node) throws WeedingException{
	PType t = node.getType();
	if(t instanceof AStringType){
	    PExp e = node.getExp();
	    if(e instanceof AIdExp){
		AIdExp ie = (AIdExp) e;
		TId i = ie.getId();
		String s = i.getText();
		stringAliasList.add(s);
	    }
	    if(e instanceof ABlankExp){
		stringAliasList.add("_");
	    }
	}
    }
    


    /* Ensures the number of expressions on the LHS of an assignment statement match the number of expressions on the RHS */
    public void outAAssignEqStmt(AAssignEqStmt node) throws WeedingException{
        int leftsize = node.getL().size();
        int rightsize = node.getR().size();
	if(leftsize != rightsize) throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Assignment has a different number of expressions on LHS and RHS.");
    }

    /* Ensures the keyword "break" only appears within a loop or switch statement*/
    public void outABreakStmt(ABreakStmt node) throws WeedingException{
	Node parent = node.parent();
	while(parent !=null){
	    if(parent instanceof AForStmt || parent instanceof AInfiniteLoopStmt || parent instanceof AWhileStmt || parent instanceof ASwitchStmt) return;
	    parent=parent.parent();
	}
	throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Break found outside of a loop.");
    }

    /* Ensures the keywords "continue" only appears within a loop */
    public void outAContinueStmt(AContinueStmt node) throws WeedingException{
        Node parent = node.parent();
	while(parent !=null){
	    if(parent instanceof AForStmt || parent instanceof AInfiniteLoopStmt || parent instanceof AWhileStmt) return;
	    parent=parent.parent();
	}
	throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Continue found outside of a loop.");
    }
    
    /* Ensures a switch statement has only one "default" */
    public void outASwitchStmt(ASwitchStmt node) throws WeedingException{
	LinkedList<PSwitchExp> switchexprs = node.getSwitchExp();
	int numOfDefaults = 0;
	for(PSwitchExp switchexp : switchexprs){
	    if (switchexp instanceof ASwitchExp){
		ASwitchExp aswitchexp = (ASwitchExp) switchexp;
		if(aswitchexp.getExp().size()==0) numOfDefaults++;
	    }
	}
	if(numOfDefaults>1) throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Switch statement with " + numOfDefaults + " defaults is not allowed.");
    }

    /* Ensures the lvalue_list in a short_decl_statement is an id_list, and that the number of expressions on the left matches those on the right. */
    public void outAShortDeclStmt(AShortDeclStmt node) throws WeedingException{
	LinkedList<PExp> leftlist = node.getL();
	for(PExp exp : leftlist){
	    if(!(exp instanceof AIdExp || exp instanceof ABlankExp)){
	        throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Invalid declaration.");
	    }
	}
	int leftsize = node.getL().size();
        int rightsize = node.getR().size();
	if(leftsize != rightsize) throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Declaration has a different number of expressions on LHS and RHS.");
        
        /* Ensures the short_decl_statement is not of the form "_ := exp" */
        if (leftsize == 1) {
            List<PExp> copy = new ArrayList<PExp>(node.getL());
            for(PExp e : copy)
            {
                if (e instanceof ABlankExp) {
                    throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] No new variables on left side.");
                }
            }
        }
    }
 
    /* Ensures the number of expresions on the left of a var_dec_short matches those on the right. */
    public void outAVarDecStmt(AVarDecStmt node) throws WeedingException{
	int leftsize = node.getId().size();
        int rightsize = node.getVal().size();
	if(leftsize>0 && rightsize>0 && leftsize != rightsize) throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Declaration has a different number of expressions on LHS and RHS.");
    }

      /* Ensures a for loop can't have a short declaration statement as its post statement. */
    public void outAForStmt(AForStmt node) throws WeedingException
	{
	    PStmt s = node.getCond();
	    if(s !=null){
		if(s instanceof AShortDeclStmt) throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Short declaration statement as post statemet in a for loop.");
	    }
	    
	}

          /* Ensures that one cannot type cast to string directly. */
    public void outATypeCastExp(ATypeCastExp node) throws WeedingException
	{
	    PType t = node.getType();
	    if(t instanceof AStringType) throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Attempted cast to string.");
	}

    /* Ensures that one cannot type cast to string using an alias. */
    public void outAFuncCallExp(AFuncCallExp node) throws WeedingException
	{
	    PExp e = node.getName();
	    if(e instanceof AIdExp){
		AIdExp ie = (AIdExp) e;
		TId i = ie.getId();
		String s = i.getText();
		if(stringAliasList.contains(s))  throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Attempted cast to an alias of a string.");
	    }
	    else if(e instanceof ABlankExp){
		if(stringAliasList.contains("_"))  throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Attempted cast to an alias of a string.");
	    }
	}
    
    /* Ensures that any function that returns a value has a return statement on every execution path. */
    public void outAFuncDecStmt(AFuncDecStmt node) throws WeedingException
    {
        if (node.getType() != null) {
            boolean hasRet = FuncWeeder.hasReturn(node);
            if (!hasRet) {
                throw new WeedingException("[line " + LineNumber.getLineNumber(node) + "] Missing return statement in function.");
            }
        }
        
    }
    
    
}
