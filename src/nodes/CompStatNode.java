package nodes;

import java.util.ArrayList;
import java.util.Collections;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class CompStatNode extends StatNode {
	
	private ArrayList<StatNode> statements;
	
	public CompStatNode(ArrayList<StatNode> statements) {
		Collections.reverse(statements);
		this.statements = statements;
	}

	@Override
	public String getKind() {
		return "Comp_stat";
	}
	
	public ArrayList<StatNode> getStatements() {
		return statements;
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
