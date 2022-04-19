package programminglanguage;

import java.util.ArrayList;

public class ListNode {
	private ArrayList<ParseResult> list = new ArrayList<ParseResult>();
	Position positionStart;
	Position positionEnd;
	
	public ListNode(ArrayList<ParseResult> list, Position start, Position end) {
		this.list = list;
		this.positionStart = start;
		this.positionEnd = end;
	}
	
	public ArrayList<ParseResult> getList(){
		return this.list;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
}
