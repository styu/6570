package edu.mit.printAtMIT.list;


public class ButtonItem implements Item{

	public final String title;
	public final int id;

	public ButtonItem(String title,  int id) {
		this.title = title;
		this.id = id;
	}
	
	public boolean isSection() {
		return false;
	}
	
	public boolean isButton() {
		return true;
	}

}
