package nodes;

import java.text.StringCharacterIterator;

import visitors.CodeVisitor;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

public class StringConstNode extends OutValueNode {

	private String stringConst;
	
	public StringConstNode(String stringConst) {
		this.stringConst = checkString(stringConst);
	}

	@Override
	public String getKind() {
		return "String_const";
	}
	
	public String getStringConst() {
		return stringConst;
	}

	@Override
	public String accept(SyntaxVisitor visitor) {
		return visitor.visit(this);
	}

	@Override
	public void accept(SemanticVisitor visitor) {
		visitor.visit(this);
	}
	
	public String accept(CodeVisitor visitor) {
		return visitor.visit(this);
	}
	
	private String checkString(String string) {
		StringBuilder result = new StringBuilder();
	    StringCharacterIterator iterator = new StringCharacterIterator(string);
	    char character = iterator.current();
	    while (character != StringCharacterIterator.DONE){
	      if (character == '\"' )
	        result.append("\\\"");
	      else if (character == '\\')
	        result.append("\\\\");
	      else if (character == '/')
	        result.append("\\/");
	      else if (character == '\b')
	        result.append("\\b");
	      else if (character == '\f')
	        result.append("\\f");
	      else if (character == '\n')
	        result.append("\\n");
	      else if (character == '\r')
	        result.append("\\r");
	      else if (character == '\t')
	        result.append("\\t");
	      else
	        result.append(character);
	      character = iterator.next();
	    }
	    return result.toString();    
	}

}
