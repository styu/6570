package edu.mit.printAtMIT;

import com.parse.Parse;


import edu.mit.printAtMIT.main.LoginActivity;
import edu.mit.printAtMIT.main.MainMenuActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;

public class PrintAtMITActivity extends Activity {
	
	public static final String PREFS_NAME = "user_preferences";
	public static final String USERNAME = "kerberosId";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer", "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT"); 

        setContentView(R.layout.main);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        if (settings.getString(USERNAME, "").equals("")) {
        	Intent intent = new Intent(this, LoginActivity.class);
        	startActivity(intent);
        }
        else {
        	Intent intent = new Intent(this, MainMenuActivity.class);
        	startActivity(intent);
        }
    }
}