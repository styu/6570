package edu.mit.printAtMIT.view;

import com.parse.Parse;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

import edu.mit.printAtMIT.R;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;

import java.util.List;
/**
 * Shows map of printers, color coded by status
 * 
 * Menu Items:
 *      
 */
public class PrinterMapActivity extends MapActivity {
    LinearLayout linearLayout;
    MapView mapView;
    List<Overlay> mapOverlays;
    Drawable drawable;
    PrinterItemizedOverlay itemizedOverlay;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        setContentView(R.layout.map);
        mapView = (MapView) findViewById(R.id.mapview);
        mapView.setBuiltInZoomControls(true);
        mapOverlays = mapView.getOverlays();
        drawable = this.getResources().getDrawable(R.drawable.androidmarker);
        itemizedOverlay = new PrinterItemizedOverlay(drawable);
        
        GeoPoint point = new GeoPoint(19240000,-99120000);
        OverlayItem overlayitem = new OverlayItem(point, "", "");
        itemizedOverlay.addOverlay(overlayitem);
        GeoPoint point2 = new GeoPoint(35410000, 139460000);
        OverlayItem overlayitem2 = new OverlayItem(point2, "", "");
        itemizedOverlay.addOverlay(overlayitem2);
        mapOverlays.add(itemizedOverlay);
    }

    @Override
    protected boolean isRouteDisplayed() {
        return false;
    }
}
