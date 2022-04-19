package programminglanguage;

import java.util.ArrayList;

public class VarAssignNode {
	private Token varName;
	private ArrayList<Token> inputTokens; // added change
	private NumberNode numberNode;
	private Token dataType;
	private BinOpNode binOpNode;
	private UnaryOpNode unaryOpNode;
	private CharNode charNode;
	private StringNode stringNode;
	private VarAccessNode varAccessNode;
	Position positionStart;
	Position positionEnd;
	
	public VarAssignNode(Token varNameToken,  ArrayList<Token> inputTokens) { // added change
		this.varName = varNameToken;
		this.inputTokens = inputTokens;
		this.positionStart = this.varName.getPositionStart();
		this.positionEnd = this.varName.getPositionEnd();
	}
	
	public VarAssignNode(Token varNameToken, BinOpNode node) {
		this.varName = varNameToken;
		this.binOpNode = node;
		this.positionStart = this.varName.getPositionStart();
		this.positionEnd = this.binOpNode.getPositionEnd();
	}
	
	public VarAssignNode(Token varNameToken, NumberNode node) {
		this.varName = varNameToken;
		this.numberNode = node;
		this.positionStart = this.varName.getPositionStart();
		this.positionEnd = this.numberNode.getPositionEnd();
	}
	
	public VarAssignNode(Token varNameToken, UnaryOpNode node) {
		this.varName = varNameToken;
		this.unaryOpNode = node;
		this.positionStart = this.varName.getPositionStart();
		this.positionEnd = this.unaryOpNode.getPositionEnd();
	}
	
	public VarAssignNode(Token varNameToken, CharNode node) {
		this.varName = varNameToken;
		this.charNode = node;
		this.positionStart = this.varName.getPositionStart();
		this.positionEnd = this.charNode.getPositionEnd();
	}
	
	public VarAssignNode(Token varNameToken, StringNode node) {
		this.varName = varNameToken;
		this.stringNode = node;
		this.positionStart = this.varName.getPositionStart();
		this.positionEnd = this.stringNode.getPositionEnd();
	}
	
	public VarAssignNode(Token varNameToken, VarAccessNode node) {
		this.varName = varNameToken;
		this.varAccessNode = node;
		this.positionStart = this.varName.getPositionStart();
		this.positionEnd = this.varAccessNode.getPositionEnd();
	}
	
	public Token getVarName() {
		return this.varName;
	}
	
	public ArrayList<Token> getInputTokens() {
		return this.inputTokens;
	}
	
	public BinOpNode getBinOpNode() {
		return this.binOpNode;
	}
	
	public UnaryOpNode getUnaryOpNode() {
		return this.unaryOpNode;
	}
	
	public NumberNode getNumberNode() {
		return this.numberNode;
	}
	
	public Token getDataType() {
		return this.dataType;
	}
	
	public CharNode getCharNode() {
		return this.charNode;
	}
	
	public StringNode getStringNode() {
		return this.stringNode;
	}
	
	public VarAccessNode getVarAccessNode() {
		return this.varAccessNode;
	}
}
