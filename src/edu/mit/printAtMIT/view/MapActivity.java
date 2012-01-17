package edu.mit.printAtMIT.view;

import edu.mit.printAtMIT.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * Shows map of printers, color coded by status
 * 
 * Menu Items:
 *      
 */
public class MapActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
    }
}
