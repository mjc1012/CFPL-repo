package programminglanguage;

public class Number {
	private Integer intValue;
	private Float floatValue;
	private Position positionStart;
	private Position positionEnd;
	
	public Number(Integer intValue) {
		this.intValue = intValue;
	}
	
	public Number(Float floatValue) {
		this.floatValue = floatValue;
	}
	
	public void setPosition(Position positionStart, Position positionEnd) {
		this.positionStart = positionStart;
		this.positionEnd = positionEnd;
	}
	
	public Number addedTo(Number other) {
		Number n = null;
		if(this.intValue != null && other.intValue != null) {
			n = new Number(this.intValue + other.intValue);
		}
		else if(this.intValue != null && other.floatValue != null) {
			n = new Number(this.intValue + other.floatValue);
		}
		else if(this.floatValue != null && other.floatValue != null) {
			n = new Number(this.floatValue + other.floatValue);
		}
		else if(this.floatValue != null && other.intValue != null) {
			n = new Number(this.floatValue + other.intValue);
		}
		return n;
	}
	
	public Number subtractedTo(Number other) {
		Number n = null;
		if(this.intValue != null && other.intValue != null) {
			n = new Number(this.intValue - other.intValue);
		}
		else if(this.intValue != null && other.floatValue != null) {
			n = new Number(this.intValue - other.floatValue);
		}
		else if(this.floatValue != null && other.floatValue != null) {
			n = new Number(this.floatValue - other.floatValue);
		}
		else if(this.floatValue != null && other.intValue != null) {
			n = new Number(this.floatValue - other.intValue);
		}
		return n;
	}

	public Number multipliedTo(Number other) {
		Number n = null;
		if(this.intValue != null && other.intValue != null) {
			n = new Number(this.intValue * other.intValue);
		}
		else if(this.intValue != null && other.floatValue != null) {
			n = new Number(this.intValue * other.floatValue);
		}
		else if(this.floatValue != null && other.floatValue != null) {
			n = new Number(this.floatValue * other.floatValue);
		}
		else if(this.floatValue != null && other.intValue != null) {
			n = new Number(this.floatValue * other.intValue);
		}
		return n;
	}
	public Number dividedTo(Number other) {
		Number n = null;
		if(this.intValue != null && other.intValue != null) {
			n = new Number(this.intValue / other.intValue);
		}
		else if(this.intValue != null && other.floatValue != null) {
			n = new Number(this.intValue / other.floatValue);
		}
		else if(this.floatValue != null && other.floatValue != null) {
			n = new Number(this.floatValue / other.floatValue);
		}
		else if(this.floatValue != null && other.intValue != null) {
			n = new Number(this.floatValue / other.intValue);
		}
		return n;
	}
	
	public Number moduloTo(Number other) {
		Number n = null;
		if(this.intValue != null && other.intValue != null) {
			n = new Number(this.intValue % other.intValue);
		}
		else if(this.intValue != null && other.floatValue != null) {
			n = new Number(this.intValue % other.floatValue);
		}
		else if(this.floatValue != null && other.floatValue != null) {
			n = new Number(this.floatValue % other.floatValue);
		}
		else if(this.floatValue != null && other.intValue != null) {
			n = new Number(this.floatValue % other.intValue);
		}
		return n;
	}
	
	public Integer getIntValue() {
		return this.intValue;
	}
	
	public Float getFloatValue() {
		return this.floatValue;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
	
	public String toString() {
		if(this.intValue != null) {
			return ""+ this.intValue;
		}
		else if(this.floatValue != null){
			return "" + this.floatValue;
		}
		return "null";
	}
}
