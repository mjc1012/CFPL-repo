package programminglanguage;

public class Error {
	String errorName;
	String details;
	Position positionStart;
	Position positionEnd;
	
	public Error(Position positionStart, Position positionEnd, String errorName, String details) {
		this.positionStart = positionStart;
		this.positionEnd = positionEnd; 
		this.errorName = errorName;
		this.details = details;
	}
	
	public String toString() {
		return this.errorName + ": " + this.details + "\nFile " + this.positionStart.getFileName() + ", line " + (this.positionStart.getLine() + 1) + ", column " + (this.positionStart.getColumn() + 1);
	}
}
