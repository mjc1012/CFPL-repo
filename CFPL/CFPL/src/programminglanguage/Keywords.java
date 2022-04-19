package programminglanguage;

public enum Keywords {
	VAR,
	START,
	STOP,
	AS,
	INT,
	FLOAT,
	CHAR,
	BOOL,
	INPUT,
	OUTPUT;
	public static boolean findByName(String name) {
	    for (Keywords direction : values()) {
	        if (direction.name().equals(name)) {
	        	return true;
	        }
	    }
	    return false;
	}
}
