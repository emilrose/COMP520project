package golite.symbol;


public class SliceType extends Type {

	private Type type;
	
	public SliceType(Type type) {
		this.type = type;
	}
	
	public Type getType() {
		return type;
	}
	
	public String toString() {
		return "[]" + type.toString();
	}
}
