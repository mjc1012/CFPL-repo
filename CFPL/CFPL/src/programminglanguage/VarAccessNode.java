package programminglanguage;

public class VarAccessNode {
	private Token varName;
	Position positionStart;
	Position positionEnd;
	
	public VarAccessNode(Token varName) {
		this.varName = varName;
		this.positionStart = this.varName.getPositionStart();
		this.positionEnd = this.varName.getPositionEnd();
	}
	
	public Token getVarName() {
		return this.varName;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
}
