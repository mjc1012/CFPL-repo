package programminglanguage;

import java.util.ArrayList;

public class Lexer {
	private String text;
	private Position position;
	private char currentChar;
	private String fileName;
	private IllegalCharError error;
	
	public Lexer(String fileName, String text){
		this.text = text;
		this.fileName = fileName;
		this.position = new Position(-1, 0, -1, this.fileName, text); // added change
		this.currentChar = '\0';
		this.advance();
	}
	
	public void advance() {
		this.position.advance(this.currentChar);
		this.currentChar = (this.position.getIndex() < this.text.length())? this.text.charAt(this.position.getIndex()) : '\0';
	}
	
	public Token makeNumber() {
		String numStr = "";
		int dotCount = 0;
		Position positionStart = this.position.copy();
		
		while(this.currentChar != '\0' && (Character.isDigit(this.currentChar) || this.currentChar == '.')) {
			if(this.currentChar == '.') {
				if(dotCount == 1) {
					break;
				}
				dotCount += 1;
				numStr += '.';
			}
			else {
				numStr += this.currentChar;
			}
			this.advance();
		}
		
		if(Character.isLetter(currentChar) || this.currentChar == '_') {
			this.error = new IllegalCharError(positionStart, this.position, "Variable must start with letter or underscore");
			return null;
		}
		
		Token newToken = null;
		if(dotCount == 0) {
			newToken = new Token(Integer.parseInt(numStr), TokenType.INT, positionStart, this.position);
		}
		else {
			newToken = new Token(Float.parseFloat(numStr), TokenType.FLOAT, positionStart, this.position);
		}
		
		return newToken;
	}
	
	
	public Token makeIdentifierOrKeyword() {
		String str = "";
		Position positionStart = this.position.copy();
		
		while(this.currentChar != '\0' && (Character.isDigit(this.currentChar) || Character.isLetter(this.currentChar) || this.currentChar == '_')) {
			if(this.currentChar != '\0' && (Character.isDigit(this.currentChar) || Character.isLetter(this.currentChar) || this.currentChar == '_')) {
				str += this.currentChar;
				this.advance();
			}
		}
		Token newToken = null;
		if(Keywords.findByName(str) ) {
			newToken = new Token(str, TokenType.KEYWORD, positionStart, this.position);
		}
		else {
			newToken = new Token(str, TokenType.IDENTIFIER, positionStart, this.position);
		}
		
		return newToken;
	}
	
	public Token makeBoolOrString() {
		String str = "";
		Position positionStart = this.position.copy();
		this.advance();
		if(this.currentChar == '\"') {
			this.error = new IllegalCharError(positionStart, this.position, "Missing string or bool value");
			return null;
		}
		while(this.currentChar != '\0' && (this.currentChar != '"')) {
			if(this.currentChar == '#'){
				this.currentChar = '\n';
			}
			str += this.currentChar;
			if(this.currentChar == '\n') {
				this.currentChar = '#';
			}
			this.advance();
		}
		if(this.currentChar != '"') {
			this.error = new IllegalCharError(positionStart, this.position, "Missing double quote");
			return null;
		}
		
		Token newToken = null;
		
		if((str.equals("TRUE")) || (str.equals("FALSE"))) {
			newToken = new Token(str, TokenType.BOOL, positionStart, this.position);
		}
		else {
			newToken = new Token(str, TokenType.STRING, positionStart, this.position);
		}

		return newToken;
	}
	
	public Token makeChar() {
		Position positionStart = this.position.copy();
		this.advance();
		if(this.currentChar == '#'){
			this.currentChar = '\n';
		}
		char c = this.currentChar;
		if(this.currentChar == '\n') {
			this.currentChar = '#';
		}
		if(this.currentChar == '\'') {
			this.error = new IllegalCharError(positionStart, this.position, "Missing char value");
			return null;
		}
		this.advance();
		if(this.currentChar != '\'') {
			this.error = new IllegalCharError(positionStart, this.position, "Missing single quote");
			return null;
		}
		Token newToken = new Token(c, TokenType.CHAR, positionStart, this.position);
		return newToken;
	}
	
	public ArrayList<Token> make_tokens(){
		ArrayList<Token> tokens = new ArrayList<Token>();
		Token token = null;
		while(this.currentChar != '\0') {
			
			if(this.currentChar == '\n' || this.currentChar == ';') {
				tokens.add(new Token(TokenType.NEWLINE, this.position));
				this.advance();
			}
			else if(Character.isWhitespace(this.currentChar)) {
				this.advance();
			}
			else if(Character.isDigit(this.currentChar)) {
				token = this.makeNumber();
				if(this.error != null) {
					break;
				}
				
				tokens.add(token);
			}
			else if(Character.isLetter(this.currentChar)) {
				token = this.makeIdentifierOrKeyword();
				if(this.error != null) {
					break;
				}
				tokens.add(token);
			}
			else if(this.currentChar == '+') {
				tokens.add(new Token(TokenType.PLUS, this.position));
				this.advance();
			}
			else if(this.currentChar == '-') {
				tokens.add(new Token(TokenType.MINUS, this.position));
				this.advance();
			}
			else if(this.currentChar == '*') {
				tokens.add(new Token(TokenType.MULTIPLICATION, this.position));
				this.advance();
			}
			else if(this.currentChar == '/') {
				tokens.add(new Token(TokenType.DIVISION, this.position));
				this.advance();
			}
			else if(this.currentChar == '%') {
				tokens.add(new Token(TokenType.MODULO, this.position));
				this.advance();
			}
			else if(this.currentChar == '&') {
				tokens.add(new Token(TokenType.AMPERSAND, this.position));
				this.advance();
			}
			else if(this.currentChar == '=') {
				tokens.add(new Token(TokenType.EQUALS, this.position));
				this.advance();
			}
			else if(this.currentChar == ',') {
				tokens.add(new Token(TokenType.COMMA, this.position));
				this.advance();
			}
			else if(this.currentChar == ':') {
				tokens.add(new Token(TokenType.COLON, this.position));
				this.advance();
			}
			else if(this.currentChar == '\'') {
				token = this.makeChar();
				if(this.error != null) {
					break;
				}
				if(token == null) {
					tokens.clear();
					break;
				}
				tokens.add(token);
				this.advance();
			}
			else if(this.currentChar == '"') {
				token = this.makeBoolOrString();
				if(this.error != null) {
					break;
				}
				if(token == null) {
					tokens.clear();
					break;
				}
				tokens.add(token);
				this.advance();
			}
			else if(this.currentChar == '(') {
				tokens.add(new Token(TokenType.LEFT_PARENTHESIS, this.position));
				this.advance();
			}
			else if(this.currentChar == ')') {
				tokens.add(new Token(TokenType.RIGHT_PARTENTHESIS, this.position));
				this.advance();
			}
			else {
				Position errorPosition = this.position.copy();
				char errorChar = this.currentChar;
				this.error = new IllegalCharError(errorPosition, this.position, "'" + errorChar + "'");
				break;
			}
		}
		
		tokens.add(new Token(TokenType.END_OF_FILE, this.position));
		return tokens;
	}
	
	public IllegalCharError getError() {
		return this.error;
	}
}
