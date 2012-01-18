package edu.mit.printAtMIT.print;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
import edu.mit.printAtMIT.R;

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
        
        // Show file chosen -- need to add options, then print
        Bundle extras = getIntent().getExtras(); 
        String fileLoc = extras.getString("fileLoc");
        String fileName = extras.getString("fileName");
        
        Toast.makeText(getApplicationContext(), fileLoc, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();

    }
}
