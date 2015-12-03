package golite.symbol;

import java.util.*;

public class FunctionType extends Type {

	private List<Type> paramTypes;
	private Type returnType;
	
	public FunctionType(List<Type> paramTypes, Type returnType) {
		this.paramTypes = paramTypes;
		this.returnType = returnType;
	}
	
	public List<Type> getParamTypes() {
		return paramTypes;
	}
	
	public Type getReturnType() {
		return returnType;
	}
	
	public String toString() {
		List<String> paramTypeNames = new ArrayList<String>();
		for (Type type: paramTypes) {
			paramTypeNames.add(type.toString());
		}
		//return String.format("func(%s) %s", String.join(", ", paramTypeNames), returnType.toString());
        
        // not able to use String.join without external libraries so will do it manually for now
        String returnStr = "func(";
        Iterator<String> itr = paramTypeNames.iterator();
        if (itr.hasNext()) {
            returnStr += itr.next();
        }
        while (itr.hasNext()) {
            returnStr += ", " + itr.next();
        }
        if (returnType==null) {
            returnStr += ")";
        }
        else {
            returnStr += ") " + returnType.toString();
        }
        return returnStr;
	}
	
}
