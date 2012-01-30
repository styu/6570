package edu.mit.printAtMIT.view;

import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;

import edu.mit.printAtMIT.R;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BalloonOverlayView extends FrameLayout {

    private LinearLayout layout;
    private TextView title;
    private TextView snippet;

    public BalloonOverlayView(Context context, int balloonBottomOffset) {
        super(context);
        setPadding(10, 0, 10, balloonBottomOffset);
        layout = new LinearLayout(context);
        layout.setVisibility(VISIBLE);

//         setupView(context, layout);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View v = inflater.inflate(R.layout.map_balloon, layout);
        LinearLayout inner = (LinearLayout) v.findViewById(R.id.balloon_inner_layout);
        inner.setPadding(15, 0, 5, 5);
        title = (TextView) v.findViewById(R.id.balloon_item_title);
        title.setPadding(0, 5, 10, 0);
        snippet = (TextView) v.findViewById(R.id.balloon_item_snippet);
        snippet.setPadding(0, 0, 10, 5);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, MapView.LayoutParams.BOTTOM_CENTER);
        
        params.gravity = Gravity.NO_GRAVITY;

        addView(layout, params);
    }

    public void setData(OverlayItem item) {

        layout.setVisibility(VISIBLE);

        if (item.getTitle() != null) {
            title.setVisibility(VISIBLE);
            title.setText(item.getTitle());
        } else {
            title.setVisibility(GONE);
        }

        if (item.getSnippet() != null) {
            snippet.setVisibility(VISIBLE);
            snippet.setText(item.getSnippet());
        } else {
            snippet.setVisibility(GONE);
        }

    }
}
