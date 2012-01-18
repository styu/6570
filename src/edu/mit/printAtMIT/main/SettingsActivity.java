package edu.mit.printAtMIT.main;

import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.PrintAtMITActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Users can reset their kerberos ID here
 * 
 * Menu Items:
 *      Home
 */
public class SettingsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        
        EditText username = (EditText) findViewById(R.id.entry);
        SharedPreferences settings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
        username.setText(settings.getString(PrintAtMITActivity.USERNAME, ""));
    }
}
