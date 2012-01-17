package edu.mit.printAtMIT.print;

import edu.mit.printAtMIT.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * User selects print options:
 *      bw/color
 *      copies
 * Print button
 * 
 * Menu Items:
 *      Settings
 *      About     
 */

public class PrintOptionsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_options);
    }
}
