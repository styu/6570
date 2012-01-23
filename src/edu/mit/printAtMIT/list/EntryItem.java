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
	
	public boolean isSection() {
		return false;
	}
	
	public boolean isButton() {
		return false;
	}

    @Override
    public boolean isPrinterEntry() {
        // TODO Auto-generated method stub
        return false;
    }

}
