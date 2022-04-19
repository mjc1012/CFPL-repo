package programminglanguage;

import java.util.ArrayList;

public class Parser {
	private ArrayList<Token> tokens;
	private int tokenIndex;
	private Token currentToken;
	
	public Parser(ArrayList<Token> tokens) {
		this.tokens = tokens;
		this.tokenIndex = -1;
		this.advance();
	}
	
	public void advance() {
		this.tokenIndex += 1;
		if(this.tokenIndex < tokens.size()) {
			this.currentToken = this.tokens.get(this.tokenIndex);
		}
	}

	public Token seeNextToken() {
		Token nextToken = this.tokens.get(this.tokenIndex + 1);
		return nextToken;
	}
	
	public Token seePreviousToken() {
		Token previousToken = this.tokens.get(this.tokenIndex - 1);
		return previousToken;
	}
	
	public ParseResult parse(Context context1, Context context2) {
		ParseResult result = this.completeProgram(context1, context2);
		return result;
	}
	
	public ParseResult completeProgram(Context context1, Context context2) {
		ParseResult result = new ParseResult();
		ArrayList<ParseResult> statements = new ArrayList<ParseResult>();
		Position positionStart = this.currentToken.getPositionStart().copy();
		
		while(this.currentToken.getType() == TokenType.NEWLINE) {
			this.advance();
		}
		
		result = varStatements(context1,context2);
		if(result.getError() != null) {
			result.failure(result.getError());
			return result;
		}
		
		statements.addAll(result.getListNode().getList());
		
		if(this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "START")) {
			context2.getSymbolTable().setStringValue(this.currentToken.toString(), null);
			this.advance();
		}
		else if(this.currentToken.getType() != TokenType.NEWLINE && this.currentToken.getType() != TokenType.END_OF_FILE) {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
			result.failure(error);
			return result;
		}
		else {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing START keyword");
			result.failure(error);
			return result;
		}
		
		if(this.currentToken.getType() != TokenType.NEWLINE) {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing new line");
			result.failure(error);
			return result;
		}
		
		result = identifierOutputStatements(context1,context2);
		if(result.getError() != null) {
			result.failure(result.getError());
			return result;
		}
		
		statements.addAll(result.getListNode().getList());
		
