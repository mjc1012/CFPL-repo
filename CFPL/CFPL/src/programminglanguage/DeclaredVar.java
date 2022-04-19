package programminglanguage;

import java.util.HashMap;
public class DeclaredVar {
	private HashMap<String, Token> vars;
	
	public DeclaredVar() {
		this.vars = new HashMap<String, Token>();
	}
	
	public boolean isDeclaredVar(String var) {
		return this.vars.containsKey(var);
	}
	
	public Token getDataType(String varName) {
		Token value = this.vars.get(varName);	
		return value;
	}
	
	public void setVar(String varName, Token dataType) {
		this.vars.put(varName, dataType);
	}
	
	public HashMap<String, Token> getDeclaredVar() {
		return this.vars;
	}
	
}