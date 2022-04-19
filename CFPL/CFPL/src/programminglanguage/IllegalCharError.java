package programminglanguage;

public class IllegalCharError extends Error{

	public IllegalCharError(Position positionStart, Position positionEnd, String details) {
		super(positionStart, positionEnd,"Illegal Character", details);
	}

}
