package programminglanguage;

import java.util.HashMap;

public class SymbolTable {
	private HashMap<String, Number> symbols1;
	private HashMap<String, CharValue> symbols2;
	private HashMap<String, StringValue> symbols3;
	
	public SymbolTable() {
		this.symbols1 = new HashMap<String, Number>();
		this.symbols2 = new HashMap<String, CharValue>();
		this.symbols3 = new HashMap<String, StringValue>();
	}

	public Number getNumberValue(String varName) {
		Number value = this.symbols1.get(varName);	
		return value;
	}
	
	public CharValue getCharValue(String varName) {
		CharValue value = this.symbols2.get(varName);	
		return value;
	}
	
	public StringValue getStringValue(String varName) {
		StringValue value = this.symbols3.get(varName);	
		return value;
	}
	
	public void setNumberValue(String varName, Number node) {
		this.symbols1.put(varName, node);
	}
	
	public void setCharValue(String varName, CharValue node) {
		this.symbols2.put(varName, node);
	}
	
	
	public void setStringValue(String varName, StringValue node) {
		this.symbols3.put(varName, node);
	}
	
	public void removeNumberValue(String varName) {
		this.symbols1.remove(varName);
	}
	
	public void removeCharValue(String varName) {
		this.symbols2.remove(varName);
	}
	
	public void removeStringValue(String varName) {
		this.symbols3.remove(varName);
	}
	
	public boolean isSymbol(String var) {
		return this.symbols3.containsKey(var);
	}
	public HashMap<String, StringValue> getSymbolTable() {
		return this.symbols3;
	}
}
