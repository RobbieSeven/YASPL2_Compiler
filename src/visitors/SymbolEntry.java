package visitors;

import java.util.ArrayList;

public class SymbolEntry {
	
	private String kind;
	private String type;
	private ArrayList<String> varTypes;
	private ArrayList<String> parTypes;
	
	public SymbolEntry(String kind, String type) {
		this.kind = kind;
		this.type = type;
		varTypes = new ArrayList<String>();
		parTypes = new ArrayList<String>();
	}
	
	public SymbolEntry(String kind, String type, ArrayList<String> varTypes, ArrayList<String> parTypes) {
		this.kind = kind;
		this.type = type;
		this.varTypes = varTypes;
		this.parTypes = parTypes;
	}
	
	public String getKind() {
		return kind;
	}
	
	public String getType() {
		return type;
	}

	public ArrayList<String> getVarTypes() {
		return varTypes;
	}
	
	public ArrayList<String> getParTypes() {
		return parTypes;
	}
	
}
