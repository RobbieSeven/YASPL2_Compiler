package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class VarDeclNode extends DeclNode {
	
	private String varType;
	private VarsNode vars;

	public VarDeclNode(String varType, VarsNode vars) {
		this.varType = varType;
		this.vars = vars;
	}

	@Override
	public String getKind() {
		return "Var_decl";
	}
	
	public String getVarType() {
		return varType;
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
