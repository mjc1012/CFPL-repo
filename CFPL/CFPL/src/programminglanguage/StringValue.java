package programminglanguage;

public class StringValue {
	private String stringValue;
	private Position positionStart;
	private Position positionEnd;
	
	public StringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	
	public void setPosition(Position positionStart, Position positionEnd) {
		this.positionStart = positionStart;
		this.positionEnd = positionEnd;
	}
	
	public String getStringValue() {
		return this.stringValue;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
	
	public StringValue concatenate(StringValue other) {
		StringValue s = new StringValue(this.stringValue + other.stringValue);
		return s;
	}
	
	public String toString() {
		return "" + this.stringValue;
	}
}
