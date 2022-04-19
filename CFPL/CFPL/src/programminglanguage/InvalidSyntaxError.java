package programminglanguage;

public class InvalidSyntaxError extends Error {

	public InvalidSyntaxError(Position positionStart, Position positionEnd, String details) {
		super(positionStart, positionEnd, "Invalid Syntax", details);
		// TODO Auto-generated constructor stub
	}

}
