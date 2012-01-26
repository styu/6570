package edu.mit.printAtMIT.view;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.MapView.LayoutParams;
import com.google.android.maps.Projection;

import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.list.PrinterEntryItem;

/**
 * An overlay showing printer locations in PrinterMapActivity. Some of the code
 * is taken from
 * https://github.com/MIT-Mobile/MIT-Mobile-for-Android/blob/master
 * /src/edu/mit/mitmobile2/maps/MITItemizedOverlay.java
 */
public class PrinterItemizedOverlay extends ItemizedOverlay<PrinterOverlayItem> {

    private ArrayList<PrinterOverlayItem> mOverlayItems = new ArrayList<PrinterOverlayItem>();
    private Context mContext;
    private MapView mapView;
    private BalloonOverlayView balloonView;
    private View clickableRegion;
    private int mBubbleOffset = -5;

    public PrinterItemizedOverlay(Drawable defaultMarker, Context context,
            MapView mapView) {
        // center point at the bottom of the images points to gps coord
        super(boundCenterBottom(defaultMarker));
        mContext = context;
        this.mapView = mapView;
    }

    @Override
    protected PrinterOverlayItem createItem(int i) {
        return mOverlayItems.get(i);
    }

    @Override
    public int size() {
        return mOverlayItems.size();
    }

    public void addOverlay(PrinterOverlayItem overlay) {
        mOverlayItems.add(overlay);
        populate();
    }

    @Override
    protected boolean onTap(int index) {
        // return super.onTap(index);

        makeBalloon(mOverlayItems.get(index));
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e, MapView mapView) {
        mapView.removeView(balloonView);
        return super.onTouchEvent(e, mapView);

    }

    protected void makeBalloon(final PrinterOverlayItem item) {
        GeoPoint gp = item.getPoint();

        mapView.removeView(balloonView);

        balloonView = new BalloonOverlayView(mContext, mBubbleOffset);

        clickableRegion = balloonView.findViewById(R.id.balloon_inner_layout);

        clickableRegion.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                View l = ((View) v.getParent())
                        .findViewById(R.id.balloon_main_layout);
                Drawable d = l.getBackground();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    int[] states = { android.R.attr.state_pressed };
                    if (d.setState(states)) {
                        d.invalidateSelf();
                    }
                    return true;
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    int newStates[] = {};
                    if (d.setState(newStates)) {
                        d.invalidateSelf();
                    }

                    handleTap(item);

                    return true;
                } else {
                    return false;
                }
            }
        });

        balloonView.setData(item);

        MapView.LayoutParams params = new MapView.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gp, MapView.LayoutParams.BOTTOM_CENTER);

//        params.mode = MapView.LayoutParams.MODE_MAP;
        balloonView.setLayoutParams(params);

        balloonView.setVisibility(View.VISIBLE);


        mapView.getController().animateTo(gp);

        mapView.addView(balloonView);


    }

    /**
     * Balloon tap brings user back to printer info page.
     * @param item
     */
    protected void handleTap(PrinterOverlayItem item) {
        Intent intent = new Intent(mContext, PrinterInfoActivity.class);
        intent.putExtra("id", item.getParseId());
        mContext.startActivity(intent);

    }
}
