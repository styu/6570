package edu.mit.printAtMIT.main;

import com.parse.Parse;
import com.parse.ParseObject;

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
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	EditText textfield = (EditText) findViewById(R.id.entry);
            	if (!textfield.getText().toString().equals("")) {
	            	SharedPreferences settings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
	                SharedPreferences.Editor editor = settings.edit();
	                editor.putString(PrintAtMITActivity.USERNAME, textfield.getText().toString());
	                
	                // Commit the edits!
	                editor.commit();
	            	Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
	            	startActivity(intent);
            	}
            }
        });
    }
}
