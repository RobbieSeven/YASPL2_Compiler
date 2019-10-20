package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class IfThenElseNode extends SimpleStatNode {

	private BoolExprNode boolExpr;
	private StatNode stat1;
	private StatNode stat2;
	
	public IfThenElseNode(BoolExprNode boolExpr, StatNode stat1, StatNode stat2) {
		this.boolExpr = boolExpr;
		this.stat1 = stat1;
		this.stat2 = stat2;
	}
	
	@Override
	public String getKind() {
		return "If_then_else";
	}
	
	public BoolExprNode getBoolExpr() {
		return boolExpr;
	}
	
	public StatNode getStat1() {
		return stat1;
	}
	
	public StatNode getStat2() {
		return stat2;
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
