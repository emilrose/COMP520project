package golite.symbol;

import golite.parser.*;
import golite.pretty.PrettyPrinter;
import golite.lexer.*;
import golite.node.*;
import golite.analysis.*;

import java.util.*;
import java.lang.*;

public class SymbolTable extends DepthFirstAdapter{
    
    public class TypeException extends RuntimeException {
       	public TypeException(java.lang.String message) {
            super(message);
        }
    }
    
    //Symbol Table
    private Deque<Map<String, Type>> scopes;
    //parallel table for types
    private Deque<Map<String, Type>> typeScopes;
    
    // typemap
    private HashMap<Node,Type> typemap;
    
    // hashmap to keep info about new vars in short var declarations (for codegen)
    private HashMap<Node, List<Node>> shortDeclInfo;
    
    // list of identifiers used in program (for codegen)
    private List<String> names;
    
    // hashmap to keep track of slice sizes (for codegen)
    private HashMap<String, Integer> slicemap;
    
    private boolean dump;
    
    // for init stmt scope in for/switch
    private int initCount;
    
    // return type of current function (for typechecking return statements)
    private Type funcReturnType;
    
    private String structName;
    
    // ensure that function body doesn't open new scope because it should be in the
    // same scope as the list of parameters
    private boolean isFuncBody;
    
    // type vs var identifier
    private boolean isType;
    
    // constructor
    public SymbolTable() {
        scopes = new LinkedList<Map<String, Type>>();
        typeScopes = new LinkedList<Map<String, Type>>();
        typemap = new HashMap<Node,Type>();
        shortDeclInfo = new HashMap<Node, List<Node>>();
        names = new ArrayList<String>();
        slicemap = new HashMap<String, Integer>();
        dump = false;
        initCount = 0;
        funcReturnType = null;
        structName = "";
        isFuncBody = false;
        isType = false;
    }
	
    public void typeCheck(Node node)
	{
        dump = false;
	    node.apply(this);
	}
    
    public void typeCheckAndDump(Node node)
    {
        dump = true;
        node.apply(this);
    }
    
    // return typemap
    public HashMap<Node, Type> getTypemap() {
        return typemap;
    }
    
    // return info about short var declarations
    public HashMap<Node, List<Node>> getShortDeclInfo() {
        return shortDeclInfo;
    }
    
    // return list of names used in program
    public List<String> getNames() {
        return names;
    }
    
    // return slicemap
    public HashMap<String, Integer> getSlicemap() {
        return slicemap;
    }
    
    //Symbol Table related methods
    public void newScope() {
        Map<String, Type> scope = new HashMap<String, Type>();
        scopes.push(scope);
        Map<String, Type> typeScope = new HashMap<String, Type>();
        typeScopes.push(typeScope);
    }
	
    public void unScope() {
	//check for flag before dumping symbol table
        if (dump) {
            dumpSymTable();
        }
        scopes.pop();
        typeScopes.pop();
    }
	
    public Deque<Map<String, Type>> getScopes() {
	return scopes;
    }
    
    public Deque<Map<String, Type>> getTypeScopes() {
        return typeScopes;
    }
	
    public Map<String,Type> getScope() {
	return scopes.peek();
    }
    
    public Map<String,Type> getTypeScope() {
        return typeScopes.peek();
    }
    
    public Type lookUpVarType(String id, int lineNum) {
        for (Map<String, Type> scope: scopes) {
            if (scope.containsKey(id)) {
                return scope.get(id);
            }
        }
        throw new TypeException("[" + lineNum + "] " + String.format("identifier %s was not already declared.", id));
    }
    
    public Type lookUpTypeOrVar (String id, int lineNum) {
        isType = false;
        Iterator <Map<String, Type>> itrV = scopes.iterator();
        Iterator <Map<String, Type>> itrT = typeScopes.iterator();
        while (itrV.hasNext()) {
            Map<String, Type> vScope = itrV.next();
            Map<String, Type> tScope = itrT.next();
            if (vScope.containsKey(id)) {
                return vScope.get(id);
            }
            if (tScope.containsKey(id)) {
                isType = true;
                return tScope.get(id);
            }
        }
        throw new TypeException("[" + lineNum + "] " + String.format("identifier %s was not already declared.", id));
        
    }
    
	
    public void declInScope(String id, Type type) {
        checkUndecl(id);
        Map<String, Type> scope = scopes.pop();
        scope.put(id, type);
        scopes.push(scope);
        if (!names.contains(id)) {
            names.add(id);
        }
    }
    
    public void declInTypeScope(String id, Type type) {
        checkUndecl(id);
        Map<String, Type> typeScope = typeScopes.pop();
        typeScope.put(id, type);
        typeScopes.push(typeScope);
        if (!names.contains(id)) {
            names.add(id);
        }
    }
	
    public void checkUndecl(String id) {
        Map<String, Type> scope = getScope();
        Map<String, Type> typeScope = getTypeScope();
        if (scope.containsKey(id) || typeScope.containsKey(id)) {
            throw new TypeException(String.format("identifier %s is already declared in this scope.", id));
        }
    }
	
    public void dumpSymTable() {
    
	System.out.println("---------------------------------------------");
	for (Map.Entry<String,Type> e: getScope().entrySet()) {
            if (e.getValue()!=null) {
                System.out.println("var " + e.getKey() + " : " + e.getValue().toString());
            }
            else {
                System.out.println("var " + e.getKey());
            }
	}
        
        for (Map.Entry<String,Type> e: getTypeScope().entrySet()) {
            if (e.getValue()!=null) {
                System.out.println("type " + e.getKey() + " : " + e.getValue().toString());
            }
            else {
                System.out.println("type " + e.getKey());
            }
        }
         
	System.out.println("---------------------------------------------");
         
    }
	
    //These global fields are used to circumvent the "case" methods returning void
    //To pretend they return something, we will store the most recent case visit in these global variables
    //and read from them.
    private Type type;
    private String id;
	
    public Type getAliasType(String id, Node node) {
	node.apply(this);
	Type refType = this.type;
	this.type = null;
	Type type = new AliasType(id, refType, true);
	return type;
    }
	
