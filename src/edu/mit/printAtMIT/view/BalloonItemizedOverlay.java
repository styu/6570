package edu.mit.printAtMIT.view;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import edu.mit.printAtMIT.R;


public class BalloonItemizedOverlay extends ItemizedOverlay<OverlayItem>{

    private Context context;
    private MapView mapview;
    private BalloonOverlayView balloonView;
    private View clickRegion;

    private int viewoffset;
    private final MapController controller;
    private OnTouchListener balloonOnTouchListener = new OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // TODO Auto-generated method stub
            Toast.makeText(context, "balloon on touch", Toast.LENGTH_SHORT).show();
            return false;
        }
        
    };
    public BalloonItemizedOverlay(Drawable defaultMarker, MapView mapview, Context context) {
//        super(boundCenterBottom(defaultMarker));
        super(defaultMarker);
        this.mapview = mapview;
        viewoffset = 0;
        controller = mapview.getController();
        this.context = context;
    }

    @Override
    protected OverlayItem createItem(int i) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }
    
    /**
     * Set the horizontal distance between the marker and the bottom of the information
     * balloon. The default is 0 which works well for center bounded markers. If your
     * marker is center-bottom bounded, call this before adding overlay items to ensure
     * the balloon hovers exactly above the marker. 
     * 
     * @param pixels - The padding between the center point and the bottom of the
     * information balloon.
     */
    public void setBalloonBottomOffset(int pixels) {
        viewoffset = pixels;
    }
    public int getBalloonBottomOffset() {
        return viewoffset;
    }
    
    /* (non-Javadoc)
     * @see com.google.android.maps.ItemizedOverlay#onTap(int)
     */
    @Override
    protected final boolean onTap(int index) {
        
//        currentFocussedIndex = index;
//        currentFocussedItem = createItem(index);
//        
//        createAndDisplayBalloonOverlay();
//        
//        mc.animateTo(currentFocussedItem.getPoint());
        // go to printerinfo page
        return true;
    }

//    /**
//     * Creates and displays the balloon overlay by recycling the current 
//     * balloon or by inflating it from xml. 
//     * @return true if the balloon was recycled false otherwise 
//     */
//    private boolean createAndDisplayBalloonOverlay(){
//        boolean isRecycled;
//        if (balloonView == null) {
////            balloonView = createBalloonOverlayView();
//            balloonView = new BalloonOverlayView(mapview.getContext(), viewoffset);
//            clickRegion = (View) balloonView.findViewById(R.id.balloon_inner_layout);
//            clickRegion.setOnTouchListener(balloonOnTouchListener);
//            isRecycled = false;
//        } else {
//            isRecycled = true;
//        }
//    
//        balloonView.setVisibility(View.GONE);
//        
//        List<Overlay> mapOverlays = mapview.getOverlays();
//        if (mapOverlays.size() > 1) {
//            hideOtherBalloons(mapOverlays);
//        }
//        
//        if (currentFocussedItem != null)
//            balloonView.setData(currentFocussedItem);
//        
//        GeoPoint point = currentFocussedItem.getPoint();
//        MapView.LayoutParams params = new MapView.LayoutParams(
//                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, point,
//                MapView.LayoutParams.BOTTOM_CENTER);
//        params.mode = MapView.LayoutParams.MODE_MAP;
//        
//        balloonView.setVisibility(View.VISIBLE);
//        
//        if (isRecycled) {
//            balloonView.setLayoutParams(params);
//        } else {
//            mapView.addView(balloonView, params);
//        }
//        
//        return isRecycled;
//    }
}
