package nodes;

import visitors.*;

public abstract class Node {
	
	public abstract String getKind();
	public abstract String accept(SyntaxVisitor visitor);
	public abstract void accept(SemanticVisitor visitor) throws SemanticException;
	public abstract String accept(CodeVisitor visitor);
	
}
