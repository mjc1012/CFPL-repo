package programminglanguage;

import java.util.ArrayList;
import java.util.Scanner;

public class Interpreter {
	private Scanner scanner; 
	public Interpreter() {}
	
	public void printOutput(Context context2) { 
		System.out.print(context2.getSymbolTable().getSymbolTable().get("OUTPUT"));
	}
	
	public void setScanner(Scanner scanner) { // added change
		this.scanner = scanner;
	}
	
	public Scanner getScanner() { // added change
		return this.scanner;
	}
	
	public RuntimeResult visit(ParseResult result, Context context1, Context context2) throws Exception {
		RuntimeResult n = new RuntimeResult();
		if(result.getListNode() != null) {
			n = this.visit(result.getListNode(),  context1, context2);
			if(n.getError() != null) {
				return n;
			}
		}
		if(result.getVarAssignNode() != null) {
			n = this.visit(result.getVarAssignNode(),  context1, context2);
			if(n.getError() != null) {
				return n;
			}
		}
		else if(result.getVarAccessNode() != null) {
			n = this.visit(result.getVarAccessNode(),  context1, context2);
			if(n.getError() != null) {
				return n;
			}
		}
		else if(result.getBinOpNode() != null) {
			n = this.visit(result.getBinOpNode(),  context1, context2);
			if(n.getError() != null) {
				return n;
			}
		}
		else if(result.getUnaryOpNode() != null) {
			n = this.visit(result.getUnaryOpNode(),  context1, context2);
			if(n.getError() != null) {
				return n;
			}
		}
		else if(result.getNumberNode() != null) {
			n = this.visit(result.getNumberNode(),  context1, context2);
			if(n.getError() != null) {
				return n;
			}
		}
		else if(result.getCharNode() != null) {
			n = this.visit(result.getCharNode(),  context1, context2);
			if(n.getError() != null) {
				return n;
			}
		}
		else if(result.getStringNode() != null) {
			n = this.visit(result.getStringNode(),  context1, context2);
			if(n.getError() != null) {
				return n;
			}
		}
		else {
			this.no_visit_method();
		}
		
		return n;
	}
	
	public RuntimeResult visit(ListNode node, Context context1, Context context2) throws Exception {
		RuntimeResult result = new RuntimeResult();
		RuntimeResult result2 = new RuntimeResult();
		ArrayList<RuntimeResult> list = new ArrayList<RuntimeResult>(); 
		for(ParseResult statement : node.getList()) {
			 result = this.visit(statement, context1, context2);
			 if(result.getError() != null) {
				return result;
			 }
			 list.add(result);
		 }
		
		ListValue l = new ListValue(list);
		l.setPosition(node.getPositionStart(), node.getPositionEnd());
		result2.success(l);
		return result2;
	}
	
	public RuntimeResult visit(VarAccessNode node, Context context1, Context context2) {
		RuntimeResult result = new RuntimeResult();
		String varName = node.getVarName().getStringValue();
		Number value1 = null;
		StringValue value2 = null;
		CharValue value3 = null;
		if(context2.getSymbolTable().getNumberValue(varName) != null) {
			value1 = context2.getSymbolTable().getNumberValue(varName);
		}
		else if(context2.getSymbolTable().getStringValue(varName) != null) {
			value2 = context2.getSymbolTable().getStringValue(varName);
		}
		else if(context2.getSymbolTable().getCharValue(varName) != null) {
			value3 = context2.getSymbolTable().getCharValue(varName);
		}

		
		if(value1 == null && value2 == null && value3 == null) {
			RuntimeError error = new RuntimeError(node.getPositionStart(), node.getPositionEnd(), "\"" + varName + "\" is not initialized");
			result.failure(error);
			return result;
		}
		
		if(value1 != null) {
			result.success(value1);
		}
		else if(value2 != null) {
			result.success(value2);
		}
		else if(value3 != null) {
			result.success(value3);
		}
		
		return result;
	}
	
