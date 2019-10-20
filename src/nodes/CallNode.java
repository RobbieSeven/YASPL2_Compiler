package nodes;

import java.util.ArrayList;
import java.util.Collections;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class CallNode extends SimpleStatNode {

	private IdentifierNode name;
	private ArrayList<ExprNode> params;
	private ArrayList<IdentifierNode> vars;
	
	public CallNode(IdentifierNode name, ArrayList<ExprNode> params, ArrayList<IdentifierNode> vars) {
		this.name = name;
		Collections.reverse(params);
		this.params = params;
		Collections.reverse(vars);
		this.vars = vars;
	}
	
	@Override
	public String getKind() {
		return "Call";
	}
	
	public IdentifierNode getName() {
		return name;
	}
	
	public ArrayList<ExprNode> getParams() {
		return params;
	}
	
	public ArrayList<IdentifierNode> getVars() {
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
