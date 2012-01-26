package edu.mit.printAtMIT.view;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.google.android.maps.MapView.LayoutParams;

import edu.mit.printAtMIT.R;

/**
 * An overlay showing printer locations in PrinterMapActivity
 */
public class PrinterItemizedOverlay extends ItemizedOverlay<OverlayItem> {

    private ArrayList<OverlayItem> mOverlayItems = new ArrayList<OverlayItem>();
    private Context mContext;
    private MapView mapView;
    private BalloonOverlayView balloonView;
    private View clickableRegion;
    private int mBubbleOffset = -5;
    private boolean balloonVisible = false;
    private int selectedIndex = -1;

    public PrinterItemizedOverlay(Drawable defaultMarker, Context context,
            MapView mapView) {
        // center point at the bottom of the images points to gps coord
        super(boundCenterBottom(defaultMarker));
        mContext = context;
        this.mapView = mapView;
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
        // return super.onTap(index);

        makeBalloon(mOverlayItems.get(index));
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e, MapView mapView) {
        // TODO Auto-generated method stub
        mapView.removeView(balloonView);
        return super.onTouchEvent(e, mapView);

    }

    protected void makeBalloon(final OverlayItem item) {
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
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, gp,
                MapView.LayoutParams.BOTTOM_CENTER);

        params.mode = MapView.LayoutParams.MODE_MAP;

        balloonView.setVisibility(View.VISIBLE);

        balloonView.setLayoutParams(params);

        mapView.getController().animateTo(gp);

        mapView.addView(balloonView);
    }

    protected void handleTap(OverlayItem item) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setTitle(item.getTitle());
        dialog.setMessage(item.getSnippet());
        dialog.show();

    }
}