	public RuntimeResult visit(ArrayList<VarAssignNode> node, Context context1, Context context2) throws Exception {
		RuntimeResult result = new RuntimeResult();
		for(int i = 0; i < node.size(); i++) {
			result = this.visit(node.get(i),  context1, context2);
			if(result.getError() != null) {
				return result;
			}
		}
		
		return result;
	}
	
	public RuntimeResult visit(VarAssignNode node, Context context1, Context context2) throws Exception {
		RuntimeResult result = new RuntimeResult();
		RuntimeError error = null;
		String varName = node.getVarName().getStringValue();
		boolean isNumber = false;
		boolean isStr = false;
		boolean isChar = false;
		
		if(node.getNumberNode() != null) {
			result = this.visit(node.getNumberNode(),  context1, context2);
			isNumber = true;
		}
		else if(node.getUnaryOpNode() != null) {
			result = this.visit(node.getUnaryOpNode(),  context1, context2);
			isNumber = true;
		}
		else if(node.getBinOpNode() != null) {
			result = this.visit(node.getBinOpNode(),  context1, context2);
			if(result.getNumber() != null) {
				isNumber = true;
			}
			else if(result.getCharValue() != null) {
				isChar = true;
			}
			else if(result.getStringValue() != null) {
				isStr = true;
			}
		}	
		else if(node.getCharNode() != null) {
			result = this.visit(node.getCharNode(),  context1, context2);
			isChar = true;
		}
		else if(node.getStringNode() != null) {
			result = this.visit(node.getStringNode(),  context1, context2);
			isStr = true;
		}
		else if(node.getVarAccessNode() != null) {
			result = this.visit(node.getVarAccessNode(),  context1, context2);
			if(result.getNumber() != null) {
				isNumber = true;
			}
			else if(result.getCharValue() != null) {
				isChar = true;
			}
			else if(result.getStringValue() != null) {
				isStr = true;
			}
		}
		
		if(result.getError() != null) {
			return result;
		}
		
		//added change
		if(context1.getDeclaredVar().getDataType(varName).getType() == TokenType.KEYWORD && context1.getDeclaredVar().getDataType(varName).matches(TokenType.KEYWORD, "INPUT")) { 
			InputParser inputParser = new InputParser(node.getInputTokens());
			ParseResult ast = inputParser.assignValue();
			setScanner(inputParser.getScanner());
			if(ast.getError() != null) {
				error = new RuntimeError(node.getVarName().getPositionStart(), node.getVarName().getPositionEnd(), "Error in INPUT");
				result.failure(error);
				return result;
			}
			result = this.visit(ast, context1, context2);
			return result;
		}
		else if(isNumber) {
			Number value = result.getNumber();
			if(context1.getDeclaredVar().getDataType(varName).getType() == TokenType.KEYWORD && context1.getDeclaredVar().getDataType(varName).matches(TokenType.KEYWORD, "OUTPUT")) {
				context2.getSymbolTable().setStringValue(varName, new StringValue(value.toString())); // added change
				result.success(value);
				this.printOutput(context2);
				return result;
			}
			else if(value.getFloatValue() != null || value.getIntValue() != null) {
				if(context1.getDeclaredVar().getDataType(varName).getType() == TokenType.KEYWORD && context1.getDeclaredVar().getDataType(varName).matches(TokenType.KEYWORD, "INT")) {
					if(value.getIntValue() != null) {
						context2.getSymbolTable().setNumberValue(varName, value);
						result.success(value);
						return result;
					}
				}
				else if(context1.getDeclaredVar().getDataType(varName).getType() == TokenType.KEYWORD && context1.getDeclaredVar().getDataType(varName).matches(TokenType.KEYWORD, "FLOAT")) {
						context2.getSymbolTable().setNumberValue(varName, value);
						result.success(value);
						return result;
				}
			}
			
			
			error = new RuntimeError(value.getPositionStart(), value.getPositionEnd(), "Type mismatch in assignment");	
		}
		else if(isStr) {
			StringValue value = result.getStringValue();
			if(context1.getDeclaredVar().getDataType(varName).getType() == TokenType.KEYWORD && context1.getDeclaredVar().getDataType(varName).matches(TokenType.KEYWORD, "OUTPUT")) {
				context2.getSymbolTable().setStringValue(varName, new StringValue(value.toString())); // added change
				result.success(value);
				this.printOutput(context2);
				return result;
			}
			else if(context1.getDeclaredVar().getDataType(varName).getType() == TokenType.KEYWORD && context1.getDeclaredVar().getDataType(varName).matches(TokenType.KEYWORD, "BOOL")) {
				if((value.getStringValue().equals("TRUE")) || (value.getStringValue().equals("FALSE"))) {
					context2.getSymbolTable().setStringValue(varName, value);
					result.success(value);
					return result;
				}
			}

			error = new RuntimeError(value.getPositionStart(), value.getPositionEnd(), "Type mismatch in assignment");
		}
		else if(isChar) {
			CharValue value = result.getCharValue();
			if(context1.getDeclaredVar().getDataType(varName).getType() == TokenType.KEYWORD && context1.getDeclaredVar().getDataType(varName).matches(TokenType.KEYWORD, "OUTPUT")) {
				context2.getSymbolTable().setStringValue(varName, new StringValue(value.toString())); // added change
				result.success(value);
				this.printOutput(context2);
				return result;
			}
			else if(context1.getDeclaredVar().getDataType(varName).getType() == TokenType.KEYWORD && context1.getDeclaredVar().getDataType(varName).matches(TokenType.KEYWORD, "CHAR")) {
					
					context2.getSymbolTable().setCharValue(varName, value);
					result.success(value);
					return result;
			}
			error = new RuntimeError(value.getPositionStart(), value.getPositionEnd(), "Type mismatch in assignment");
		}
		result.failure(error);
		return result;
	}
	
