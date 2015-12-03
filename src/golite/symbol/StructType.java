package golite.symbol;

import java.util.*;

public class StructType extends Type {

	private List<Map.Entry<List<String>,Type>> fields;
    private String name;
	
	public StructType(List<Map.Entry<List<String>,Type>> fields, String name) {
		this.fields = fields;
        this.name = name;
	}
	
	public List<Map.Entry<List<String>,Type>> getFields() {
		return fields;
	}
    
    public String getStructName() {
        return name;
    }
	
	
	public String toString() {
		
		return String.format("struct{%s}", name + " " + Arrays.toString(fields.toArray()) );
	}
}
