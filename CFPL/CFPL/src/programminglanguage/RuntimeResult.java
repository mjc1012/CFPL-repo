package programminglanguage;

public class RuntimeResult {
	private Number number;
	private StringValue stringValue;
	private CharValue charValue;
	private ListValue listValue;
	private NumberNode numberNode;
	private UnaryOpNode unaryOpNode;
	private BinOpNode binOpNode;
	private RuntimeError error;
	
	public RuntimeResult() {}
	
	public Number registerNumber(RuntimeResult result) {
		if(result.getError() != null) {
			this.error = result.getError();
		}
		return result.getNumber();
	}
	
	public CharValue registerCharValue(RuntimeResult result) {
		if(result.getError() != null) {
			this.error = result.getError();
		}
		return result.getCharValue();
	}
	
	public StringValue registerStringValue(RuntimeResult result) {
		if(result.getError() != null) {
			this.error = result.getError();
		}
		return result.getStringValue();
	}
	
	public Number getNumber() {
		return this.number;
	}
	
	public StringValue getStringValue() {
		return this.stringValue;
	}
	
	public NumberNode getNumberNode() {
		return this.numberNode;
	}
	
	public UnaryOpNode getUnaryOpNode() {
		return this.unaryOpNode;
	}
	
	public BinOpNode getBinOpNode() {
		return this.binOpNode;
	}
	
	public CharValue getCharValue() {
		return this.charValue;
	}
	
	public ListValue getListValue() { // added change
		return this.listValue;
	}
	
	public RuntimeError getError() {
		return this.error;
	}
	
	public void success(Number number) {
		this.number = number;
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
	
	public void success(CharValue node) {
		this.charValue = node;
	}
	
	public void success(StringValue node) {
		this.stringValue = node;
	}
	
	public void success(ListValue node) {
		this.listValue = node;
	}
	
	public void failure(RuntimeError error) {
		this.error = error;
	}
}
