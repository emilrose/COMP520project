package golite.symbol;


public class ArrayType extends Type {

	private Type type;
	private String size;
	
	public ArrayType(Type type, String size) {
		this.type = type;
		this.size = size;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getSize() {
		return size;
	}
	
	public String toString() {
		return String.format("[%s]%s", size, type.toString());
	}
}
