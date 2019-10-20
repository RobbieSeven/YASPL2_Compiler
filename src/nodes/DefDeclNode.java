package nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SymbolEntry;
import visitors.SyntaxVisitor;

public class DefDeclNode extends DeclNode {

	private HashMap<String, SymbolEntry> scope;
	private IdentifierNode name;
	private ArrayList<VarDeclNode> varDecls;
	private ArrayList<VarDeclNode> parDecls;
	private BodyNode body;
	
	public DefDeclNode(IdentifierNode name, ArrayList<VarDeclNode> varDecls, 
					   ArrayList<VarDeclNode> parDecls, BodyNode body) {
		this.name = name;
		Collections.reverse(varDecls);
		this.varDecls = varDecls;
		Collections.reverse(parDecls);
		this.parDecls = parDecls;
		this.body = body;
	}

	@Override
	public String getKind() {
		return "Def_decl";
	}
	
	public HashMap<String, SymbolEntry> getScope() {
		return scope;
	}
	
	public void setScope(HashMap<String, SymbolEntry> scope) {
		this.scope = scope;
	}
	
	public IdentifierNode getName() {
		return name;
	}
	
	public ArrayList<VarDeclNode> getVarDecls() {
		return varDecls;
	}
	
	public ArrayList<VarDeclNode> getParDecls() {
		return parDecls;
	}
	
	public BodyNode getBody() {
		return body;
	}
	
	@Override
	public String accept(SyntaxVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public void accept(SemanticVisitor visitor) throws SemanticException {
		visitor.visit(this);
	}
	
	public String accept(CodeVisitor visitor) {
		return visitor.visit(this);
	}

}
