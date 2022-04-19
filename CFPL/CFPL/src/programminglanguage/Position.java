package programminglanguage;

public class Position {
	private int index;
	private int line;
	private int column;
	private String fileName;
	private String filetText;
	
	public Position(int index, int line, int column, String fileName, String filetText) {
		this.index = index;
		this.line = line;
		this.column = column;
		this.fileName = fileName;
		this.filetText = filetText;
	}
	
	public void advance(char current_char) {
		this.index += 1;
		this.column += 1;
		
		if(current_char == '\n') {
			this.line += 1;
			this.column = 0;
		}
	}
	
	public void advance() {
		this.index += 1;
		this.column += 1;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	public int getLine() {
		return this.line;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public String getFileText() {
		return this.filetText;
	}
	
	public Position copy() {
		return new Position(this.index, this.line, this.column, this.fileName, this.filetText);
	}
}
