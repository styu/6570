package edu.mit.printAtMIT.main;

import com.parse.Parse;

import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.PrintAtMITActivity;
import edu.mit.printAtMIT.print.PrintMenuActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer", "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT"); 
        
        EditText username = (EditText) findViewById(R.id.entry);
        SharedPreferences settings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
        username.setText(settings.getString(PrintAtMITActivity.USERNAME, ""));
        
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	Intent intent = new Intent(view.getContext(), PrintMenuActivity.class);
            	intent.putExtra("activity", "mainmenu");
            	startActivity(intent);
            }
        });
    }
}
