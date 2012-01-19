package edu.mit.printAtMIT.list;


public class EntryItem implements Item{

	public final String title;
	public final String subtitle;
	public final int id;

	public EntryItem(String title, String subtitle, int id) {
		this.title = title;
		this.subtitle = subtitle;
		this.id = id;
	}
	
	@Override
	public boolean isSection() {
		return false;
	}

}