	public RuntimeResult visit(StringNode node, Context context1, Context context2) {
		RuntimeResult result = new RuntimeResult();
		StringValue s = new StringValue(node.getToken().getStringValue());
		s.setPosition(node.getPositionStart(), node.getPositionEnd());
		result.success(s);
		return result;
	}
	
	public RuntimeResult visit(CharNode node, Context context1, Context context2) {
		RuntimeResult result = new RuntimeResult();
		CharValue s = new CharValue(node.getToken().getCharValue());
		s.setPosition(node.getPositionStart(), node.getPositionEnd());
		result.success(s);
		return result;
	}
	
	public RuntimeResult visit(BinOpNode node, Context context1, Context context2)  {
		RuntimeResult result = new RuntimeResult();
		Number left1 = null;
		Number right1 = null;
		StringValue left2 = null;
		StringValue right2 = null;
		CharValue left3 = null;
		CharValue right3 = null;
		if(node.getBinOpLeftNode() != null) {
			if(result.registerNumber(this.visit(node.getBinOpLeftNode(), context1, context2)) != null) {
				left1 = result.registerNumber(this.visit(node.getBinOpLeftNode(), context1, context2));
			}
			else if(result.registerStringValue(this.visit(node.getBinOpLeftNode(), context1, context2)) != null) {
				left2 = result.registerStringValue(this.visit(node.getBinOpLeftNode(), context1, context2));
			}
		}
		else if(node.getUnaryOpLeftNode() != null) {
			left1 = result.registerNumber(this.visit(node.getUnaryOpLeftNode(), context1, context2));
		}
		else if(node.getNumberLeftNode() != null) {
			left1 = result.registerNumber(this.visit(node.getNumberLeftNode(), context1, context2));
		}
		else if(node.getVarAccessLeftNode() != null) {
			if(result.registerNumber(this.visit(node.getVarAccessLeftNode(), context1, context2)) != null) {
				left1 = result.registerNumber(this.visit(node.getVarAccessLeftNode(), context1, context2));
			}
			else if(result.registerStringValue(this.visit(node.getVarAccessLeftNode(), context1, context2)) != null) {
				left2 = result.registerStringValue(this.visit(node.getVarAccessLeftNode(), context1, context2));
			}
			else if(result.registerCharValue(this.visit(node.getVarAccessLeftNode(), context1, context2)) != null) {
				left3 = result.registerCharValue(this.visit(node.getVarAccessLeftNode(), context1, context2));
			}
		}
		else if(node.getStringLeftNode() != null) {
			left2 = result.registerStringValue(this.visit(node.getStringLeftNode(), context1, context2));
		}
		else if(node.getCharLeftNode() != null) {
			left3 = result.registerCharValue(this.visit(node.getCharLeftNode(), context1, context2));
		}
		
		if(result.getError() != null) {
			return result;
		}
		
		if(node.getBinOpRightNode() != null) {
			if(result.registerNumber(this.visit(node.getBinOpRightNode(), context1, context2)) != null) {
				right1 = result.registerNumber(this.visit(node.getBinOpRightNode(), context1, context2));
			}
			else if(result.registerStringValue(this.visit(node.getBinOpRightNode(), context1, context2)) != null) {
				right2 = result.registerStringValue(this.visit(node.getBinOpRightNode(), context1, context2));
			}
		}
		else if(node.getUnaryOpRightNode() != null) {
			right1 = result.registerNumber(this.visit(node.getUnaryOpRightNode(), context1, context2));
		}
		else if(node.getNumberRightNode() != null) {
			right1 = result.registerNumber(this.visit(node.getNumberRightNode(), context1, context2));
		}
		else if(node.getVarAccessRightNode() != null) {
			if(result.registerNumber(this.visit(node.getVarAccessRightNode(), context1, context2)) != null) {
				right1 = result.registerNumber(this.visit(node.getVarAccessRightNode(), context1, context2));
			}
			else if(result.registerStringValue(this.visit(node.getVarAccessRightNode(), context1, context2)) != null) {
				right2 = result.registerStringValue(this.visit(node.getVarAccessRightNode(), context1, context2));
			}
			else if(result.registerCharValue(this.visit(node.getVarAccessRightNode(), context1, context2)) != null) {
				right3 = result.registerCharValue(this.visit(node.getVarAccessRightNode(), context1, context2));
			}
		}
		else if(node.getStringRightNode() != null) {
			right2 = result.registerStringValue(this.visit(node.getStringRightNode(), context1, context2));
		}
		else if(node.getCharRightNode() != null) {
			right3 = result.registerCharValue(this.visit(node.getCharRightNode(), context1, context2));
		}
		
		if(result.getError() != null) {
			return result;
		}
		
		Number n = null;
		StringValue s = null;
		if(node.getOperatorToken().getType() == TokenType.AMPERSAND) {
			if(left1 != null && right1 != null) {
				left2 = new StringValue(left1.toString());
				right2 = new StringValue(right1.toString());
			}
			else if(left1 != null && right2 != null) {
				left2 = new StringValue(left1.toString());
			}
			else if(left1 != null && right3 != null) {
				left2 = new StringValue(left1.toString());
				right2 = new StringValue(right3.toString());
			}
			else if(left2 != null && right1 != null) {
				right2 = new StringValue(right1.toString());
			}
			else if(left2 != null && right3 != null) {
				right2 = new StringValue(right3.toString());
			}
			else if(left3 != null && right1 != null) {
				left2 = new StringValue(left3.toString());
				right2 = new StringValue(right1.toString());
			}
			else if(left3 != null && right2 != null) {
				left2 = new StringValue(left3.toString());
			}
			else if(left3 != null && right3 != null) {
				left2 = new StringValue(left3.toString());
				right2 = new StringValue(right3.toString());
			}

			s = left2.concatenate(right2);
			s.setPosition(node.getPositionStart(), node.getPositionEnd());
			result.success(s);
			return result;
		}
		
		if(left1 != null && right1 != null) {
			if(node.getOperatorToken().getType() == TokenType.PLUS) {
				n = left1.addedTo(right1);
			}
			else if(node.getOperatorToken().getType() == TokenType.MINUS) {
				n = left1.subtractedTo(right1);
			}
			else if(node.getOperatorToken().getType() == TokenType.MULTIPLICATION) {
				n = left1.multipliedTo(right1);
			}
			else if(node.getOperatorToken().getType() == TokenType.MODULO) {
				if(right1.getFloatValue() != null) {
					if(right1.getFloatValue() == 0) {
						RuntimeError error = new RuntimeError(right1.getPositionStart(), right1.getPositionEnd(), "Can't divide by zero");
						result.failure(error);
						return result;
					}
				}
				else if(right1.getIntValue() != null) {
					if(right1.getIntValue() == 0) {
						RuntimeError error = new RuntimeError(right1.getPositionStart(), right1.getPositionEnd(), "Can't divide by zero");
						result.failure(error);
						return result;
					}
				}
				n = left1.moduloTo(right1);
			}
			else if(node.getOperatorToken().getType() == TokenType.DIVISION) {
				if(right1.getFloatValue() != null) {
					if(right1.getFloatValue() == 0) {
						RuntimeError error = new RuntimeError(right1.getPositionStart(), right1.getPositionEnd(), "Can't divide by zero");
						result.failure(error);
						return result;
					}
				}
				else if(right1.getIntValue() != null) {
					if(right1.getIntValue() == 0) {
						RuntimeError error = new RuntimeError(right1.getPositionStart(), right1.getPositionEnd(), "Can't divide by zero");
						result.failure(error);
						return result;
					}
				}
				n = left1.dividedTo(right1);
			}
		}
		else {
			RuntimeError error = new RuntimeError(node.getPositionStart(), node.getPositionEnd(), "Type mismatch in assignment");
			result.failure(error);
			return result;
		}
		
		n.setPosition(node.getPositionStart(), node.getPositionEnd());
		result.success(n);
		return result;
	}
	
