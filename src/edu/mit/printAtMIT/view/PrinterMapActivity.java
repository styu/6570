package edu.mit.printAtMIT.view;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.mit.printAtMIT.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;


/**
 * Shows map of printers, color coded by status
 * 
 * Menu Items:
 *      Home About Refresh
 */
public class PrinterMapActivity extends MapActivity {
    public static final int MIT_CENTER_LAT = 42359425;
    public static final int MIT_CENTER_LONG = -71094735;
    LinearLayout linearLayout;
    MapView mapView;
    List<Overlay> mapOverlays;
    Drawable drawable;
    PrinterItemizedOverlay itemizedOverlay;
    private List<ParseObject> mPrinters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPrinters = new ArrayList<ParseObject>();
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        setContentView(R.layout.map);
        Bundle extras = getIntent().getExtras(); 
        
        Log.i("printerlist", "creating ParseQuery PrintersData");
        ParseQuery query = new ParseQuery("PrintersData");
        Log.i("printerlist", "created ParseQuery PrintersData");
        boolean allView = extras.getBoolean("allPrinterView", false);
        
        int centerLat = MIT_CENTER_LAT;
        int centerLong = MIT_CENTER_LONG;
        if (allView) {
            setPrinterList(query);
        }
        else {
            String id = extras.getString("id");
            ParseObject printer;
            try {
                printer = query.get(id);
                centerLat = Integer.parseInt(printer.getString("latitude"));
                centerLong = Integer.parseInt(printer.getString("longitude"));
                mPrinters.add(printer);
            } catch (ParseException e) {
                Log.e("PrinterMapActivity", "Parse query.get(id) FAIL");
                e.printStackTrace();
            }
        }
        
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        itemizedOverlay = new PrinterItemizedOverlay(drawable, this);
        MapController controller = mapView.getController();
        
        // make mapview start at MIT if allView, else animate to selected printer loc
        if (allView) {
            //TODO: set center at your current location
            controller.setCenter(new GeoPoint(centerLat, centerLong));
            controller.setZoom(17);

        }
        else {
            controller.setZoom(17);
            controller.animateTo(new GeoPoint(centerLat, centerLong));
        }
        
        // add printer overlayitems to map
        for (ParseObject printer : mPrinters) {
            GeoPoint point = new GeoPoint(Integer.parseInt(printer
                    .getString("latitude")), Integer.parseInt(printer
                    .getString("longitude")));

            OverlayItem item = new OverlayItem(point,
                    printer.getString("printerName"),
                    printer.getString("location"));
            
            itemizedOverlay.addOverlay(item);
        }

        mapOverlays.add(itemizedOverlay);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }

    /**
     * Makes request to Parse to retrieve list of all printers.
     * Sets mPrinters to that list.
     */
    private void setPrinterList(ParseQuery query) {

        try {
            List<ParseObject> objects = query.find();
            for (ParseObject o : objects) {
                mPrinters.add(o);
            }
        } catch (ParseException e1) {
            Log.e("printerList", "query.find() FAILED");
        }

    }
}
