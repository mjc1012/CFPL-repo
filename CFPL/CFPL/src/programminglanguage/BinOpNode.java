package programminglanguage;

public class BinOpNode {
	private NumberNode numberLeftNode;
	private Token operatorToken;
	private NumberNode numberRightNode;
	private BinOpNode binOpLeftNode;
	private BinOpNode binOpRightNode;
	private UnaryOpNode unaryOpLeftNode;
	private UnaryOpNode unaryOpRightNode;
	private VarAccessNode varAccessLeftNode;
	private VarAccessNode varAccessRightNode;
	private StringNode stringLeftNode;
	private StringNode stringRightNode;
	private CharNode charLeftNode;
	private CharNode charRightNode;
	Position positionStart;
	Position positionEnd;
	
	public BinOpNode(NumberNode leftNode, Token operatorToken, CharNode rightNode) {
		this.numberLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.charRightNode = rightNode;
		this.positionStart = this.numberLeftNode.getPositionStart();
		this.positionEnd = this.charRightNode.getPositionEnd();
	}
	
	public BinOpNode(BinOpNode leftNode, Token operatorToken, CharNode rightNode) {
		this.binOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.charRightNode = rightNode;
		this.positionStart = this.binOpLeftNode.getPositionStart();
		this.positionEnd = this.charRightNode.getPositionEnd();
	}
	
	public BinOpNode(UnaryOpNode leftNode, Token operatorToken, CharNode rightNode) {
		this.unaryOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.charRightNode = rightNode;
		this.positionStart = this.unaryOpLeftNode.getPositionStart();
		this.positionEnd = this.charRightNode.getPositionEnd();
	}
	
	public BinOpNode(VarAccessNode leftNode, Token operatorToken, CharNode rightNode) {
		this.varAccessLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.charRightNode = rightNode;
		this.positionStart = this.varAccessLeftNode.getPositionStart();
		this.positionEnd = this.charRightNode.getPositionEnd();
	}
	
	public BinOpNode(StringNode leftNode, Token operatorToken, CharNode rightNode) {
		this.stringLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.charRightNode = rightNode;
		this.positionStart = this.stringLeftNode.getPositionStart();
		this.positionEnd = this.charRightNode.getPositionEnd();
	}
	
	public BinOpNode(CharNode leftNode, Token operatorToken, CharNode rightNode) {
		this.charLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.charRightNode = rightNode;
		this.positionStart = this.charLeftNode.getPositionStart();
		this.positionEnd = this.charRightNode.getPositionEnd();
	}
	
	public BinOpNode(CharNode leftNode, Token operatorToken, StringNode rightNode) {
		this.charLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.stringRightNode = rightNode;
		this.positionStart = this.charLeftNode.getPositionStart();
		this.positionEnd = this.stringRightNode.getPositionEnd();
	}
	
