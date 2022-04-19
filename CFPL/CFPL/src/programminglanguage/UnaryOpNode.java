package programminglanguage;

public class UnaryOpNode { // added change
	private Token operatorToken;
	private UnaryOpNode unaryOpNode;
	private NumberNode numberNode;
	private VarAccessNode varAccessNode;
	private BinOpNode binOpNode;
	Position positionStart;
	Position positionEnd;
	
	public UnaryOpNode(Token operatorToken, BinOpNode binOpNode) {
		this.operatorToken = operatorToken;
		this.binOpNode = binOpNode;
		this.positionStart = this.operatorToken.getPositionStart();
		this.positionEnd = this.binOpNode.getPositionEnd();
	}
	
	public UnaryOpNode(Token operatorToken, NumberNode numberNode) {
		this.operatorToken = operatorToken;
		this.numberNode = numberNode;
		this.positionStart = this.operatorToken.getPositionStart();
		this.positionEnd = this.numberNode.getPositionEnd();
	}
	
	public UnaryOpNode(Token operatorToken, UnaryOpNode unaryOpNode) {
		this.operatorToken = operatorToken;
		this.unaryOpNode = unaryOpNode;
		this.positionStart = this.operatorToken.getPositionStart();
		this.positionEnd = this.unaryOpNode.getPositionEnd();
	}
	
	public UnaryOpNode(Token operatorToken, VarAccessNode varAccessNode) {
		this.operatorToken = operatorToken;
		this.varAccessNode = varAccessNode;
		this.positionStart = this.operatorToken.getPositionStart();
		this.positionEnd = this.varAccessNode.getPositionEnd();
	}
	
	public NumberNode getNumberNode() {
		return this.numberNode;
	}
	
	public UnaryOpNode getUnaryOpNode() {
		return this.unaryOpNode;
	}
	
	public VarAccessNode getVarAccessNode() {
		return this.varAccessNode;
	}
	
	public BinOpNode getBinOpNode() {
		return this.binOpNode;
	}
	
	public Token getOperatorToken() {
		return this.operatorToken;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
}
