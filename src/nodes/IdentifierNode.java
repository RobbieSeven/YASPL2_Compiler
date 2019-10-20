package nodes;

import visitors.CodeVisitor;
import visitors.SemanticException;
import visitors.SemanticVisitor;
import visitors.SymbolEntry;
import visitors.SyntaxVisitor;

public class IdentifierNode extends ExprNode {

	private final String identifier;
	private SymbolEntry entry;
	
	public IdentifierNode(String identifier) {
		this.identifier = identifier;
	}
	
	@Override
	public String getKind() {
		return "Identifier";
	}
	
	public String getIdentifier() {
		return identifier;
	}
	
	public SymbolEntry getEntry() {
		return entry;
	}
	
	public void setEntry(SymbolEntry entry) {
		this.entry = entry;
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
