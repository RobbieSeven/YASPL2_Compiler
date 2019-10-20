package nodes;

import java.util.ArrayList;
import java.util.Collections;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class ReadNode extends SimpleStatNode {

	private ArrayList<IdentifierNode> vars;
	private ArrayList<String> types;
	
	public ReadNode(ArrayList<IdentifierNode> vars, ArrayList<String> types) {
		this.vars = vars;
		Collections.reverse(types);
		this.types = types;
	}
	
	public String getKind() {
		return "Read";
	}
	
	public ArrayList<IdentifierNode> getVars() {
		return vars;
	}
	
	public ArrayList<String> getTypes() {
		return types;
	}
	
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
