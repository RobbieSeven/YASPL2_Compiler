package visitors;

import java.util.HashMap;
import java.util.Stack;

public class SymbolTable {
	
	private Stack<HashMap<String, SymbolEntry>> symbolTable;
	private HashMap<String, SymbolEntry> currentScope;
	
	public SymbolTable() {
		symbolTable = new Stack<HashMap<String, SymbolEntry>>();
		currentScope = null;
	}
	
	public HashMap<String, SymbolEntry> getCurrentScope() {
		return currentScope;
	}
	
	public HashMap<String, SymbolEntry> enterScope() {
		symbolTable.push(new HashMap<String, SymbolEntry>());
		currentScope = symbolTable.peek();
		return currentScope;
	}
	
	public SymbolEntry lookUp(String id){
		Stack<HashMap<String, SymbolEntry>> checkedTables = new Stack<HashMap<String, SymbolEntry>>();
		while (!symbolTable.empty()) {
			if (currentScope.containsKey(id)) {
				SymbolEntry entry = currentScope.get(id);
				while (!checkedTables.empty())
					symbolTable.push(checkedTables.pop());
				currentScope = symbolTable.peek();
				return entry;
			} else {
				checkedTables.push(symbolTable.pop());
				if (!symbolTable.empty())
					currentScope = symbolTable.peek();
			}
		}
		return null;
	}
	
	public void addId(String id, SymbolEntry entry) {
		currentScope.put(id, entry);
	}
	
	public boolean probe(String id) {
		return currentScope.containsKey(id);
	}
	
	public void exitScope() {
		symbolTable.pop();
		if (!symbolTable.empty())
			currentScope = symbolTable.peek();
		else
			currentScope = null;
	}

}
