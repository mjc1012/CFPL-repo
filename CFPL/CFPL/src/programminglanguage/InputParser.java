package programminglanguage;

import java.util.ArrayList;
import java.util.Scanner;

public class InputParser { // added change
	private ArrayList<Token> tokens;
	private ArrayList<Token> inputTokens;
	private int tokenIndex;
	private int inputTokenIndex;
	private Token currentToken;
	private Token currentInputToken;
	private Scanner input = new Scanner(System.in);
	
	public InputParser(ArrayList<Token> tokens) {
		this.tokens = tokens;
		this.tokenIndex = -1;
		this.advance();
	}
	
	public Scanner getScanner() {
		return this.input;
	}
	
	public void getInput() { 
		String text = input.nextLine();
		String[] values = text.split(",");
		String newText = "";
		for(String str: values) {
			str = str.strip();
			if(str.length() > 1) {
				if(Character.isLetter(str.charAt(0))) {
					str = "\"" + str + "\""; 
				}
			}
			if(str.length() == 1) {
				if(Character.isLetter(str.charAt(0))) {
					str = "\'" + str + "\'"; 
				}
			}
			if(newText.equals("")) {
				newText = str;
			}
			else {
	          newText = newText + "," + str;
			}
		}
		Lexer lexer = new Lexer("<CFPL>", newText);
		this.inputTokens = lexer.make_tokens();
		this.inputTokenIndex = -1;
		this.advanceInput();
	}
	
	public void advance() {
		this.tokenIndex += 1;
		if(this.tokenIndex < tokens.size()) {
			this.currentToken = this.tokens.get(this.tokenIndex);
		}
	}
	
	public void advanceInput() {
		this.inputTokenIndex += 1;
		if(this.inputTokenIndex < inputTokens.size()) {
			this.currentInputToken = this.inputTokens.get(this.inputTokenIndex);
		}
	}
	
	public ParseResult inputFactor() { 
		ParseResult result = new ParseResult();
		if(this.currentInputToken.getType() == TokenType.COMMA) {
			this.advanceInput();
		}
		Token token = this.currentInputToken;
		
		if(token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS) { 
			this.advanceInput();
			UnaryOpNode factor1 = null;
			NumberNode factor2 = null;
			
			result = this.inputFactor();
			if(result.getError() != null) {
				return result;
			}
			
			if(result.getUnaryOpNode() != null){
				factor1 = result.getUnaryOpNode();
				result.success(new UnaryOpNode(token, factor1));
			}
			else if(result.getNumberNode() != null){
				factor2 = result.getNumberNode();
				result.success(new UnaryOpNode(token, factor2));
			}
			else {
				InvalidSyntaxError error = new InvalidSyntaxError(token.getPositionStart(), token.getPositionEnd(), "Error in INPUT");
				result.failure(error);
				return result;
			}
			
			return result;
		}
		else if(token.getType() == TokenType.INT || token.getType() == TokenType.FLOAT) {
			result.success(new NumberNode(token));
			return result;
		}
		else if(token.getType() == TokenType.CHAR) {
			result.success(new CharNode(token));
			return result;
		}
		else if(token.getType() == TokenType.BOOL) {
			result.success(new StringNode(token));
			return result;
		}
		else {
			InvalidSyntaxError error = new InvalidSyntaxError(token.getPositionStart(), token.getPositionEnd(), "Error in INPUT");
			result.failure(error);
			return result;
		}
	}
	
	public ParseResult assignValue() {
		ParseResult result = new ParseResult();
		ParseResult result2 = new ParseResult();
		this.getInput();
		for(int i = 0; i < this.tokens.size(); i++) {
			
			result = this.inputFactor();
			
			if(result.getError() != null) {
				return result;				
			}
			
			if(result.getUnaryOpNode() != null) {
				result2.success(new VarAssignNode(this.currentToken, result.getUnaryOpNode()));
			}
			else if(result.getNumberNode() != null){
				result2.success(new VarAssignNode(this.currentToken, result.getNumberNode()));
			}
			else if(result.getCharNode() != null){
				result2.success(new VarAssignNode(this.currentToken, result.getCharNode()));
			}
			else if(result.getStringNode() != null){
				result2.success(new VarAssignNode(this.currentToken, result.getStringNode()));
			}
			else {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Error in INPUT");
				result2.failure(error);
				return result2;
			}
			this.advance();
			this.advanceInput();
		}
		return result2;
	}
}
