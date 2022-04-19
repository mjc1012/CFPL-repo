package programminglanguage;

public class CharValue {
	private char charValue;
	private Position positionStart;
	private Position positionEnd;
	
	public CharValue(char charValue) {
		this.charValue = charValue;
	}
	
	public void setPosition(Position positionStart, Position positionEnd) {
		this.positionStart = positionStart;
		this.positionEnd = positionEnd;
	}
	
	public char getStringValue() {
		return this.charValue;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
	
	public String toString() {
		return "" + this.charValue;
	}
}
