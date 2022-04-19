package programminglanguage;

public class RuntimeError extends Error{

	public RuntimeError(Position positionStart, Position positionEnd, String details) {
		super(positionStart, positionEnd, "Runtime Error", details);
		// TODO Auto-generated constructor stub
	}

}
