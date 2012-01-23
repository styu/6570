package edu.mit.printAtMIT.view;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
 * An overlay showing printer locations in PrinterMapActivity
 */
public class PrinterItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> mOverlayItems = new ArrayList<OverlayItem>();
    private Context mContext;

    public PrinterItemizedOverlay(Drawable defaultMarker, Context context) {
        //center point at the bottom of the images points to gps coord
        super(boundCenterBottom(defaultMarker));
        mContext = context;
    }

    @Override
    protected OverlayItem createItem(int i) {
        return mOverlayItems.get(i);
    }

    @Override
    public int size() {
        return mOverlayItems.size();
    }

    public void addOverlay(OverlayItem overlay) {
        mOverlayItems.add(overlay);
        populate();
    }

    @Override
    protected boolean onTap(int index) {
        // TODO Auto-generated method stub
//        return super.onTap(index);
        OverlayItem item = mOverlayItems.get(index);
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());
        dialog.show();
        
        return true;
    }
    
}
