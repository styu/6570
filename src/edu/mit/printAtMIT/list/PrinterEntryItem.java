package edu.mit.printAtMIT.list;

/**
 * Represents a printer list item.
 * Contains printer name, location, status
 */
public class PrinterEntryItem implements Item {

    public final String parseId;
    public final String printerName;
    public final String location;
    public int status;
    
    public PrinterEntryItem(String parseId, String printer, String location, int status) {
        this.parseId = parseId;
        this.printerName = printer;
        this.location = location;
        this.status = status;
        
    }
    @Override
    public boolean isSection() {
        return false;
    }

    @Override
    public boolean isButton() {
        return false;
    }
    @Override
    public boolean isPrinterEntry() {
        // TODO Auto-generated method stub
        return true;
    }

}
