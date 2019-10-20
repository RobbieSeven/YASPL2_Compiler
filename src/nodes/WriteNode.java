package nodes;

import java.util.ArrayList;
import java.util.Collections;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class WriteNode extends SimpleStatNode {

	private ArrayList<OutValueNode> outValues;
	
	public WriteNode(ArrayList<OutValueNode> outValues) {
		Collections.reverse(outValues);
		this.outValues = outValues;
	}
	
	@Override
	public String getKind() {
		return "Write";
	}
	
	public ArrayList<OutValueNode> getOutValues() {
		return outValues;
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
