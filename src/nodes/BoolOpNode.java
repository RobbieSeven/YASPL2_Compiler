package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class BoolOpNode extends BoolExprNode {
	
	private String boolOp;
	private BoolExprNode boolExpr1;
	private BoolExprNode boolExpr2;
	
	public BoolOpNode(String boolOp, BoolExprNode boolExpr1, BoolExprNode boolExpr2) {
		this.boolOp = boolOp;
		this.boolExpr1 = boolExpr1;
		this.boolExpr2 = boolExpr2;
	}

	@Override
	public String getKind() {
		if (boolOp.equals("&&"))
			return "And";
		if (boolOp.equals("||"))
			return "Or";
		return "Bool_op";
	}
	
	public String getBoolOp() {
		return boolOp;
	}
	
	public BoolExprNode getBoolExpr1() {
		return boolExpr1;
	}
	
	public BoolExprNode getBoolExpr2() {
		return boolExpr2;
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
