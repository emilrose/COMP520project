package golite.symbol;
import java.util.*;

public class AliasType extends Type {
	
	//true if aliasName is a type
	//false is aliasName is a variable
	private boolean typeOrVar;
	//contains alias id - type that is pointed. 
	private String aliasName;
	//contains underlying type reference
	private Type reference;
	//ex: type num1 int ; type num2 num1; var x num2     
	// num2's aliasName will be num1. num2's reference will be int.
	// x's typeOrVar is false. num2 and num1's typeOrVar is true.
	
	public AliasType(String name, Type ref, boolean typeOrVar) {
		this.typeOrVar = typeOrVar;
		aliasName = name;
		reference = ref;
	}
	
	public String getAliasName() {
		return aliasName;
    }
	
	public Type getType() {
		return reference;
	}
	
	public boolean isType() {
		return typeOrVar;
	}
	
	public void setToVar() {
		typeOrVar = false;
	}
	
	public String toString() {
		String varOrType = (typeOrVar)? "Type: ": "Var: ";
		//I think we want to return the name of the aliased type, not the underlying type.
		if (reference instanceof AliasType) {
			return varOrType + ((AliasType) reference).getAliasName() + ":" + reference.toString();
		} else {
			return varOrType + reference.toString();
		}
	}
}
