package nodes;

import java.util.ArrayList;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class VarsNode extends Node {

	private IdentifierNode name;
	private VarsNode vars;
	
	public VarsNode(IdentifierNode name, VarsNode vars) {
		this.name = name;
		this.vars = vars;
	}
	
	@Override
	public String getKind() {
		return "Vars";
	}

	public IdentifierNode getName() {
		return name;
	}
	
	public ArrayList<IdentifierNode> getNames() {
		ArrayList<IdentifierNode> names = new ArrayList<>();
		names.add(name);
		if (vars != null)
			names.addAll(vars.getNames());
		return names;
	}
	
	public VarsNode getVars() {
		return vars;
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