		if(this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "STOP")) {
			context2.getSymbolTable().setStringValue(this.currentToken.toString(), null);
			this.advance();
		}
		else if(this.currentToken.getType() != TokenType.NEWLINE && this.currentToken.getType() != TokenType.END_OF_FILE) {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
			result.failure(error);
			return result;
		}
		else {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing STOP keyword");
			result.failure(error);
			return result;
		}
				
		while(this.currentToken.getType() == TokenType.NEWLINE) {
			this.advance();
		}
		
		if(this.currentToken.getType() != TokenType.NEWLINE && this.currentToken.getType() != TokenType.END_OF_FILE) {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
			result.failure(error);
			return result;
		}
		result.success(new ListNode(statements, positionStart, this.currentToken.getPositionEnd().copy()));
		return result;
	}
	
	public ParseResult varStatements(Context context1, Context context2) {
		ParseResult result = new ParseResult();
		ArrayList<ParseResult> statements = new ArrayList<ParseResult>();
		Position positionStart = this.currentToken.getPositionStart().copy();
		int newLineCount, index = 0;

		while(true){
			if(this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "VAR")) {
				if(context2.getSymbolTable().isSymbol("START")) {
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "START keyword is already declared, cannot add more variables");
					result.failure(error);
					return result;
				}
				statements.add(this.assignValue(context1, context2)); 	
				if(statements.get(index).getError() != null) {
					 result.failure(statements.get(index).getError());
					 return result;
				}
				index++;
			}
			else {
				break;
			}

			newLineCount = 0;

			if(this.currentToken.getType() != TokenType.NEWLINE) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing new line");
				result.failure(error);
				return result;
			}
			
			while(this.currentToken.getType() == TokenType.NEWLINE) {
				
				newLineCount++;
				this.advance();
			}
			
			if(newLineCount == 0) {
				break;
			}
		}
		result.success(new ListNode(statements, positionStart, this.currentToken.getPositionEnd().copy()));
		return result;
	}
	
	public ParseResult identifierOutputStatements(Context context1, Context context2) {
		ParseResult result = new ParseResult();
		ArrayList<ParseResult> statements = new ArrayList<ParseResult>();
		Position positionStart = this.currentToken.getPositionStart().copy();
		int newLineCount, index = 0;
		
		while(true){
			newLineCount = 0;
			while(this.currentToken.getType() == TokenType.NEWLINE) {
				newLineCount++;
				this.advance();
			}
			
			if(newLineCount == 0) {
				break;
			}
			
			else if(this.currentToken.getType() == TokenType.IDENTIFIER || this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "OUTPUT") || this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "INPUT")) {
				if(!(context2.getSymbolTable().isSymbol("START"))) {
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing START keyword");
					result.failure(error);
					return result;
				}
				statements.add(this.assignValue(context1, context2)); 	
				if(statements.get(index).getError() != null) {
					 result.failure(statements.get(index).getError());
					 return result;
				}
				index++;
			}
			else {
				break;
			}
			
			if(this.currentToken.getType() != TokenType.NEWLINE) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing new line");
				result.failure(error);
				return result;
			}
		}
		result.success(new ListNode(statements, positionStart, this.currentToken.getPositionEnd().copy()));
		return result;
	}
	
	public ParseResult factor(Context context1, Context context2) {
		ParseResult result = new ParseResult();
		Token token = this.currentToken;

		if(token.getType() == TokenType.PLUS || token.getType() == TokenType.MINUS) { 
			this.advance();
			UnaryOpNode factor1 = null;
			NumberNode factor2 = null;
			VarAccessNode factor3 = null; // added change
			BinOpNode factor4 = null; // added change
			
			result = this.factor(context1, context2);
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
			else if(result.getVarAccessNode() != null){ // added change
				factor3 = result.getVarAccessNode();
				result.success(new UnaryOpNode(token, factor3));
			}
			else if(result.getBinOpNode() != null){ // added change
				factor4 = result.getBinOpNode();
				result.success(new UnaryOpNode(token, factor4));
				result.removeBinOpNode();
			}
			
		}
		else if(token.getType() == TokenType.INT || token.getType() == TokenType.FLOAT) {
			result.success(new NumberNode(token));
			this.advance(); 
			return result; // added change
		}
		else if(token.getType() == TokenType.CHAR) {
			result.success(new CharNode(token));
			this.advance(); 
			return result;
		}
		else if(token.getType() == TokenType.BOOL || token.getType() == TokenType.STRING) {
			result.success(new StringNode(token));
			this.advance(); 
			return result;
		}
		else if(token.getType() == TokenType.KEYWORD && token.matches(TokenType.KEYWORD, "AS")) {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Expected value for identifier");
			result.failure(error);
			return result;
		}
		else if(token.getType() == TokenType.KEYWORD) {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
			result.failure(error);
			return result;
		}
		else if(token.getType() == TokenType.IDENTIFIER) {
			if(!(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString()))) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "\"" + this.currentToken.toString() + "\" is  not a declared identifier");
				result.failure(error);
				return result;
			}
			result.success(new VarAccessNode(token));
			this.advance();
		}
		else if(token.getType() == TokenType.LEFT_PARENTHESIS) {
			this.advance();
			result = this.expression(context1, context2);
			if(result.getError() != null) {
				return result;
			}
			
			if(this.currentToken.getType() == TokenType.RIGHT_PARTENTHESIS) {
				this.advance();
				if(result.getBinOpNode() != null) {
					result.success(result.getBinOpNode());
				}
				else if(result.getUnaryOpNode() != null) {
					result.success(result.getUnaryOpNode());
				}
				else if(result.getNumberNode() != null){
					result.success(result.getNumberNode());
				}
				
			}
			else {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Expected ')'");
				result.failure(error);
				return result;
			}
		}
		else {
			InvalidSyntaxError error = new InvalidSyntaxError(token.getPositionStart(), token.getPositionEnd(), "Expected INT, FLOAT, CHAR, BOOL, string, or identifier");
			result.failure(error);
			return result;
		}
		return result;
	}
	
	public ParseResult term(Context context1, Context context2) {
		ParseResult result = new ParseResult();
		BinOpNode leftFactor1 = null;
		UnaryOpNode leftFactor2 = null;
		NumberNode leftFactor3 = null;
		VarAccessNode leftFactor4 = null;
		StringNode leftFactor5 = null;
		CharNode leftFactor6 = null;
		
		result = this.factor(context1, context2);
		if(result.getError() != null) {
			return result;
		}
		if(result.getBinOpNode() != null) {
			leftFactor1 = result.getBinOpNode();
		}
		else if(result.getUnaryOpNode() != null) { 
			leftFactor2 = result.getUnaryOpNode();
		}
		else if(result.getNumberNode() != null){ 
			leftFactor3 = result.getNumberNode();
		}
		else if(result.getVarAccessNode() != null){
			leftFactor4 = result.getVarAccessNode();
		}
		else if(result.getStringNode() != null){
			leftFactor5 = result.getStringNode();
		}
		else if(result.getCharNode() != null){
			leftFactor6 = result.getCharNode();
		}
		
		
		
		boolean isTerm = false;
		while(leftFactor5 == null && leftFactor6 == null && (this.currentToken.getType() == TokenType.MULTIPLICATION || this.currentToken.getType() == TokenType.MODULO || this.currentToken.getType() == TokenType.DIVISION)) {
			isTerm = true;
			Token operator_token = this.currentToken;
			BinOpNode rightFactor1 = null;
			UnaryOpNode rightFactor2 = null;
			NumberNode rightFactor3 = null;
			VarAccessNode rightFactor4 = null;
			this.advance();
			result = this.factor(context1, context2);
			
			if(result.getBinOpNode() != null) {
				rightFactor1 = result.getBinOpNode();
			}
			else if(result.getUnaryOpNode() != null) {
				rightFactor2 = result.getUnaryOpNode();
			}
			else if(result.getNumberNode() != null){
				rightFactor3 = result.getNumberNode();
			}
			else if(result.getVarAccessNode() != null){
				rightFactor4 = result.getVarAccessNode();
			}
			else if(result.getStringNode() != null){
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Type mismatch in assignment");
				result.failure(error);
				return result;
			}
			else if(result.getCharNode() != null){
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Type mismatch in assignment");
				result.failure(error);
				return result;
			}
			else {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Expected INT, FLOAT, or identifier");
				result.failure(error);
				return result;
			}
			
			
			if(leftFactor1 != null && rightFactor1 != null) {
				leftFactor1 = new BinOpNode(leftFactor1, operator_token, rightFactor1);
			}
			else if(leftFactor1 != null && rightFactor2 != null) {
				leftFactor1 = new BinOpNode(leftFactor1, operator_token, rightFactor2);
			}
			else if(leftFactor1 != null && rightFactor3 != null) {
				leftFactor1 = new BinOpNode(leftFactor1, operator_token, rightFactor3);
			}
			else if(leftFactor1 != null && rightFactor4 != null) {
				leftFactor1 = new BinOpNode(leftFactor1, operator_token, rightFactor4);
			}
			else if(leftFactor2 != null && rightFactor1 != null) {
				leftFactor1 = new BinOpNode(leftFactor2, operator_token, rightFactor1);
			}
			else if(leftFactor2 != null && rightFactor2 != null) {
				leftFactor1 = new BinOpNode(leftFactor2, operator_token, rightFactor2);
			}
			else if(leftFactor2 != null && rightFactor3 != null) {
				leftFactor1 = new BinOpNode(leftFactor2, operator_token, rightFactor3);
			}
			else if(leftFactor2 != null && rightFactor4 != null) {
				leftFactor1 = new BinOpNode(leftFactor2, operator_token, rightFactor4);
			}
			else if(leftFactor3 != null && rightFactor1 != null) {
				leftFactor1 = new BinOpNode(leftFactor3, operator_token, rightFactor1);
			}
			else if(leftFactor3 != null && rightFactor2 != null) {
				leftFactor1 = new BinOpNode(leftFactor3, operator_token, rightFactor2);
			}
			else if(leftFactor3 != null && rightFactor3 != null) {
				leftFactor1 = new BinOpNode(leftFactor3, operator_token, rightFactor3);
			}
			else if(leftFactor3 != null && rightFactor4 != null) {
				leftFactor1 = new BinOpNode(leftFactor3, operator_token, rightFactor4);
			}
			else if(leftFactor4 != null && rightFactor1 != null) {
				leftFactor1 = new BinOpNode(leftFactor4, operator_token, rightFactor1);
			}
			else if(leftFactor4 != null && rightFactor2 != null) {
				leftFactor1 = new BinOpNode(leftFactor4, operator_token, rightFactor2);
			}
			else if(leftFactor4 != null && rightFactor3 != null) {
				leftFactor1 = new BinOpNode(leftFactor4, operator_token, rightFactor3);
			}
			else if(leftFactor4 != null && rightFactor4 != null) {
				leftFactor1 = new BinOpNode(leftFactor4, operator_token, rightFactor4);
			}
		}

		if(!isTerm) {
			if(result.getUnaryOpNode() != null) {
				result.success(leftFactor2);
			}
			else if(result.getNumberNode() != null){
				result.success(leftFactor3);
			}
			else if(result.getVarAccessNode() != null){
				result.success(leftFactor4);
			}
			else if(result.getStringNode() != null){
				result.success(leftFactor5);
			}
			else if(result.getCharNode() != null){
				result.success(leftFactor6);
			}
		}
		else {
			result.success(leftFactor1);
			isTerm = false;
		}
		
		return result;
	}
	
	public ParseResult expression(Context context1, Context context2) {
		ParseResult result = new ParseResult();
		BinOpNode leftTerm1 = null;
		UnaryOpNode leftTerm2 = null;
		NumberNode leftTerm3 = null;
		VarAccessNode leftTerm4 = null;
		StringNode leftTerm5 = null;
		CharNode leftTerm6 = null;
		
		result = this.term(context1, context2);
		if(result.getError() != null) {
			return result;
		}
		
		if(result.getBinOpNode() != null) {
			leftTerm1 = result.getBinOpNode();
		}
		else if(result.getUnaryOpNode() != null) {
			leftTerm2 = result.getUnaryOpNode();
		}
		else if(result.getNumberNode() != null){
			leftTerm3 = result.getNumberNode();
		}
		else if(result.getVarAccessNode() != null){
			leftTerm4 = result.getVarAccessNode();
		}
		else if(result.getStringNode() != null){
			leftTerm5 = result.getStringNode();
		}
		else if(result.getCharNode() != null){
			leftTerm6 = result.getCharNode();
		}
		
		
		
		boolean isExpression = false;
		while(leftTerm5 == null && leftTerm6 == null && (this.currentToken.getType() == TokenType.PLUS || this.currentToken.getType() == TokenType.MINUS)) {
			isExpression = true;
			Token operator_token = this.currentToken;
			BinOpNode rightTerm1 = null;
			UnaryOpNode rightTerm2 = null;
			NumberNode rightTerm3 = null;
			VarAccessNode rightTerm4 = null;
			this.advance();
			result = this.term(context1, context2);
			
			if(result.getBinOpNode() != null) {
				rightTerm1 = result.getBinOpNode();
			}
			else if(result.getUnaryOpNode() != null) {
				rightTerm2 = result.getUnaryOpNode();
			}
			else if(result.getNumberNode() != null){
				rightTerm3 = result.getNumberNode();
			}
			else if(result.getVarAccessNode() != null){
				rightTerm4 = result.getVarAccessNode();
			}
			else if(result.getStringNode() != null){
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Type mismatch in assignment");
				result.failure(error);
				return result;
			}
			else if(result.getCharNode() != null){
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Type mismatch in assignment");
				result.failure(error);
				return result;
			}
			else {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Expected INT, FLOAT, or identifier");
				result.failure(error);
				return result;
			}
			
			if(leftTerm1 != null && rightTerm1 != null) {
				leftTerm1 = new BinOpNode(leftTerm1, operator_token, rightTerm1);
			}
			else if(leftTerm1 != null && rightTerm2 != null) {
				leftTerm1 = new BinOpNode(leftTerm1, operator_token, rightTerm2);
			}
			else if(leftTerm1 != null && rightTerm3 != null) {
				leftTerm1 = new BinOpNode(leftTerm1, operator_token, rightTerm3);
			}
			else if(leftTerm1 != null && rightTerm4 != null) {
				leftTerm1 = new BinOpNode(leftTerm1, operator_token, rightTerm4);
			}
			else if(leftTerm2 != null && rightTerm1 != null) {
				leftTerm1 = new BinOpNode(leftTerm2, operator_token, rightTerm1);
			}
			else if(leftTerm2 != null && rightTerm2 != null) {
				leftTerm1 = new BinOpNode(leftTerm2, operator_token, rightTerm2);
			}
			else if(leftTerm2 != null && rightTerm3 != null) {
				leftTerm1 = new BinOpNode(leftTerm2, operator_token, rightTerm3);
			}
			else if(leftTerm2 != null && rightTerm4 != null) {
				leftTerm1 = new BinOpNode(leftTerm2, operator_token, rightTerm4);
			}
			else if(leftTerm3 != null && rightTerm1 != null) {
				leftTerm1 = new BinOpNode(leftTerm3, operator_token, rightTerm1);
			}
			else if(leftTerm3 != null && rightTerm2 != null) {
				leftTerm1 = new BinOpNode(leftTerm3, operator_token, rightTerm2);
			}
			else if(leftTerm3 != null && rightTerm3 != null) {
				leftTerm1 = new BinOpNode(leftTerm3, operator_token, rightTerm3);
			}
			else if(leftTerm3 != null && rightTerm4 != null) {
				leftTerm1 = new BinOpNode(leftTerm3, operator_token, rightTerm4);
			}
			else if(leftTerm4 != null && rightTerm1 != null) {
				leftTerm1 = new BinOpNode(leftTerm4, operator_token, rightTerm1);
			}
			else if(leftTerm4 != null && rightTerm2 != null) {
				leftTerm1 = new BinOpNode(leftTerm4, operator_token, rightTerm2);
			}
			else if(leftTerm4 != null && rightTerm3 != null) {
				leftTerm1 = new BinOpNode(leftTerm4, operator_token, rightTerm3);
			}
			else if(leftTerm4 != null && rightTerm4 != null) {
				leftTerm1 = new BinOpNode(leftTerm4, operator_token, rightTerm4);
			}
		}
		
		if(!isExpression) {
			if(result.getUnaryOpNode() != null) {
				result.success(leftTerm2);
			}
			else if(result.getNumberNode() != null){
				result.success(leftTerm3);
			}
			else if(result.getVarAccessNode() != null){
				result.success(leftTerm4);
			}
			else if(result.getStringNode() != null){
				result.success(leftTerm5);
			}
			else if(result.getCharNode() != null){
				result.success(leftTerm6);
			}
		}
		else {
			result.success(leftTerm1);
			isExpression = false;
		}
		
		return result;
	}
	
	public ParseResult outputValue(Context context1, Context context2) {
		ParseResult result = new ParseResult();
		BinOpNode leftExpression1 = null;
		UnaryOpNode leftExpression2 = null;
		NumberNode leftExpression3 = null;
		VarAccessNode leftExpression4 = null;
		StringNode leftExpression5 = null;
		CharNode leftExpression6 = null;
		
		result = this.factor(context1, context2);
		if(result.getError() != null) {
			return result;
		}
		
		if(result.getBinOpNode() != null) {
			leftExpression1 = result.getBinOpNode();
		}
		else if(result.getUnaryOpNode() != null) {
			leftExpression2 = result.getUnaryOpNode();
		}
		else if(result.getNumberNode() != null){
			leftExpression3 = result.getNumberNode();
		}
		else if(result.getVarAccessNode() != null){
			leftExpression4 = result.getVarAccessNode();
		}
		else if(result.getStringNode() != null){
			leftExpression5 = result.getStringNode();
		}
		else if(result.getCharNode() != null){
			leftExpression6 = result.getCharNode();
		}
		
		boolean isStringOutput = false;
		while(this.currentToken.getType() == TokenType.AMPERSAND) {
			BinOpNode rightExpression1 = null;
			UnaryOpNode rightExpression2 = null;
			NumberNode rightExpression3 = null;
			VarAccessNode rightExpression4 = null;
			StringNode rightExpression5 = null;
			CharNode rightExpression6 = null;
			isStringOutput = true;
			Token operator_token = this.currentToken;
			this.advance();
			result = this.factor(context1, context2);
			if(result.getError() != null) {
				return result;
			}
			
			if(result.getBinOpNode() != null) {
				rightExpression1 = result.getBinOpNode();
			}
			else if(result.getUnaryOpNode() != null) {
				rightExpression2 = result.getUnaryOpNode();
			}
			else if(result.getNumberNode() != null){
				rightExpression3 = result.getNumberNode();
			}
			else if(result.getVarAccessNode() != null){
				rightExpression4 = result.getVarAccessNode();
			}
			else if(result.getStringNode() != null){
				rightExpression5 = result.getStringNode();
			}
			else if(result.getCharNode() != null){
				rightExpression6 = result.getCharNode();
			}
			else {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Expected INT, FLOAT, CHAR, BOOL, string, or identifier");
				result.failure(error);
				return result;
			}
			

			
			if(leftExpression1 != null && rightExpression1 != null) {
				leftExpression1 = new BinOpNode(leftExpression1, operator_token, rightExpression1);
			}
			else if(leftExpression1 != null && rightExpression2 != null) {
				leftExpression1 = new BinOpNode(leftExpression1, operator_token, rightExpression2);
			}
			else if(leftExpression1 != null && rightExpression3 != null) {
				leftExpression1 = new BinOpNode(leftExpression1, operator_token, rightExpression3);
			}
			else if(leftExpression1 != null && rightExpression4 != null) {
				leftExpression1 = new BinOpNode(leftExpression1, operator_token, rightExpression4);
			}
			else if(leftExpression1 != null && rightExpression5 != null) {
				leftExpression1 = new BinOpNode(leftExpression1, operator_token, rightExpression5);
			}
			else if(leftExpression1 != null && rightExpression6 != null) {
				leftExpression1 = new BinOpNode(leftExpression1, operator_token, rightExpression6);
			}
			else if(leftExpression2 != null && rightExpression1 != null) {
				leftExpression1 = new BinOpNode(leftExpression2, operator_token, rightExpression1);
			}
			else if(leftExpression2 != null && rightExpression2 != null) {
				leftExpression1 = new BinOpNode(leftExpression2, operator_token, rightExpression2);
			}
			else if(leftExpression2 != null && rightExpression3 != null) {
				leftExpression1 = new BinOpNode(leftExpression2, operator_token, rightExpression3);
			}
			else if(leftExpression2 != null && rightExpression4 != null) {
				leftExpression1 = new BinOpNode(leftExpression2, operator_token, rightExpression4);
			}
			else if(leftExpression2 != null && rightExpression5 != null) {
				leftExpression1 = new BinOpNode(leftExpression2, operator_token, rightExpression5);
			}
			else if(leftExpression2 != null && rightExpression6 != null) {
				leftExpression1 = new BinOpNode(leftExpression2, operator_token, rightExpression6);
			}
			else if(leftExpression3 != null && rightExpression1 != null) {
				leftExpression1 = new BinOpNode(leftExpression3, operator_token, rightExpression1);
			}
			else if(leftExpression3 != null && rightExpression2 != null) {
				leftExpression1 = new BinOpNode(leftExpression3, operator_token, rightExpression2);
			}
			else if(leftExpression3 != null && rightExpression3 != null) {
				leftExpression1 = new BinOpNode(leftExpression3, operator_token, rightExpression3);
			}
			else if(leftExpression3 != null && rightExpression4 != null) {
				leftExpression1 = new BinOpNode(leftExpression3, operator_token, rightExpression4);
			}
			else if(leftExpression3 != null && rightExpression5 != null) {
				leftExpression1 = new BinOpNode(leftExpression3, operator_token, rightExpression5);
			}
			else if(leftExpression3 != null && rightExpression6 != null) {
				leftExpression1 = new BinOpNode(leftExpression3, operator_token, rightExpression6);
			}
			else if(leftExpression4 != null && rightExpression1 != null) {
				leftExpression1 = new BinOpNode(leftExpression4, operator_token, rightExpression1);
			}
			else if(leftExpression4 != null && rightExpression2 != null) {
				leftExpression1 = new BinOpNode(leftExpression4, operator_token, rightExpression2);
			}
			else if(leftExpression4 != null && rightExpression3 != null) {
				leftExpression1 = new BinOpNode(leftExpression4, operator_token, rightExpression3);
			}
			else if(leftExpression4 != null && rightExpression4 != null) {
				leftExpression1 = new BinOpNode(leftExpression4, operator_token, rightExpression4);
			}
			else if(leftExpression4 != null && rightExpression5 != null) {
				leftExpression1 = new BinOpNode(leftExpression4, operator_token, rightExpression5);
			}
			else if(leftExpression4 != null && rightExpression6 != null) {
				leftExpression1 = new BinOpNode(leftExpression4, operator_token, rightExpression6);
			}
			else if(leftExpression5 != null && rightExpression1 != null) {
				leftExpression1 = new BinOpNode(leftExpression5, operator_token, rightExpression1);
			}
			else if(leftExpression5 != null && rightExpression2 != null) {
				leftExpression1 = new BinOpNode(leftExpression5, operator_token, rightExpression2);
			}
			else if(leftExpression5 != null && rightExpression3 != null) {
				leftExpression1 = new BinOpNode(leftExpression5, operator_token, rightExpression3);
			}
			else if(leftExpression5 != null && rightExpression4 != null) {
				leftExpression1 = new BinOpNode(leftExpression5, operator_token, rightExpression4);
			}
			else if(leftExpression5 != null && rightExpression5 != null) {
				leftExpression1 = new BinOpNode(leftExpression5, operator_token, rightExpression5);
			}
			else if(leftExpression5 != null && rightExpression6 != null) {
				leftExpression1 = new BinOpNode(leftExpression5, operator_token, rightExpression6);
			}
			else if(leftExpression6 != null && rightExpression1 != null) {
				leftExpression1 = new BinOpNode(leftExpression6, operator_token, rightExpression1);
			}
			else if(leftExpression6 != null && rightExpression2 != null) {
				leftExpression1 = new BinOpNode(leftExpression6, operator_token, rightExpression2);
			}
			else if(leftExpression6 != null && rightExpression3 != null) {
				leftExpression1 = new BinOpNode(leftExpression6, operator_token, rightExpression3);
			}
			else if(leftExpression6 != null && rightExpression4 != null) {
				leftExpression1 = new BinOpNode(leftExpression6, operator_token, rightExpression4);
			}
			else if(leftExpression6 != null && rightExpression5 != null) {
				leftExpression1 = new BinOpNode(leftExpression6, operator_token, rightExpression5);
			}
			else if(leftExpression6 != null && rightExpression6 != null) {
				leftExpression1 = new BinOpNode(leftExpression6, operator_token, rightExpression6);
			}
		}
		
		if(!isStringOutput) {
			if(result.getUnaryOpNode() != null) {
				result.success(leftExpression2);
			}
			else if(result.getNumberNode() != null){
				result.success(leftExpression3);
			}
			else if(result.getVarAccessNode() != null){
				result.success(leftExpression4);
			}
			else if(result.getStringNode() != null){
				result.success(leftExpression5);
			}
			else if(result.getCharNode() != null){
				result.success(leftExpression6);
			}
		}
		else {
			result.success(leftExpression1);
			isStringOutput = false;
		}
		
		return result;
	}
	
	public ParseResult assignValue(Context context1, Context context2) {
		ParseResult result = new ParseResult();
		ParseResult result2 = new ParseResult();
		ArrayList<Token> assignmentIdentifiers = new ArrayList<Token>();
		ArrayList<Token> allIdentifiers = new ArrayList<Token>();
		
		if(this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "VAR")) {
			this.advance();
			
			if(this.currentToken.getType() == TokenType.KEYWORD) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
				result.failure(error);	
				return result;
			}
			else if(this.currentToken.getType() != TokenType.IDENTIFIER) {
				if(this.currentToken.getType() == TokenType.NEWLINE || this.currentToken.getType() == TokenType.END_OF_FILE) {
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing identifier");
					result.failure(error);
					return result;
				}
				if(this.currentToken.toString() == "") {
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(),  "Delete this " + this.currentToken.getType());
					result.failure(error);
				}
				else {
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(),  "\"" +this.currentToken.toString() + "\" is not an identifier");
					result.failure(error);
				}
				
				return result;
			}
			
			while(this.currentToken.getType() == TokenType.IDENTIFIER) {
				assignmentIdentifiers.clear();
	
				if(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString())) {
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "\"" +this.currentToken.toString() + "\" is already declared");
					result.failure(error);
					return result;
				}
				
				if(this.currentToken.getType() == TokenType.IDENTIFIER) {
					allIdentifiers.add(this.currentToken);
					assignmentIdentifiers.add(this.currentToken);
				}
				
				this.advance();
				
				if(this.currentToken.getType() == TokenType.EQUALS) {
					this.advance();
					
					if(this.currentToken.getType() == TokenType.EQUALS) {
						InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Extra equals sign");
						result.failure(error);
						return result;
					}
					
					while(this.currentToken.getType() == TokenType.IDENTIFIER || this.currentToken.getType() == TokenType.EQUALS) {
						if(this.seeNextToken().getType() != TokenType.EQUALS && this.currentToken.getType() != TokenType.EQUALS) {
							if(!(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString()))) {
								InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "\"" + this.currentToken.toString() + "\" is  not a declared identifier");
								result.failure(error);
								return result;
							}
							break;
						}
						
						if(this.currentToken.getType() == TokenType.IDENTIFIER) {
							if(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString())) {
								assignmentIdentifiers.add(this.currentToken);
							}
							else {
								InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "\"" + this.currentToken.toString() + "\" is  not a declared identifier");
								result.failure(error);
								return result;
							}
							
							this.advance();
						}
						else if(this.currentToken.getType() == TokenType.EQUALS) {
							this.advance();
							if(this.currentToken.getType() == TokenType.EQUALS) {
								InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Extra equals sign");
								result.failure(error);
								return result;
							}
						}
					}
					
					result = this.expression(context1, context2);
					
					if(result.getError() != null) {
						return result;
					}
					
					if(!(this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "AS")) && this.currentToken.getType() != TokenType.COMMA){
						InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Error in identifier assignment");
						result.failure(error);
						return result;
					}
					
					for(int i = 0; i < assignmentIdentifiers.size(); i++) {
						if(result.getBinOpNode() != null) {
							result2.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getBinOpNode()));
						}
						else if(result.getUnaryOpNode() != null) {
							result2.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getUnaryOpNode()));
						}
						else if(result.getNumberNode() != null){
							result2.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getNumberNode()));
						}
						else if(result.getCharNode() != null){
							result2.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getCharNode()));
						}
						else if(result.getStringNode() != null){
							result2.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getStringNode()));
						}
						else if(result.getVarAccessNode() != null){
							result2.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getVarAccessNode()));
						}
					}
				}
				
				if(this.currentToken.getType() == TokenType.IDENTIFIER) {
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
					result.failure(error);
					return result;
				}
				
				if(this.currentToken.getType() == TokenType.COMMA) {
					this.advance();
					if(this.currentToken.getType() != TokenType.IDENTIFIER) {
						InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Extra comma");
						result.failure(error);
						return result;
					}
				}
			}
			
			if(this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "AS")) {
				this.advance();
				if(this.currentToken.getType() == TokenType.KEYWORD &&  (this.currentToken.matches(TokenType.KEYWORD, "CHAR") || this.currentToken.matches(TokenType.KEYWORD, "BOOL") || this.currentToken.matches(TokenType.KEYWORD, "INT") || this.currentToken.matches(TokenType.KEYWORD, "FLOAT"))) {
					for(int i = 0; i < allIdentifiers.size(); i++) {
						context1.getDeclaredVar().setVar(allIdentifiers.get(i).toString(), this.currentToken);
					}
					this.advance();
				}
				else if(this.currentToken.getType() == TokenType.NEWLINE || this.currentToken.getType() == TokenType.END_OF_FILE){
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing identifier's data type");
					result.failure(error);
					return result;
				}
				else {
					InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
					result.failure(error);
					return result;
				}
				
				
			}
			else if(this.currentToken.getType() == TokenType.NEWLINE || this.currentToken.getType() == TokenType.END_OF_FILE){
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Missing identifier's data type");
				result.failure(error);
				return result;
			}
			else {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
				result.failure(error);
				return result;
			}
			return result2;
		}
		else if(this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "OUTPUT")) {
			Token outputToken = this.currentToken;
			if(!(context2.getSymbolTable().isSymbol("OUTPUT"))) {
				context2.getSymbolTable().setStringValue(this.currentToken.toString(), null);
				context1.getDeclaredVar().setVar(this.currentToken.toString(), this.currentToken);
			}
			this.advance();
			if(this.currentToken.getType() != TokenType.COLON) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Expected ':'");
				result.failure(error);
				return result;
			}
			this.advance();
			result = this.outputValue(context1, context2);
			if(result.getError() != null) {
				return result;
			}
			if(result.getError() == null && this.currentToken.getType() != TokenType.NEWLINE ) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Error in OUTPUT");
				result.failure(error);
				return result;
			}
			
			if(result.getBinOpNode() != null) {
				result.success(new VarAssignNode(outputToken, result.getBinOpNode()));
			}
			else if(result.getUnaryOpNode() != null) {
				result.success(new VarAssignNode(outputToken, result.getUnaryOpNode()));
			}
			else if(result.getNumberNode() != null){
				result.success(new VarAssignNode(outputToken, result.getNumberNode()));
			}
			else if(result.getCharNode() != null){
				result.success(new VarAssignNode(outputToken, result.getCharNode()));
			}
			else if(result.getStringNode() != null){
				result.success(new VarAssignNode(outputToken, result.getStringNode()));
			}
			else if(result.getVarAccessNode() != null){
				result.success(new VarAssignNode(outputToken, result.getVarAccessNode()));
			}
		}
		else if(this.currentToken.getType() == TokenType.KEYWORD &&  this.currentToken.matches(TokenType.KEYWORD, "INPUT")) { //added change
			Token inputToken = this.currentToken;
			if(!(context2.getSymbolTable().isSymbol("INPUT"))) {
				context1.getDeclaredVar().setVar(this.currentToken.toString(), this.currentToken);
			}
			this.advance();
			if(this.currentToken.getType() != TokenType.COLON) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Expected ':'");
				result.failure(error);
				return result;
			}
			this.advance();
			ArrayList<Token> inputTokens = new ArrayList<Token>();
			while(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString())) {
				if(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString())) {
					inputTokens.add(this.currentToken);
					this.advance();
				}
				
				if(this.currentToken.getType() == TokenType.COMMA && this.seeNextToken().getType() == TokenType.IDENTIFIER) {
					if(this.currentToken.getType() == TokenType.COMMA) {
						this.advance();
						if(this.currentToken.getType() == TokenType.COMMA) {
							InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Extra comma");
							result.failure(error);
							return result;
						}
					}
				}
				else {
					break;
				}
			}
			
			if(this.currentToken.getType() == TokenType.COMMA) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Extra comma");
				result.failure(error);
				return result;
			}
			else if(this.currentToken.getType() != TokenType.NEWLINE ) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.currentToken.getType());
				result.failure(error);
				return result;
			}
			else {
				result.success(new VarAssignNode(inputToken, inputTokens));
			}
		}
		else if(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString())) {
			assignmentIdentifiers.clear();
			assignmentIdentifiers.add(this.currentToken);
			this.advance();
			if(this.currentToken.getType() != TokenType.EQUALS) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Delete this " + this.seePreviousToken().getType());
				result.failure(error);
				return result;
			}
			this.advance();
			while(this.currentToken.getType() == TokenType.IDENTIFIER || this.currentToken.getType() == TokenType.EQUALS) {
				if(this.seeNextToken().getType() != TokenType.EQUALS && this.currentToken.getType() != TokenType.EQUALS) {
					if(!(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString()))) {
						InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "\"" + this.currentToken.toString() + "\" is  not a declared identifier");
						result.failure(error);
						return result;
					}
					break;
				}
				
				if(this.currentToken.getType() == TokenType.IDENTIFIER) {
					if(context1.getDeclaredVar().isDeclaredVar(this.currentToken.toString())) {
						assignmentIdentifiers.add(this.currentToken);
					}
					else {
						InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "\"" + this.currentToken.toString() + "\" is  not a declared identifier");
						result.failure(error);
						return result;
					}
					
					this.advance();
				}
				else if(this.currentToken.getType() == TokenType.EQUALS) {
					this.advance();
					if(this.currentToken.getType() == TokenType.EQUALS) {
						InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Extra equals sign");
						result.failure(error);
						return result;
					}
				}
			}
			result = this.expression(context1, context2);
			
			
			if(result.getError() != null) {
				return result;
			}
			
			if(result.getError() == null && this.currentToken.getType() != TokenType.NEWLINE ) {
				InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "Error in identifier assignment");
				result.failure(error);
				return result;
			}
			for(int i = 0; i < assignmentIdentifiers.size(); i++) {
				if(result.getBinOpNode() != null) {
					result.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getBinOpNode()));
				}
				else if(result.getUnaryOpNode() != null) {
					result.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getUnaryOpNode()));
				}
				else if(result.getNumberNode() != null){
					result.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getNumberNode()));
				}
				else if(result.getCharNode() != null){
					result.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getCharNode()));
				}
				else if(result.getStringNode() != null){
					result.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getStringNode()));
				}
				else if(result.getVarAccessNode() != null){
					result.success(new VarAssignNode(assignmentIdentifiers.get(i), result.getVarAccessNode()));
				}
			}
		}
		else {
			InvalidSyntaxError error = new InvalidSyntaxError(this.currentToken.getPositionStart(), this.currentToken.getPositionEnd(), "\"" + this.currentToken.toString() + "\" is not a declared identifier");
			result.failure(error);
			return result;
		}
		return result;
	}
	

}
