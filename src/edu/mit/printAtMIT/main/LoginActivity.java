package edu.mit.printAtMIT.main;

import edu.mit.printAtMIT.R;

import edu.mit.printAtMIT.PrintAtMITActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;


/**
 * This is the first activity a user will see upon first opening this app.
 * User inputs Kerberos ID.
 */
public class LoginActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        
        EditText textfield = (EditText) findViewById(R.id.entry);
        Button button01 = (Button) findViewById(R.id.continue_button);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	EditText textfield = (EditText) findViewById(R.id.entry);
            	if (!textfield.getText().toString().equals("")) {
	            	SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
	                SharedPreferences.Editor editor = userSettings.edit();
	                editor.putString(PrintAtMITActivity.USERNAME, textfield.getText().toString());
	                editor.putString(PrintAtMITActivity.INKCOLOR, PrintAtMITActivity.BLACKWHITE);
	                editor.putInt(PrintAtMITActivity.COPIES, 1);
	                
	                // Commit the edits!
	                editor.commit();
	            	Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
	            	startActivity(intent);
            	}
            }
        });
    }
}
