package programminglanguage;

import java.util.ArrayList;

public class ParseResult {
	private InvalidSyntaxError error;
	private NumberNode numberNode;
	private CharNode charNode;
	private StringNode stringNode;
	private BinOpNode binOpNode;
	private UnaryOpNode unaryOpNode;
	private VarAccessNode varAccessNode;
	private ListNode listNode;
	private ArrayList<VarAssignNode> multipleVarAssignNode;
	
	public ParseResult() { this.multipleVarAssignNode = new ArrayList<VarAssignNode>();}
	
	public InvalidSyntaxError getError() {
		return this.error;
	}
	
	public NumberNode getNumberNode() {
		return this.numberNode;
	}
	
	public BinOpNode getBinOpNode() {
		return this.binOpNode;
	}
	
	public void removeBinOpNode() {
		this.binOpNode = null;
	}
	
	public UnaryOpNode getUnaryOpNode() {
		return this.unaryOpNode;
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
	
	public ListNode getListNode() {
		return this.listNode;
	}
	
	public ArrayList<VarAssignNode> getVarAssignNode() {
		return this.multipleVarAssignNode;
	}
	
	public NumberNode numberNodeRegister(ParseResult result) {
		if(result.getError() != null) {
			this.error = result.getError();
		}
		return result.getNumberNode();
	}

	public BinOpNode binOpNodeRegister(ParseResult result) {
		if(result.getError() != null) {
			this.error = result.getError();
		}
		return result.getBinOpNode();
	}
	
	public void success(NumberNode node) {
		this.numberNode = node;
	}
	
	public void success(UnaryOpNode node) {
		this.unaryOpNode = node;
	}
	
	public void success(BinOpNode node) {
		this.binOpNode = node;
	}
	
	public void success(StringNode node) {
		this.stringNode = node;
	}
	
	public void success(CharNode node) {
		this.charNode = node;
	}
	
	public void success(VarAccessNode node) {
		this.varAccessNode = node;
	}
	
	public void success(ListNode node) {
		this.listNode = node;
	}
	
	public void success(VarAssignNode node) {
		this.multipleVarAssignNode.add(node);
	}
	
	public void failure(InvalidSyntaxError error) {
		this.error = error;
	}
}
