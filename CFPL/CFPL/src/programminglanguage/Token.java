package programminglanguage;

public class Token {
	private Integer intTokenValue;
	private Float floatTokenValue;
	private String stringTokenValue;
	private Character charTokenValue;
	private TokenType tokenType;
	Position positionStart;
	Position positionEnd;
	
	public Token(int intTokenValue, TokenType tokenType, Position positionStart, Position positionEnd) {
		this.tokenType = tokenType;
		this.intTokenValue = intTokenValue;
		this.positionStart = positionStart.copy();
		this.positionEnd = positionEnd;
	}
	
	
	public Token(float floatTokenValue, TokenType tokenType, Position positionStart, Position positionEnd) {
		this.tokenType = tokenType;
		this.floatTokenValue = floatTokenValue;
		this.positionStart = positionStart.copy();
		this.positionEnd = positionEnd;
	}
	
	public Token(char charTokenValue, TokenType tokenType, Position positionStart, Position positionEnd) {
		this.tokenType = tokenType;
		this.charTokenValue = charTokenValue;
		this.positionStart = positionStart.copy();
		this.positionEnd = positionEnd;
	}
	
	
	public Token(String stringTokenValue, TokenType tokenType, Position positionStart, Position positionEnd) {
		this.tokenType = tokenType;
		this.stringTokenValue = stringTokenValue;
		this.positionStart = positionStart.copy();
		this.positionEnd = positionEnd;
	}
	
	public Token(String stringTokenValue, TokenType tokenType, Position positionStart) {
		this.tokenType = tokenType;
		this.stringTokenValue = stringTokenValue;
		this.positionStart = positionStart.copy();
		this.positionEnd = positionStart.copy();
		this.positionEnd.advance();
	}
	
	
	public Token(TokenType tokenType, Position positionStart, Position positionEnd) {
		this.tokenType = tokenType;
		this.positionStart = positionStart.copy();
		this.positionEnd = positionEnd;
	}
	
	public Token(TokenType tokenType, Position positionStart) {
		this.tokenType = tokenType;
		this.positionStart = positionStart.copy();
		this.positionEnd = positionStart.copy();
		this.positionEnd.advance();
	}
	
	public TokenType getType(){
		return this.tokenType;
	}
	
	public Integer getIntValue(){
		return this.intTokenValue;
	}
	
	public Float getFloatValue(){
		return this.floatTokenValue;
	}
	
	public String getStringValue(){
		return this.stringTokenValue;
	}
	
	public Character getCharValue(){
		return this.charTokenValue;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
	
	public boolean matches(TokenType tokenType, String stringTokenValue) {
		if(this.tokenType == tokenType && this.stringTokenValue.equals(stringTokenValue)) {
			return true;
		}
		return false;
	}
	public String toString() {
		if(this.intTokenValue != null)
			return "" + this.intTokenValue;
		else if(this.floatTokenValue != null)
			return "" + this.floatTokenValue;
		else if(this.stringTokenValue != null)
			return "" + this.stringTokenValue;
		else if(this.charTokenValue != null)
			return "" + this.charTokenValue;
		
		return "";
	}
}