    public Type getType(Node node) {
	node.apply(this);
	Type type = this.type;
	this.type = null;
	//declare type as a var and not a type
	//only var decs get through here. func decs will go through the getAliasType above.
	if (type instanceof AliasType) {
	    AliasType aliasRef = (AliasType) type;
	    type = new AliasType(aliasRef.getAliasName(), aliasRef.getType(), false);
	}
        
	return type;
    }
    
        
	
    // gets id from AIdExp
    public String getId(Node node) {
        String id = "";
        if (node instanceof AIdExp) {
            AIdExp idNode = (AIdExp) node;
            id = idNode.getId().getText();
        }
	return id;
    }
	
	
    /* program */
    public void inAProgram(AProgram node)
	{
	    newScope();
	    declInScope("true", new BooleanType());
	    declInScope("false", new BooleanType());
	}
    
    public void outAProgram(AProgram node) {
        if (dump) {
            System.out.println("[end of program]");
        }
        unScope();
        
    }
    
    
    /*-----------------------------------------------------------------*/
    /* var declaration */
    // blank identifiers are not added to symbol table
    public void caseAVarDecStmt(AVarDecStmt node)
	{
	    inAVarDecStmt(node);
	    outAVarDecStmt(node);
	}
    
    public void inAVarDecStmt(AVarDecStmt node)
	{
        
	    List<PExp> ids = node.getId();
	    List<PExp> values = node.getVal();
        // typecheck right side before updating symbol table
        for (PExp e: values) {
            e.apply(this);
        }
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
	    PType typeNode = node.getType();
	    if (typeNode != null) {
            Type type = getType(typeNode);
            for (PExp idNode: ids) {
                if (idNode instanceof AIdExp) {
                    String id = getId(idNode);
                    try {
                        declInScope(id, type);
			typemap.put(idNode,type);
                    }
                    catch (TypeException e) {
                        throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(idNode) + "] identifier " + id + " already declared in this scope." );
                    }
                }
		
            }
            
            for (PExp e: values) {
                Type t = typemap.get(e);
                if (!areSameType(t, type)) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] mismatched types in assignment." );
                }
                if (t==null) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempted to assign void value to variable.");
                }
                if (t instanceof FunctionType) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempted to assign function as value.");
                }
            }
            
	    }

	}
    
    // var decl where type is not explicitly stated
    public void outAVarDecStmt(AVarDecStmt node)
	{
	    List<PExp> ids = node.getId();
	    List<PExp> values = node.getVal();
	    PType typeNode = node.getType();
	    if (typeNode == null) {
		for (int i=0; i<ids.size(); i++) {
		    Node idNode = ids.get(i);
                
		    if (idNode instanceof AIdExp) {
			String id = getId(idNode);
			Node valueNode = values.get(i);
			Type t = typemap.get(valueNode);
                if (t==null) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempted to assign void value to variable.");
                }
                if (t instanceof FunctionType) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempted to assign function as value.");
                }
			//System.out.println(id + t.toString());
		declInScope(id, t);
		typemap.put(idNode,t);
		    }
                
		}
	    }
    	
	}
    
    /* int type */
    public void caseAIntType(AIntType node)
	{
	    type = new IntType();
	}
    
    public void inAIdExp(AIdExp node)
	{
	    this.id = node.getId().getText();
	}
    
    @Override
    public void caseTId(TId node)
	{
	    this.id = node.getText();
	}
    
    /* float type */
    public void inAFloatType(AFloatType node)
	{
	    type = new Float64Type();
	}
    
    /* bool type */
    public void inABoolType(ABoolType node)
	{
	    type = new BooleanType();
	}
    
    /* rune type */
    public void inARuneType(ARuneType node)
	{
	    type = new RuneType();
	}
    
    /* string type */
    public void inAStringType(AStringType node)
	{
	    type = new StringType();
	}
    
    /* slice type */
    public void caseASliceType(ASliceType node)
	{
	    Type sliceType = getType(node.getType());
	    type = new SliceType(sliceType);
	}
    
    
    
    /* array type */
    public void caseAArrayType(AArrayType node)
	{
	    Type arrayType = getType(node.getType());
	    String size = node.getIntLit().getText();
	    type = new ArrayType(arrayType, size);
	}
    
    /* struct type */
    public void caseAStructType(AStructType node)
	{
	    newScope();
	    PField last = null;
        String outerStructName = structName;
        structName = "";
	    List<Map.Entry<List<String>,Type>> fields = new ArrayList<Map.Entry<List<String>,Type>>();
	    for (PField field: node.getField()) {
            last = field;
            List<String> ids = new ArrayList<String>();
            Type type = getType(((AField)field).getType());
            for (PExp id: ((AField)field).getExp()) {
                String fieldVarName = getId(id);
                if (((AField)field).getExp().size()==1 && fieldVarName.equals("")) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(id) + "] Struct with blank field." );
                }
                ids.add(fieldVarName);
                try {
                    declInScope(fieldVarName, type);
                    typemap.put(id, type);
                }
                catch (TypeException e) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(id) + "] identifier " + fieldVarName + " already declared in this scope." );
                }
            }
            Map.Entry<List<String>,Type> fieldEntry = new AbstractMap.SimpleEntry<List<String>,Type>(ids,type);
            fields.add(fieldEntry);
		
	    }
	    type = new StructType(fields, outerStructName);
        
        if (dump) {
            if (last!=null && golite.weeder.LineNumber.getLineNumber(last)>0) {
                System.out.println("[line " + golite.weeder.LineNumber.getLineNumber(last) + "]");
            }
        }
	    unScope();
	}
    
    /* field */
    //included in StructType above
