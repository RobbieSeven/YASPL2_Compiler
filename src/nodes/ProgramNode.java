package nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SymbolEntry;
import visitors.SyntaxVisitor;

public class ProgramNode extends Node {
	
	private HashMap<String, SymbolEntry> scope;
	private ArrayList<DeclNode> decls;
	private ArrayList<StatNode> stats;

	public ProgramNode(ArrayList<DeclNode> decls, ArrayList<StatNode> stats) {
		Collections.reverse(decls);
		this.decls = decls;
		Collections.reverse(stats);
		this.stats = stats;
	}

	public String getKind() {
		return "Program";
	}
	
	public HashMap<String, SymbolEntry> getScope() {
		return scope;
	}
	
	public void setScope(HashMap<String,SymbolEntry> scope) {
		this.scope = scope;
	}
	
	public ArrayList<DeclNode> getDecls() {
		return decls;
	}
	
	public ArrayList<StatNode> getStats() {
		return stats;
	}
	
	public String accept(SyntaxVisitor visitor) {
		return visitor.visit(this);
	}

	public void accept(SemanticVisitor visitor) throws SemanticException {
		visitor.visit(this);
	}
	
	public String accept(CodeVisitor visitor) {
		return visitor.visit(this);
	}

}
