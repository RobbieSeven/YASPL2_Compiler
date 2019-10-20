package compiler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import java_cup.runtime.ComplexSymbolFactory;
import nodes.ProgramNode;
import visitors.CodeVisitor;
import visitors.SemanticVisitor;
import visitors.SyntaxVisitor;

class Driver {

	public static void main(String[] args) {
		File inputFile = null;
		FileInputStream inputStream = null;
		String outputFileName = null;
		try {
			if (args[0] != null)
				if (args[0].endsWith("yaspl"))
					inputFile = new File(args[0]);
				else
					throw new IOException();
			else
				inputFile = new File("input1.yaspl");
			int index = inputFile.getName().lastIndexOf('.');
			outputFileName = inputFile.getName().substring(0, index);
			inputStream = new FileInputStream(inputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ComplexSymbolFactory factory = new ComplexSymbolFactory();
		Lexer lexer = new Lexer(factory, inputStream);
		Parser parser = new Parser();
		parser.setScanner(lexer);
		parser.symbolFactory = factory;
		try {
			ProgramNode programNode = (ProgramNode) parser.parse().value;
			String parseTreeString = programNode.accept(new SyntaxVisitor());
			PrintWriter writer = new PrintWriter(outputFileName + "ParseTree.xml", "UTF-8");
			writer.println(parseTreeString);
			writer.close();
			programNode.accept(new SemanticVisitor());
			String codeString = programNode.accept(new CodeVisitor());
			writer = new PrintWriter(outputFileName + ".c", "UTF-8");
			writer.println(codeString);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
