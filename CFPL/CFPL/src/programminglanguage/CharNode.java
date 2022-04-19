package programminglanguage;

public class CharNode {
	private Token token;
	Position positionStart;
	Position positionEnd;
	
	public CharNode(Token token) {
		this.token = token;
		this.positionStart = this.token.getPositionStart();
		this.positionEnd = this.token.getPositionEnd();
	}
	
	public Token getToken() {
		return this.token;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
}