	public BinOpNode(CharNode leftNode, Token operatorToken, UnaryOpNode rightNode) {
		this.charLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.unaryOpRightNode= rightNode;
		this.positionStart = this.charLeftNode.getPositionStart();
		this.positionEnd = this.unaryOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(CharNode leftNode, Token operatorToken, BinOpNode rightNode) {
		this.charLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.binOpRightNode= rightNode;
		this.positionStart = this.charLeftNode.getPositionStart();
		this.positionEnd = this.binOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(CharNode leftNode, Token operatorToken, NumberNode rightNode) {
		this.charLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.numberRightNode= rightNode;
		this.positionStart = this.charLeftNode.getPositionStart();
		this.positionEnd = this.numberRightNode.getPositionEnd();
	}
	
	public BinOpNode(CharNode leftNode, Token operatorToken, VarAccessNode rightNode) {
		this.charLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.varAccessRightNode = rightNode;
		this.positionStart = this.charLeftNode.getPositionStart();
		this.positionEnd = this.varAccessRightNode.getPositionEnd();
	}
	
	public BinOpNode(StringNode leftNode, Token operatorToken, VarAccessNode rightNode) {
		this.stringLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.varAccessRightNode = rightNode;
		this.positionStart = this.stringLeftNode.getPositionStart();
		this.positionEnd = this.varAccessRightNode.getPositionEnd();
	}
	
	public BinOpNode(StringNode leftNode, Token operatorToken, UnaryOpNode rightNode) {
		this.stringLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.unaryOpLeftNode = rightNode;
		this.positionStart = this.stringLeftNode.getPositionStart();
		this.positionEnd = this.unaryOpLeftNode.getPositionEnd();
	}
	
	public BinOpNode(StringNode leftNode, Token operatorToken, BinOpNode rightNode) {
		this.stringLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.binOpRightNode = rightNode;
		this.positionStart = this.stringLeftNode.getPositionStart();
		this.positionEnd = this.binOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(StringNode leftNode, Token operatorToken, NumberNode rightNode) {
		this.stringLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.numberRightNode = rightNode;
		this.positionStart = this.stringLeftNode.getPositionStart();
		this.positionEnd = this.numberRightNode.getPositionEnd();
	}
	
	public BinOpNode(UnaryOpNode leftNode, Token operatorToken, StringNode rightNode) {
		this.unaryOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.stringRightNode = rightNode;
		this.positionStart = this.unaryOpLeftNode.getPositionStart();
		this.positionEnd = this.stringRightNode.getPositionEnd();
	}
	
	public BinOpNode(NumberNode leftNode, Token operatorToken, StringNode rightNode) {
		this.numberLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.stringRightNode = rightNode;
		this.positionStart = this.numberLeftNode.getPositionStart();
		this.positionEnd = this.stringRightNode.getPositionEnd();
	}
	
	public BinOpNode(StringNode leftNode, Token operatorToken, StringNode rightNode) {
		this.stringLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.stringRightNode = rightNode;
		this.positionStart = this.stringLeftNode.getPositionStart();
		this.positionEnd = this.stringRightNode.getPositionEnd();
	}
	
	public BinOpNode(BinOpNode leftNode, Token operatorToken, StringNode rightNode) {
		this.binOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.stringRightNode = rightNode;
		this.positionStart = this.binOpLeftNode.getPositionStart();
		this.positionEnd = this.stringRightNode.getPositionEnd();
	}
	
	public BinOpNode(VarAccessNode leftNode, Token operatorToken, StringNode rightNode) {
		this.varAccessLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.stringRightNode = rightNode;
		this.positionStart = this.varAccessLeftNode.getPositionStart();
		this.positionEnd = this.stringRightNode.getPositionEnd();
	}
	
	public BinOpNode(NumberNode leftNode, Token operatorToken, NumberNode rightNode) {
		this.numberLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.numberRightNode = rightNode;
		this.positionStart = this.numberLeftNode.getPositionStart();
		this.positionEnd = this.numberRightNode.getPositionEnd();
	}
	
	public BinOpNode(NumberNode leftNode, Token operatorToken, UnaryOpNode rightNode) {
		this.numberLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.unaryOpRightNode = rightNode;
		this.positionStart = this.numberLeftNode.getPositionStart();
		this.positionEnd = this.unaryOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(NumberNode leftNode, Token operatorToken, BinOpNode rightNode) {
		this.numberLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.binOpRightNode = rightNode;
		this.positionStart = this.numberLeftNode.getPositionStart();
		this.positionEnd = this.binOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(NumberNode leftNode, Token operatorToken, VarAccessNode rightNode) {
		this.numberLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.varAccessRightNode = rightNode;
		this.positionStart = this.numberLeftNode.getPositionStart();
		this.positionEnd = this.varAccessRightNode.getPositionEnd();
	}
	
	public BinOpNode(UnaryOpNode leftNode, Token operatorToken, UnaryOpNode rightNode) {
		this.unaryOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.unaryOpRightNode = rightNode;
		this.positionStart = this.unaryOpLeftNode.getPositionStart();
		this.positionEnd = this.unaryOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(UnaryOpNode leftNode, Token operatorToken, NumberNode rightNode) {
		this.unaryOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.numberRightNode = rightNode;
		this.positionStart = this.unaryOpLeftNode.getPositionStart();
		this.positionEnd = this.numberRightNode.getPositionEnd();
	}
	
	public BinOpNode(UnaryOpNode leftNode, Token operatorToken, BinOpNode rightNode) {
		this.unaryOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.binOpRightNode = rightNode;
		this.positionStart = this.unaryOpLeftNode.getPositionStart();
		this.positionEnd = this.binOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(UnaryOpNode leftNode, Token operatorToken, VarAccessNode rightNode) {
		this.unaryOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.varAccessRightNode = rightNode;
		this.positionStart = this.numberLeftNode.getPositionStart();
		this.positionEnd = this.varAccessRightNode.getPositionEnd();
	}
	
	public BinOpNode(BinOpNode leftNode, Token operatorToken, NumberNode rightNode) {
		this.binOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.numberRightNode = rightNode;
		this.positionStart = this.binOpLeftNode.getPositionStart();
		this.positionEnd = this.numberRightNode.getPositionEnd();
	}
	
	public BinOpNode(BinOpNode leftNode, Token operatorToken, UnaryOpNode rightNode) {
		this.binOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.unaryOpRightNode = rightNode;
		this.positionStart = this.binOpLeftNode.getPositionStart();
		this.positionEnd = this.unaryOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(BinOpNode leftNode, Token operatorToken, BinOpNode rightNode) {
		this.binOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.binOpRightNode = rightNode;
		this.positionStart = this.binOpLeftNode.getPositionStart();
		this.positionEnd = this.binOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(BinOpNode leftNode, Token operatorToken, VarAccessNode rightNode) {
		this.binOpLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.varAccessRightNode = rightNode;
		this.positionStart = this.binOpLeftNode.getPositionStart();
		this.positionEnd = this.varAccessRightNode.getPositionEnd();
	}
	
	public BinOpNode(VarAccessNode leftNode, Token operatorToken, NumberNode rightNode) {
		this.varAccessLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.numberRightNode = rightNode;
		this.positionStart = this.varAccessLeftNode.getPositionStart();
		this.positionEnd = this.numberRightNode.getPositionEnd();
	}
	
	public BinOpNode(VarAccessNode leftNode, Token operatorToken, UnaryOpNode rightNode) {
		this.varAccessLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.unaryOpRightNode = rightNode;
		this.positionStart = this.varAccessLeftNode.getPositionStart();
		this.positionEnd = this.unaryOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(VarAccessNode leftNode, Token operatorToken, BinOpNode rightNode) {
		this.varAccessLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.binOpRightNode = rightNode;
		this.positionStart = this.varAccessLeftNode.getPositionStart();
		this.positionEnd = this.binOpRightNode.getPositionEnd();
	}
	
	public BinOpNode(VarAccessNode leftNode, Token operatorToken, VarAccessNode rightNode) {
		this.varAccessLeftNode = leftNode;
		this.operatorToken = operatorToken;
		this.varAccessRightNode = rightNode;
		this.positionStart = this.varAccessLeftNode.getPositionStart();
		this.positionEnd = this.varAccessRightNode.getPositionEnd();
	}
	
	public NumberNode getNumberLeftNode() {
		return this.numberLeftNode;
	}
	
	public NumberNode getNumberRightNode() {
		return this.numberRightNode;
	}
	
	public BinOpNode getBinOpLeftNode() {
		return this.binOpLeftNode;
	}
	
	public BinOpNode getBinOpRightNode() {
		return this.binOpRightNode;
	}
	
	public UnaryOpNode getUnaryOpLeftNode() {
		return this.unaryOpLeftNode;
	}
	
	public UnaryOpNode getUnaryOpRightNode() {
		return this.unaryOpRightNode;
	}
	
	public VarAccessNode getVarAccessRightNode() {
		return this.varAccessRightNode;
	}
	
	public VarAccessNode getVarAccessLeftNode() {
		return this.varAccessLeftNode;
	}
	
	public StringNode getStringLeftNode() {
		return this.stringLeftNode;
	}
	
	public StringNode getStringRightNode() {
		return this.stringRightNode;
	}
	
	public CharNode getCharLeftNode() {
		return this.charLeftNode;
	}
	
	public CharNode getCharRightNode() {
		return this.charRightNode;
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