//    public void caseAField(AField node)
//    {
//    }
    
    /* alias type */
    public void inAAliasType(AAliasType node)
	{
	    type = lookUpTypeOrVar(node.getId().getText(), golite.weeder.LineNumber.getLineNumber(node));
        if (!isType) {
            throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Variable used as type." );
        }
        else {
            isType = false;
        }
	}
    
    /*------------------------------------------------------------------*/
    
    /* type declaration */
    public void caseATypeDecStmt(ATypeDecStmt node)
    {
        // ignore declarations with blank identifier
        if (node.getExp() instanceof AIdExp) {
            String id = getId(node.getExp());
            if (! (node.getType() instanceof AStructType)) {
                Type type = getAliasType(id, node.getType());
                declInTypeScope(id, type);
                
            }
            else {
                structName = id;
                Type type = getType(node.getType());
                declInTypeScope(id, type);
                structName = "";
            }
            // TODO: deal with alias of alias
            //update fixed. Check AliasType's boolean field for info.
            
	    }
	}
    
    /* func declaration */
    public void inAFuncDecStmt(AFuncDecStmt node)
    {
        List<Type> paramTypes = new ArrayList<Type>();
        // parameters will open new scope
        Map<String, Type> newScope = new HashMap<String, Type>();
        if (node.getExp() instanceof AIdExp || node.getExp() instanceof ABlankExp) {
            List<Node> paramList = new ArrayList<Node>(node.getIdsType());
            Iterator<Node> itr = paramList.iterator();
            while (itr.hasNext()) {
                Node n = itr.next();
                if (!(n instanceof AIdsType)) {
                    break;
                }
                AIdsType paramGroup = (AIdsType) n;
                Type t = getType(paramGroup.getType());
                List<PExp> paramNames = new ArrayList<PExp>(paramGroup.getExp());
                String id;
                for(PExp e : paramNames)
                {
                    paramTypes.add(t);
                }
            }
            String funcId = getId(node.getExp());
            // void func has null return type
            Type returnType = null;
            if(node.getType() != null)
            {
                returnType = getType(node.getType());
            }
            funcReturnType = returnType;
            // add func to symbol table
            declInScope(funcId, new FunctionType(paramTypes, returnType));
            if(node.getExp() != null)
            {
                node.getExp().apply(this);
            }
            // start new scope
            newScope();
            // don't want func body to start another scope
            isFuncBody = true;
            itr = paramList.iterator();
            while (itr.hasNext()) {
                Node n = itr.next();
                if (!(n instanceof AIdsType)) {
                    break;
                }
                AIdsType paramGroup = (AIdsType) n;
                Type t = getType(paramGroup.getType());
                List<PExp> paramNames = new ArrayList<PExp>(paramGroup.getExp());
                String id;
                for(PExp e : paramNames)
                {
                    // add parameters to symbol table
                    if (e instanceof AIdExp) {
                        id = getId(e);
                        declInScope(id, t);
                    }
                }
            }
        }
    }
    
    public void caseAFuncDecStmt(AFuncDecStmt node)
    {
        inAFuncDecStmt(node);
        
        // don't recheck stuff already checked in inAFuncDecStmt
        
        if(node.getType() != null)
        {
            node.getType().apply(this);
        }
         
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getStmt());
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
         
        outAFuncDecStmt(node);
        
    }
    
    
    
    /* block */
    public void caseABlockStmt(ABlockStmt node)
	{
	    // starts new scope unless direct parent is a func declaration
	    if (!isFuncBody) {
            newScope();
	    }
	    boolean orig_isFuncBody = isFuncBody;
	    isFuncBody = false;
	    List<PStmt> copy = new ArrayList<PStmt>(node.getStmt());
	    PStmt last = null;
	    for(PStmt e : copy)
	    {
            e.apply(this);
            last = e;
	    }
	    isFuncBody = orig_isFuncBody;
	    // outABlockStmt(node);
        
	    // end of scope
        if (dump) {
            int lineNum;
            if (last!=null) {
                lineNum = golite.weeder.LineNumber.getLineNumber(last);
            }
            else {
                lineNum = golite.weeder.LineNumber.getLineNumber(node);
            }
            if (lineNum>0) {
                System.out.println("[line " + lineNum + "]");
            }
        }
	    unScope();
	}
    
    
    /* ids_type */
    public void caseAIdsType(AIdsType node)
	{
        // taken care of by inAFuncDecStmt
	}
    
    
    
    
    
    /* HELPER FUNCTIONS */
    
    /* returns true if two types are boolean, false otherwise */
    public boolean areBoolean(Type typeone, Type typetwo){
        if (areSameType(typeone, typetwo)) {
            typeone = removeAlias(typeone);
            typetwo = removeAlias(typetwo);
        }
        if(typeone instanceof BooleanType && typetwo instanceof BooleanType) return true;
        else return false;
    }
    
    /* returns true if two types are comparable, false otherwise */
    public boolean areComparable(Type typeone, Type typetwo){
        /* first check the basic types */
        if((typeone instanceof IntType && typetwo instanceof IntType) || (typeone instanceof Float64Type && typetwo instanceof Float64Type) || (typeone instanceof BooleanType && typetwo instanceof BooleanType) ||(typeone instanceof RuneType && typetwo instanceof RuneType) || (typeone instanceof StringType && typetwo instanceof StringType)) return true;
        /* structs are comparable if they correspond to the same type declaration and the fields are comparable */

        else if(typeone instanceof StructType && typetwo instanceof StructType){
            
            String name1 = ((StructType)typeone).getStructName();
            String name2 = ((StructType)typetwo).getStructName();
            if (name1==name2 || (name1.equals(name2) && name1.equals(""))) {
                StructType lefts = (StructType) typeone;
                StructType rights = (StructType) typetwo;
                List<Map.Entry<List<String>,Type>> leftlist = lefts.getFields();
                List<Map.Entry<List<String>,Type>> rightlist = rights.getFields();
                if (leftlist.size()!=rightlist.size()) {
                    return false;
                }
                for(int i=0; i<leftlist.size(); i++){
                    Type typel = leftlist.get(i).getValue();
                    Type typer = rightlist.get(i).getValue();
                    if(!areComparable(typel,typer)) {
                        return false;
                    }
                }
                return true;
            }
            else {
                return false;
            }
        }
        /* finally, if they are arrays, check that the underlying type is comparable */
        else if(typeone instanceof ArrayType && typetwo instanceof ArrayType){
            Type typel,typer;
            ArrayType lefta = (ArrayType) typeone;
            ArrayType righta = (ArrayType) typetwo;
            typel = lefta.getType();
            typer = righta.getType();
            if(!areComparable(typel,typer) || !(lefta.getSize().equals(righta.getSize()))) return false;
            else return true;
        }
        else if (typeone instanceof AliasType && typetwo instanceof AliasType) {
            AliasType left = (AliasType) typeone;
            AliasType right = (AliasType) typetwo;
            if (left.getAliasName()==right.getAliasName()) {
                return true;
            }
            else {
                return false;
            }

        }
        else return false;
    }
    
    /* returns true if two types are ordered, false otherise */
    public boolean areOrdered(Type typeone, Type typetwo){
        /* if they are both the same alias type, get the underlying type */
        if(areBinaryAlias(typeone,typetwo)){
            typeone = removeAlias(typeone);
            typetwo = removeAlias(typetwo);
        }//if exactly one is an alias, get it's underlying type 
//        else if (typeone instanceof AliasType && !(typetwo instanceof AliasType) 
//				||  !(typeone instanceof AliasType) && typetwo instanceof AliasType ) {
//			typeone = removeAlias(typeone);
//			typetwo = removeAlias(typetwo);
//		}
        if((typeone instanceof IntType && typetwo instanceof IntType) || (typeone instanceof Float64Type && typetwo instanceof Float64Type) || (typeone instanceof RuneType && typetwo instanceof RuneType) || (typeone instanceof StringType && typetwo instanceof StringType)) return true;
        else return false;
    }
    
   
    
    /* returns true if two types are numeric, false otherwise */
    public boolean areNumeric(Type typeone, Type typetwo){
        /* if they are both the same alias type, get the underlying type */
        if(areBinaryAlias(typeone,typetwo)){
            typeone = removeAlias(typeone);
            typetwo = removeAlias(typetwo);
        }//if exactly one is an alias, get it's underlying type 
//        else if (typeone instanceof AliasType && !(typetwo instanceof AliasType) 
//				||  !(typeone instanceof AliasType) && typetwo instanceof AliasType ) {
//	typeone = removeAlias(typeone);
//	typetwo = removeAlias(typetwo);
//}
        	
        if((typeone instanceof IntType && typetwo instanceof IntType) || (typeone instanceof Float64Type && typetwo instanceof Float64Type) || (typeone instanceof RuneType && typetwo instanceof RuneType)) return true;
        else return false;
    }
    
    
    public boolean castableBasicNum(Type t) {
        if (t instanceof IntType || t instanceof Float64Type || t instanceof RuneType ) {
            return true;
        }
        else {
            return false;
        }
    }
    
    /* returns true if two types are integral, false otherwise */
    public boolean areIntegral(Type typeone, Type typetwo){
        /* if they are both the same alias type, get the underlying type */
        if(areBinaryAlias(typeone,typetwo)){
            typeone = removeAlias(typeone);
            typetwo = removeAlias(typetwo);
        }
        if((typeone instanceof IntType && typetwo instanceof IntType) || (typeone instanceof RuneType && typetwo instanceof RuneType)) return true;
        else return false;
    }
    
    /* returns true if the two types are the same, false otherwise */
    public boolean areSameType(Type typeone, Type typetwo){
        if(areBinaryAlias(typeone,typetwo)) return true;
        else if (typeone instanceof ArrayType && typetwo instanceof ArrayType){
            Type typel;
            Type typer;
            ArrayType lefta = (ArrayType) typeone;
            ArrayType righta = (ArrayType) typetwo;
            typel = lefta.getType();
            typer = righta.getType();
            if(!areComparable(typel,typer)) return false;
            else return true;
        }
        /* areComparable takes care of the basic types except boolean*/
        else if(areComparable(typeone,typetwo)) return true;
        else if (typeone instanceof BooleanType && typetwo instanceof BooleanType) return true;
        /* compare every field in the two structs */
        
        /* if slices, compare their underlying types */
        else if (typeone instanceof SliceType && typetwo instanceof SliceType){
            Type typel,typer;
            SliceType lefta = (SliceType) typeone;
            SliceType righta = (SliceType) typetwo;
            typel = lefta.getType();
            typer = righta.getType();
            if(areSameType(typel,typer)) return true;
            else return false;
        }
        /* function return type might be null */
        else if (typeone==null && typetwo==null) {
            return true;
        }
        /* otherwise, return false */
        else {
            return false;
        }
    }
    
    /* returns true if the two types are the same alias, false otherwise */
    public boolean areBinaryAlias(Type tone, Type ttwo){
        if(tone instanceof AliasType && ttwo instanceof AliasType){
            AliasType aliasone = (AliasType) tone;
            AliasType aliastwo = (AliasType) ttwo;
		    if (aliasone.getAliasName().equals(aliastwo.getAliasName())) return true;
		    else return false;
        }
        else return false;
    }
    
    /* returns the underlying type of a type */
    public Type removeAlias(Type t){
        while(t instanceof AliasType){
            AliasType aliast = (AliasType) t;
            t = aliast.getType();
        }
        return t;
    }
    
    
    /* STATEMENT TYPING */
    
    
    /* 2.4 Return Statements */
    public void outAReturnStmt(AReturnStmt node) throws TypeException{
        PExp exp = node.getExp();
        
        if(exp==null){
            if (!(funcReturnType == null)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Empty return statement in a non-void function.");
        }
        else {
            Type exptype = typemap.get(exp);
            if (!areSameType(exptype,funcReturnType)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Return type and enclosing function type do not match.");
        }
        
    }

    
    /* 2.5 Short Declaration Statements */
    public void caseAShortDeclStmt(AShortDeclStmt node)
    {
        inAShortDeclStmt(node);
        {
            List<PExp> copy = new ArrayList<PExp>(node.getR());
            for(PExp e : copy)
            {
                e.apply(this);
            }
        }
        outAShortDeclStmt(node);
    }
    
    // check init statements separately
    public void outAShortDeclStmt(AShortDeclStmt node) throws TypeException{
        
        // keep track of new variables
        List<Node> newVars = new ArrayList<Node>();
        
        List<String> leftVars = new ArrayList<String>();
        
        int newVarCount = 0;
        LinkedList<PExp> leftlist = node.getL();
        LinkedList<PExp> rightlist = node.getR();
        ListIterator<PExp> iterl = leftlist.listIterator(0);
        ListIterator<PExp> iterr = rightlist.listIterator(0);
        while(iterl.hasNext() && iterr.hasNext()){
            PExp leftexp = iterl.next();
            PExp rightexp = iterr.next();
            Type exptype;
            java.lang.String idname;
            // MODIFIED
            if(leftexp instanceof AIdExp){
                AIdExp idexp = (AIdExp) leftexp;
                TId id = idexp.getId();
                idname = id.getText();
                if (leftVars.contains(idname)) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Repeated variable " + idname + " on left.");
                }
                leftVars.add(idname);
                Map<String, Type> scope = getScope();
                Map<String, Type> typeScope = getScope();
                if (!scope.containsKey(idname) && !typeScope.containsKey(idname)) {
                    newVars.add(leftexp);
                    newVarCount++;
                    Type t = typemap.get(rightexp);
                    if (t==null) {
                        throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempted to assign void value to variable " + idname + ".");
                    }
                    if (t instanceof FunctionType) {
                        throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempted to assign function as value " + idname + ".");
                    }
                    declInScope(idname, t);
                }
                else {
                    exptype = lookUpVarType(idname, golite.weeder.LineNumber.getLineNumber(node));
                    if(!(exptype == null)){
                        Type righttype = typemap.get(rightexp);
                        if(! (areSameType(exptype,righttype))){
                            throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Types on left and right don't match for short declaration statment at: " + idname + ".");
                        }
                    }
                }
            }
        }
        if (newVarCount==0) {
            throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] No new variables on left.");
        }
        
        shortDeclInfo.put(node, newVars);
    }
    
    /* increment */
    public void outAIncStmt(AIncStmt node)
    {
        Type expType = typemap.get(node.getExp());
        expType = removeAlias(expType);
        if (! (expType instanceof IntType || expType instanceof Float64Type || expType instanceof RuneType)) {
            throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempted to increment non-numeric variable.");
        }
    }
    
    /* decrement */
    public void outADecStmt(ADecStmt node)
    {
        Type expType = typemap.get(node.getExp());
        expType = removeAlias(expType);
        if (! (expType instanceof IntType || expType instanceof Float64Type || expType instanceof RuneType)) {
            throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempted to decrement non-numeric variable.");
        }
    }

    
    /* 2.7 Assignment Statements */
    public void outAAssignEqStmt(AAssignEqStmt node) throws TypeException{
        LinkedList<PExp> leftlist = node.getL();
        LinkedList<PExp> rightlist = node.getR();
        Iterator<PExp> iteratorone = leftlist.iterator();
        Iterator<PExp> iteratortwo = rightlist.iterator();
        while(iteratorone.hasNext() && iteratortwo.hasNext()){
            PExp expone = iteratorone.next();
            PExp exptwo = iteratortwo.next();
            Type typeone = typemap.get(expone);
            Type typetwo = typemap.get(exptwo);
            if (!(expone instanceof ABlankExp) && !areSameType(typeone,typetwo)) {
                dumpSymTable();
                throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Types on left and right don't match for assignment statement.");
            }
        }
    }
    
    /* 2.8 Op-Assignment Statements */
    public void outAAmpCaretEqStmt(AAmpCaretEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areIntegral(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use bit clear statement on two incompatible types.");
    }
    
    public void outAAmpEqStmt(AAmpEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areIntegral(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use bitwise AND statement on two incompatible types.");
    }
    
    public void outACaretEqStmt(ACaretEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areIntegral(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use bitwise XOR statement on two incompatible types.");
    }
    
    public void outALShiftEqStmt(ALShiftEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areIntegral(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use left shift statement on two incompatible types.");
    }
    
    public void outARShiftEqStmt(ARShiftEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areIntegral(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use right shift on two incompatible types.");
    }
    
    public void outAPipeEqStmt(AAmpCaretEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areIntegral(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use bitwise OR statement on two incompatible types.");
    }
    
    public void outAPlusEqStmt(APlusEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(typel instanceof StringType && typer instanceof StringType) typemap.put(node,typel);
        else if(areNumeric(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to add two incompatible types in a statement.");
    }
    
    public void outAMinusEqStmt(AMinusEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areNumeric(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to subtract two incompatible types in a statement.");
    }
    
    public void outAModEqStmt(AModEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areNumeric(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to mod two incompatible types in a statement.");
    }
    
    public void outASlashEqStmt(ASlashEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areNumeric(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to divide two incompatible types in a statement.");
    }
    
    public void outAStarEqStmt(AStarEqStmt node) throws TypeException{
        PExp l = node.getL();
        PExp r = node.getR();
        Type typel = typemap.get(l);
        Type typer = typemap.get(r);
        if(areNumeric(typel,typer)) typemap.put(node,typel);
        else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to multiply two incompatible types in a statement.");
    }
    
    /* 2.10 Print Statements */
    public void outAPrintStmt(APrintStmt node) throws TypeException{
        LinkedList<PExp> explist = node.getExp();
        for(PExp exp : explist){
            Type exptype = typemap.get(exp);
            exptype = removeAlias(exptype);
            if(!(exptype instanceof IntType || exptype instanceof BooleanType || exptype instanceof Float64Type || exptype instanceof StringType || exptype instanceof RuneType)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Print of non-basic type.");
        }
        
    }
    
    public void outAPrintlnStmt(APrintlnStmt node) throws TypeException{
        LinkedList<PExp> explist = node.getExp();
        for(PExp exp : explist){
            Type exptype = typemap.get(exp);
            exptype = removeAlias(exptype);
            if(!(exptype instanceof IntType || exptype instanceof BooleanType || exptype instanceof Float64Type || exptype instanceof StringType || exptype instanceof RuneType)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Println of non-basic type.");
            
        }
    }
    
    /* 2.11 Loop Statements */
    public void outAWhileStmt(AWhileStmt node) throws TypeException{
        if(node.getExp() != null)
        {
            Node e = node.getExp();
            Type t = typemap.get(e);
            t = removeAlias(t);
            if(!(t instanceof BooleanType)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] While loop with non-boolean condition.");
        }
    }
    
    
    /* For Statements */
    public void inAForStmt(AForStmt node) {
        if(node.getInit() != null)
        {
            newScope();
            initCount++;
        }
    }
    
    public void outAForStmt(AForStmt node) throws TypeException{
        if(node.getExp() != null)
        {
            Node e = node.getExp();
            Type t = typemap.get(e);
            t = removeAlias(t);
            if(!(t instanceof BooleanType)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] For loop with non-boolean condition.");
        }
        if (initCount>0 && node.getInit() != null) {
            initCount--;
            if (dump) {
                List<PStmt> copy = new ArrayList<PStmt>(node.getBody());
                if (copy.size()>0 && golite.weeder.LineNumber.getLineNumber(copy.get(copy.size()-1))>0)
                {
                    Node n = copy.get(copy.size()-1);
                    System.out.println("[line " + golite.weeder.LineNumber.getLineNumber(n) + "]");
                }
            }
            unScope();
        }
    }
    
    /* 2.12 If Statements */
    public void caseAIfStmt(AIfStmt node)
    {
        int initCount = 0;
        //inAIfStmt(node);
        if(node.getStmt() != null)
        {
            newScope();
            initCount++;
        }
        if(node.getIf() != null)
        {
            node.getIf().apply(this);
        }
        if(node.getStmt() != null)
        {
            node.getStmt().apply(this);
        }
        if(node.getExp() != null)
        {
            node.getExp().apply(this);
        }
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getTrue());
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        {
            List<PElseIf> copy = new ArrayList<PElseIf>(node.getElseIf());
            Iterator<PElseIf> itr = copy.iterator();
            while (itr.hasNext()) {
                PElseIf e = itr.next();
                AElseIf elseIf = (AElseIf) e;
                if(elseIf.getStmt() != null)
                {
                    newScope();
                    initCount++;
                }
                e.apply(this);
                if(elseIf.getExp() != null)
                {
                    Type t = typemap.get(elseIf.getExp());
                    t = removeAlias(t);
                    if(!(t instanceof BooleanType)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(elseIf) + "] If-else statement with non-boolean condition.");
                }
            }
        }
        {
            List<PStmt> copy = new ArrayList<PStmt>(node.getFalse());
            for(PStmt e : copy)
            {
                e.apply(this);
            }
        }
        //outAIfStmt(node);
        if(node.getExp() != null)
        {
            Node e = node.getExp();
            Type t = typemap.get(e);
            t = removeAlias(t);
            if(!(t instanceof BooleanType)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] If statement with non-boolean condition.");
        }
        if (initCount>0) {
            if (dump) {
                List<PStmt> copy = new ArrayList<PStmt>(node.getTrue());
                if (copy.size()>0 && golite.weeder.LineNumber.getLineNumber(copy.get(copy.size()-1))>0)
                {
                    Node n = copy.get(copy.size()-1);
                    System.out.println("[line " + golite.weeder.LineNumber.getLineNumber(n) + "]");
                }
            }
            while (initCount>0) {
                unScope();
                initCount--;
            }
        }
    }
    
    
    /* 2.13 Switch Statements */
    
    public void inASwitchStmt(ASwitchStmt node) throws TypeException {
        if(node.getStmt() != null)
        {
            newScope();
            initCount++;
        }
    }
    
    /* switch expression */
    public void outASwitchExp(ASwitchExp node)
	{
	    Node p = node.parent();
	    ASwitchStmt parent;
        
	    while (p!=null && !(p instanceof ASwitchStmt)) {
		p = p.parent();
	    }
	    if (!(p instanceof ASwitchStmt)) {
		throw new TypeException ("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Case expression without switch statement parent.");
	    }
	    parent = (ASwitchStmt) p;
        
	    List<Node> expList = new ArrayList<Node>(node.getExp());
	    // case clause
	    if(!expList.isEmpty()) {
		// switch statement has no expression so case expressions must be bool
		if(parent.getExp() == null) {
		    for(Node exp : expList)
		    {
                Type caseType = typemap.get(exp);
                if (!(caseType instanceof BooleanType) ) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Case expression on empty switch statement with non-boolean type.");
                }
		    }
		}
		// check that switch statement expression type matches type of case expressions
		else {
                
		    Node e = parent.getExp();
		    Type t = typemap.get(e);
		    for(Node exp : expList)
		    {
			Type caseType = typemap.get(exp);
			if (!(areSameType(caseType, t)) ) {
			    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Switch expression in switch statement with mismatched types.");
			}
		    }
		}
	    }
	}
    
    public void outASwitchStmt(ASwitchStmt node) throws TypeException {
        if (node.getExp() != null) {
            Type t = typemap.get(node.getExp());
            if (t==null) {
                throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Void used as value.");
            }
        }
        if (initCount>0 && node.getStmt() != null) {
            initCount--;
            if (dump) {
                List<PSwitchExp> copy = new ArrayList<PSwitchExp>(node.getSwitchExp());
                if (copy.size()>0 && golite.weeder.LineNumber.getLineNumber(copy.get(copy.size()-1))>0)
                {
                    Node n = copy.get(copy.size()-1);
                    if (n instanceof ASwitchExp) {
                        List<PStmt> copy2 = new ArrayList<PStmt>(((ASwitchExp)n).getStmt());
                        if (copy2.size()>0 && golite.weeder.LineNumber.getLineNumber(copy2.get(copy2.size()-1))>0) {
                            Node n2 = copy2.get(copy2.size()-1);
                            System.out.println("[line " + golite.weeder.LineNumber.getLineNumber(n2) + "]");
                        }
                    }
                }
            }
            unScope();
        }
    }
    
    
    
    /* EXPRESSION TYPING */
                   
    /* 3.1 Literals */
    /* just save the type of the node into the typemap */
    public void outAIntExp(AIntExp node) throws TypeException{
	typemap.put(node,new IntType());
    }
                   
    public void outAFloatExp(AFloatExp node) throws TypeException{
	typemap.put(node,new Float64Type());
    }
                   
    public void outARuneExp(ARuneExp node) throws TypeException{
	typemap.put(node,new RuneType());
    }
                   
    public void outAStrExp(AStrExp node) throws TypeException{
	typemap.put(node,new StringType());
    }
                   
    /* 3.2 Identifiers */
    /* grab the type out of the symboltable, check that it's not null, and then put it into the typemap with the node */
    public void outAIdExp(AIdExp node) throws TypeException{
        TId id = node.getId();
        java.lang.String name = id.getText();
        Type t = lookUpVarType(name, golite.weeder.LineNumber.getLineNumber(node));
        typemap.put(node,t);
	/*
	  if(t==null) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Identifier " + name + " used that cannot be found in symbol table.");
	  else typemap.put(node,t);
	*/
    }
    
                   
    /* 3.3 Unary Expressions */
    /* get the type out of the typemap, dealias it if necessary, and check that it's the correct type */
    public void outAUPlusExp(AUPlusExp node) throws TypeException{
	Node e = node.getExp();
	Type t = typemap.get(e);
	Type t_dealiased = removeAlias(t);
	if(t_dealiased instanceof IntType || t_dealiased instanceof RuneType || t_dealiased instanceof Float64Type) typemap.put(node,t);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Unary plus used in non-numeric context.");
    }
                   
    public void outAUMinusExp(AUMinusExp node) throws TypeException{
	Node e = node.getExp();
	Type t = typemap.get(e);
	Type t_dealiased = removeAlias(t);
	if(t_dealiased instanceof IntType || t_dealiased instanceof RuneType || t_dealiased instanceof Float64Type) typemap.put(node,t);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Negation used in non-numeric context.");
    }
                   
    public void outAUBangExp(AUBangExp node) throws TypeException{
	Node e = node.getExp();
	Type t = typemap.get(e);
	Type t_dealiased = removeAlias(t);
	if(t_dealiased instanceof BooleanType) typemap.put(node,t);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Logical negation used in a non-Boolean context.");
    }
                   
    public void outAUCaretExp(AUCaretExp node) throws TypeException{
	Node e = node.getExp();
	Type t = typemap.get(e);
	Type t_dealiased = removeAlias(t);
	if(t_dealiased instanceof IntType || t_dealiased instanceof RuneType) typemap.put(node,t);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Unary plus used in non-integer context.");
    }
                   
                   
                   
    /* 3.4 Binary Expressions */
    /* same idea for all of these: grab the types out of the typemap, use the appropriate helper function to verify that they are of the correct type (or aliases of the correct type, then store the result */
    public void outABinAndExp(ABinAndExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areBoolean(typel,typer)) typemap.put(node, typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Binary and used without two Booleans.");
    }
                   
    public void outABinOrExp(ABinOrExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areBoolean(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Binary or used without two Booleans.");
    }
                   
                   
                   
    public void outAEqExp(AEqExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
                       
	if(areComparable(typel,typer)) typemap.put(node,new BooleanType());
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to compare incomparable values.");
    }
                   
    public void outANeqExp(ANeqExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
                       
	if(areComparable(typel,typer)) typemap.put(node,new BooleanType());
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to compare incomparable values.");
    }
                   
                   
                   
    public void outALtExp(ALtExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areOrdered(typel,typer)) typemap.put(node,new BooleanType());
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to order incomparable values.");
    }
                   
    public void outALeqExp(ALeqExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
                       
	if(areOrdered(typel,typer)) typemap.put(node,new BooleanType());
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to order incomparable values.");
    }
                   
    public void outAGtExp(AGtExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
                       
	if(areOrdered(typel,typer)) typemap.put(node,new BooleanType());
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to order incomparable values.");
    }
                   
    public void outAGeqExp(AGeqExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
                       
	if(areOrdered(typel,typer)) typemap.put(node,new BooleanType());
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to order incomparable values.");
    }
                   
                   
                   
                   
                   
    public void outAPlusExp(APlusExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(typel instanceof StringType && typer instanceof StringType) typemap.put(node,typel);
	else if(areNumeric(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to add two incompatible types.");
    }
                   
    public void outAMinusExp(AMinusExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areNumeric(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to subtract two incompatible types.");
    }
                   
    public void outAModExp(AModExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areNumeric(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to mod two incompatible types.");
    }
                   
    public void outASlashExp(ASlashExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areNumeric(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to divide two incompatible types.");
    }
                   
    public void outAStarExp(AStarExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areNumeric(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to multiply two incompatible types.");
    }
                   
                   
                   
    public void outAAmpCaretExp(AAmpCaretExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areIntegral(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use bit clear on two incompatible types.");
    }
                   
    public void outAAmpExp(AAmpExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areIntegral(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use bitwise AND on two incompatible types.");
    }
                   
    public void outACaretExp(ACaretExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areIntegral(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use bitwise XOR on two incompatible types.");
    }
                   
    public void outALShiftExp(ALShiftExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areIntegral(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use left shift on two incompatible types.");
    }
                   
    public void outARShiftExp(ARShiftExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areIntegral(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use right shift on two incompatible types.");
    }
                   
    public void outAPipeExp(APipeExp node) throws TypeException{
	PExp l = node.getL();
	PExp r = node.getR();
	Type typel = typemap.get(l);
	Type typer = typemap.get(r);
	if(areIntegral(typel,typer)) typemap.put(node,typel);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Attempt to use bitwise OR on two incompatible types.");
    }
                   
                   
                   
    /* 3.5 FunctionType Calls */
    /* first, check that we can get the function's type out of the symboltable. then compare the types of all the arguments from the node with the declared parameter types from the symbol table */
    public void caseAFuncCallExp(AFuncCallExp node)
    {
        inAFuncCallExp(node);
        {
            // don't typecheck name
            List<PExp> copy = new ArrayList<PExp>(node.getParam());
            for(PExp e : copy)
            {
                e.apply(this);
            }
        }
        outAFuncCallExp(node);
    }

    public void outAFuncCallExp(AFuncCallExp node) throws TypeException{
	PExp name = node.getName();
	LinkedList<PExp> args = node.getParam();
	java.lang.String funcname = new java.lang.String();
	if(name instanceof AIdExp){
	    AIdExp expname = (AIdExp) name;
	    TId id = expname.getId();
	    funcname = id.getText();
	}
	if(funcname==null || lookUpTypeOrVar(funcname, golite.weeder.LineNumber.getLineNumber(node))==null) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Call to undefined function.");
	else{
        Type f = lookUpTypeOrVar(funcname, golite.weeder.LineNumber.getLineNumber(node));
            if (f instanceof AliasType || castableBasicNum(f) || f instanceof BooleanType){
                PExp exp = args.getFirst();
                Type exptype = typemap.get(exp);
                AliasType alias = (AliasType) f;
                Type dealiased = removeAlias(alias);
                exptype = removeAlias(exptype);
                if((castableBasicNum(exptype) && castableBasicNum(dealiased)) || (exptype instanceof BooleanType && dealiased instanceof BooleanType)) typemap.put(node,alias);
                else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Type cast to a disallowed type.");
                List<PExp> copy = new ArrayList<PExp>(node.getParam());
                if (copy.size()>1) {
                    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Too many arguments for type cast.");
                }
            }
	    else if (f instanceof FunctionType){
		FunctionType functype = (FunctionType) f;
		List<Type> paramtypes = functype.getParamTypes();
            if (args.size() != paramtypes.size()) {
                throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Function expects " + paramtypes.size() + " arguments.");
            }
		Iterator<Type> iterparams = paramtypes.iterator();
		Iterator<PExp> iterargs = args.iterator();
		Type paramtype, argtype;
		while(iterparams.hasNext() && iterargs.hasNext()){
		    paramtype = iterparams.next();
		    PExp arg = iterargs.next();
		    argtype = typemap.get(arg);
		    if(!areSameType(paramtype,argtype)) throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Incorrectly typed function call.");
		}
            typemap.put(name, functype);
		typemap.put(node,functype.getReturnType());
	    }
	    
	    else{
		throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Couldn't find alias/function name.");
	    }
	}
    }
                   
    /* 3.6 Indexing */
    /* verify that the index expression has type int or alias of int, and that the expression has type slice or array or corresponding alias */
    public void outAIndexingExp(AIndexingExp node) throws TypeException{
	PExp exp = node.getArray();
	PExp index = node.getIndex();
	Type exptype = typemap.get(exp);
	Type indextype = typemap.get(index);
	indextype = removeAlias(indextype);
	exptype = removeAlias(exptype);
	if(!(indextype instanceof IntType)){
	    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Non-integer index.");
	}
	if(exptype instanceof SliceType){
	    SliceType expslice = (SliceType) exptype;
	    typemap.put(node,expslice.getType());
	}
	else if(exptype instanceof ArrayType){
	    ArrayType exparray = (ArrayType) exptype;
	    typemap.put(node,exparray.getType());
	}
	else{
	    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Indexing expression on a non-array/slice type.");
	}
    }
                   
    /* confirm the node has type struct or alias of struct, then check that struct for the relevant fieldname */
    /* 3.7 Field Expressions */
    public void outASelectorExp(ASelectorExp node) throws TypeException{
	PExp exp = node.getExp();
	Type exptype = typemap.get(exp);
	exptype = removeAlias(exptype);
	TId id = node.getId();
	java.lang.String idname = id.getText();
	if(!(exptype instanceof StructType)){
	    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Selector on non-struct type.");
	}
	else{
	    StructType tempstruct = (StructType) exptype;
	    List<Map.Entry<List<String>,Type>> fields = tempstruct.getFields();
	    for(Map.Entry<List<String>,Type> entry : fields){
		List<String> fieldname = entry.getKey();
		Type fieldtype = entry.getValue();
		if (fieldname.contains(idname)) {
		    typemap.put(node,fieldtype);
		    return;
		}
	    }
	    throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Selector on nonexistent field.");
	}
    }
                   
    /* 3.8 Append */
    public void outAAppendExp(AAppendExp node) throws TypeException{
        PExp exp = node.getExp();
        Type exptype = typemap.get(exp);
        Type idtype = lookUpVarType(node.getId().getText(), golite.weeder.LineNumber.getLineNumber(node));
        Type dealiased_idtype = removeAlias(idtype);
        if(!(dealiased_idtype instanceof SliceType)){
            throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Append used on non-slice type.");
        }
        else{
            SliceType tempslice = (SliceType) dealiased_idtype;
            Type sliceof = tempslice.getType();
            if(areSameType(sliceof,exptype)) typemap.put(node,idtype);
            else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Type of slice from ID and of exp don't match in an append.");
            
        }

        typemap.put(node, idtype);
        
        if (node.getId() instanceof TId) {
            TId id = (TId) node.getId();
            
            if (slicemap.containsKey(id.getText())) {
                Integer oldSize = slicemap.remove(id.getText());
                oldSize = new Integer(oldSize.intValue() + 1);
                slicemap.put(id.getText(), oldSize);
            }
            else {
                slicemap.put(id.getText(), new Integer(1));
            }
        }
    }
                   
                   
    /* 3.9 Type Cast (can assume the cast is not an alias, this will be handled by function calls in 3.5)*/
    public void outATypeCastExp(ATypeCastExp node){
	PExp exp = node.getExp();
	Type exptype = removeAlias(typemap.get(exp));
	PType castedtype = node.getType();
	Type finaltype;
	if(castedtype instanceof AIntType){
	    finaltype = new IntType();
	}
	else if(castedtype instanceof ABoolType){
	    finaltype = new BooleanType();
	}
	else if(castedtype instanceof AFloatType){
	    finaltype = new Float64Type();
	}
	else if(castedtype instanceof ARuneType){
	    finaltype = new RuneType();
	}
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Type cast to a disallowed type.");
                     
	if((exptype instanceof IntType) ||(exptype instanceof Float64Type) ||(exptype instanceof BooleanType) ||(exptype instanceof RuneType)) typemap.put(node,finaltype);
	else throw new TypeException("[line " + golite.weeder.LineNumber.getLineNumber(node) + "] Type cast from a disallowed type.");
    }
}
