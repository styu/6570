package edu.mit.printAtMIT.list;

import android.util.Log;

/**
 * Represents a printer list item.
 * Contains printer name, location, status
 */
public class PrinterEntryItem implements Item {

    public final String parseId;
    public final String printerName;
    public final String location;
    public int status;
    
    public static final String BUSY = "Busy";
    public static final String READY = "Ready";
    public static final String ERROR = "Error";
    public static final String UNKNOWN = "Unknown";
    
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

    public String getStatusString() {
        int status = this.status;
        String string = "";
        switch(status) {
            case 0: string = PrinterEntryItem.READY; break; //green
            case 1: string = PrinterEntryItem.BUSY; break; //yellow
            case 2: string = PrinterEntryItem.ERROR; break; //error
            case 3: string = PrinterEntryItem.UNKNOWN; break; //grey
            default: Log.e("PrinterEntryItem", "Invalid printer status, thos hast problems"); break;
        }
        return string;
    }
}
