package nodes;

import java.util.ArrayList;
import java.util.Collections;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class BodyNode extends Node {
	
	private ArrayList<VarDeclNode> varDecls;
	private ArrayList<StatNode> stats;
	
	public BodyNode(ArrayList<VarDeclNode> varDecls, ArrayList<StatNode> stats) {
		Collections.reverse(varDecls);
		this.varDecls = varDecls;
		Collections.reverse(stats);
		this.stats = stats;
	}

	@Override
	public String getKind() {
		return "Body";
	}
	
	public ArrayList<VarDeclNode> getVarDecls() {
		return varDecls;
	}
	
	public ArrayList<StatNode> getStats() {
		return stats;
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
