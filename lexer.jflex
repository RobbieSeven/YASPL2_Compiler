package compiler;
import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import java_cup.runtime.Symbol;

%%

%class Lexer
%implements sym
%public
%unicode
%line
%column
%cup
%char

%{
	
    private StringBuffer string;
    private ComplexSymbolFactory symbolFactory;
    private int csline, cscolumn;
    
    public Lexer(ComplexSymbolFactory factory, java.io.InputStream stream) {
		this(stream);
        symbolFactory = factory;
        string = new StringBuffer();
    }
    
	public Lexer(ComplexSymbolFactory factory, java.io.Reader reader) {
		this(reader);
        symbolFactory = factory;
        string = new StringBuffer();
    }

    public Symbol symbol(String name, int code) {
		return symbolFactory.newSymbol(name, code,
				new Location(yyline + 1, yycolumn + 1, yychar), // -yylength()
				new Location(yyline + 1, yycolumn + yylength(), yychar + yylength()));
    }
    
    public Symbol symbol(String name, int code, String lexem) {
		return symbolFactory.newSymbol(name, code, 
				new Location(yyline + 1, yycolumn + 1, yychar), 
				new Location(yyline + 1,yycolumn + yylength(), yychar + yylength()), lexem);
    }
    
    protected void emit_warning(String message) {
    	System.out.println("scanner warning: " + message + " at : 2 " + 
    			(yyline + 1) + " " + (yycolumn + 1) + " " + yychar);
    }
    
    protected void emit_error(String message) {
    	System.out.println("scanner error: " + message + " at : 2" + 
    			(yyline + 1) + " " + (yycolumn + 1) + " " + yychar);
    }
    
%}

/* to ignore */
LineTerminator = \r | \n | \r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Comment = {TraditionalComment} | {LineComment}
TraditionalComment = "/*" [^*] ~"*/" | "/*" "*"+ "/"
LineComment = "//" [^\r\n]* {LineTerminator}?

/* identifiers */
Name = ([:jletter:] | "_" ) ([:jletterdigit:] | [:jletter:] | "_" )*

/* costants */
Int_Const = 0 | [1-9][0-9]*
Double_Const = (0 | [1-9][0-9]*)\.[0-9]+
String_Const = [^\n\r\"\\]+

/* states */
%state STRING

/* end of file */
%eofval{
    return symbolFactory.newSymbol("EOF",sym.EOF);
%eofval}

%%  

<YYINITIAL> {

  /* keywords */
	"HEAD" 		{ return symbolFactory.newSymbol("HEAD", sym.HEAD); }
	"START"		{ return symbolFactory.newSymbol("START", sym.START); }
    "def" 		{ return symbolFactory.newSymbol("DEF", sym.DEF); }
	"if" 		{ return symbolFactory.newSymbol("IF", sym.IF); }
    "then" 		{ return symbolFactory.newSymbol("THEN", sym.THEN); }
    "else" 		{ return symbolFactory.newSymbol("ELSE", sym.ELSE); }
    "while"		{ return symbolFactory.newSymbol("WHILE", sym.WHILE); }
    "do" 		{ return symbolFactory.newSymbol("DO", sym.DO); }
    "not"       { return symbolFactory.newSymbol("NOT", sym.NOT); }

    /* types */
	"int" 	    { return symbolFactory.newSymbol("INT", sym.INT); }
	"double" 	{ return symbolFactory.newSymbol("DOUBLE", sym.DOUBLE); }
	"bool" 		{ return symbolFactory.newSymbol("BOOL", sym.BOOL); }

	/* values */
	"true"      { return symbolFactory.newSymbol("TRUE", sym.TRUE); }
    "false"     { return symbolFactory.newSymbol("FALSE", sym.FALSE); }

    /* identifiers */
	{Name} 		{ return symbolFactory.newSymbol("NAME", sym.NAME, yytext()); }

	/* costants */
	{Int_Const} 	{ return symbolFactory.newSymbol("INT_CONST", sym.INT_CONST, 
								Integer.parseInt(yytext())); }
	{Double_Const}  { return symbolFactory.newSymbol("DOUBLE_CONST", sym.DOUBLE_CONST, 
								Double.parseDouble(yytext())); }
	\" 			    { string.setLength(0); yybegin(STRING); }

	/* operators */
	"=" 		{ return symbolFactory.newSymbol("ASSIGN", sym.ASSIGN); }
	"+" 		{ return symbolFactory.newSymbol("PLUS", sym.PLUS); }
	"-" 		{ return symbolFactory.newSymbol("MINUS", sym.MINUS); }
	"*" 		{ return symbolFactory.newSymbol("TIMES", sym.TIMES); }
	"/" 		{ return symbolFactory.newSymbol("DIV", sym.DIV); }
	"==" 		{ return symbolFactory.newSymbol("EQ", sym.EQ); }
	"<" 		{ return symbolFactory.newSymbol("LT", sym.LT); }
	"<=" 		{ return symbolFactory.newSymbol("LE", sym.LE); }
	">" 		{ return symbolFactory.newSymbol("GT", sym.GT); }
	">=" 		{ return symbolFactory.newSymbol("GE", sym.GE); }
    "&&"        { return symbolFactory.newSymbol("AND", sym.AND); }
    "||"        { return symbolFactory.newSymbol("OR", sym.OR); }
    "<-"        { return symbolFactory.newSymbol("READ", sym.READ); }
    "->"        { return symbolFactory.newSymbol("WRITE", sym.WRITE); }
    
    /* separators */
    "("         { return symbolFactory.newSymbol("LPAR", sym.LPAR); }
    ")"         { return symbolFactory.newSymbol("RPAR", sym.RPAR); }
    "{"         { return symbolFactory.newSymbol("LGPAR", sym.LGPAR); }
    "}"         { return symbolFactory.newSymbol("RGPAR", sym.RGPAR); }
    ","         { return symbolFactory.newSymbol("COMMA", sym.COMMA); }
    ":"         { return symbolFactory.newSymbol("COLON", sym.COLON); }
    ";"         { return symbolFactory.newSymbol("SEMI", sym.SEMI); }

    /* to ignore */
    {Comment} 		{ }
    {WhiteSpace} 	{ }

}

<STRING> {

	\" 			        { yybegin(YYINITIAL);
                          return symbolFactory.newSymbol("STRING_CONST", sym.STRING_CONST, 
                          		string.toString()); }
	{String_Const} 		{ string.append(yytext()); }

	\\t 			    { string.append('\t'); }
	\\n 			    { string.append('\n'); }
	\\r 			    { string.append('\r'); }
	\\\" 			    { string.append('\"'); }
	\\ 			        { string.append('\\'); }

}

/* error */
.|\n		{ emit_warning("Unrecognized character '" + yytext() + "' -- ignored"); }