	public RuntimeResult visit(UnaryOpNode node, Context context1, Context context2) {
		RuntimeResult result = new RuntimeResult();
		Number n = null; 
		if(node.getUnaryOpNode() != null){
			n = result.registerNumber(this.visit(node.getUnaryOpNode(), context1, context2)); 
		}
		else if(node.getNumberNode() != null){
			n = result.registerNumber(this.visit(node.getNumberNode(), context1, context2)); 
		}
		else if(node.getVarAccessNode() != null){ // added change
			n = result.registerNumber(this.visit(node.getVarAccessNode(), context1, context2)); 
		}
		else if(node.getBinOpNode() != null){ // added change
			n = result.registerNumber(this.visit(node.getBinOpNode(), context1, context2)); 
		}
		
		if(result.getError() != null) {
			return result;
		}
		
		if(node.getOperatorToken().getType() == TokenType.MINUS) {
			n = n.multipliedTo(new Number(-1));
		}
		
		n.setPosition(node.getPositionStart(), node.getPositionEnd());
		result.success(n);
		return result;
	}
	
	public RuntimeResult visit(NumberNode node, Context context1, Context context2) {
		RuntimeResult result = new RuntimeResult();
		Number n = null;
		if(node.getToken().getIntValue() != null) {
			n = new Number(node.getToken().getIntValue());
		}
		else if(node.getToken().getFloatValue() != null) {
			n = new Number(node.getToken().getFloatValue());
		}
		n.setPosition(node.getPositionStart(), node.getPositionEnd());
		result.success(n);
		return result;
	}
	
	public void no_visit_method() throws Exception {
		throw new Exception("No visit method defined");
	}
	
	
}
