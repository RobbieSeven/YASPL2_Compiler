package nodes;

public class Leaf<T> {
	
	private String type;
	private T attribute;
	
	public Leaf(String type, T attribute) {
		this.type = type;
		this.attribute = attribute;
	}
	
	public String getType() {
		return type;
	}
	
	public T getAttribute() {
		return attribute;
	}

}
