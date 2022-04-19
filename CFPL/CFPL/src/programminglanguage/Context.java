package programminglanguage;

public class Context {
	private SymbolTable symbolTable;
	private DeclaredVar declaredVar;
	
	public Context(SymbolTable symbolTable) {
		this.symbolTable = symbolTable;
	}
	
	public Context(DeclaredVar declaredVar) {
		this.declaredVar = declaredVar;
	}
	
	public SymbolTable getSymbolTable() {
		return this.symbolTable;
	}
	
	public DeclaredVar getDeclaredVar() {
		return this.declaredVar;
	}
}
