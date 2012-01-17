package edu.mit.printAtMIT.view;

import edu.mit.printAtMIT.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * Shows printer info; name, location, status, picture, relevant stuff
 */
public class PrinterInfoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.printer_info);
    }
}
