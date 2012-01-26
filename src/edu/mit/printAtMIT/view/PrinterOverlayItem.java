package edu.mit.printAtMIT.view;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class PrinterOverlayItem extends OverlayItem {

    private String parseId;
    
    public PrinterOverlayItem(GeoPoint point, String title, String snippet, String parseId) {
        super(point, title, snippet);
        this.parseId = parseId;
    }
    
    public String getParseId() {
        return parseId;
    }

}
