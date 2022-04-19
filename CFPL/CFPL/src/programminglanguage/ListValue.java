package programminglanguage;

import java.util.ArrayList;

public class ListValue {
	private ArrayList<RuntimeResult> list;
	private Position positionStart;
	private Position positionEnd;
	
	public ListValue(ArrayList<RuntimeResult> list) {
		this.list = list;
	}
	
	public void setPosition(Position positionStart, Position positionEnd) {
		this.positionStart = positionStart;
		this.positionEnd = positionEnd;
	}
	
	public ArrayList<RuntimeResult> getListValue() {
		return this.list;
	}
	
	public Position getPositionStart(){
		return this.positionStart;
	}
	
	public Position getPositionEnd(){
		return this.positionEnd;
	}
}
