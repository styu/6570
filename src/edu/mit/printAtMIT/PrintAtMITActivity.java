package edu.mit.printAtMIT;

import edu.mit.printAtMIT.main.LoginActivity;
import edu.mit.printAtMIT.main.MainMenuActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.content.Intent;

public class PrintAtMITActivity extends Activity {
	
	public static final String PREFS_NAME = "user_preferences";
	public static final String USERNAME = "kerberosId";
	public static final String INKCOLOR = "inkcolor";
	public static final String COPIES = "copies";
	
	public static final String BLACKWHITE = "Black and White";
	public static final String COLOR = "Color";
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        
        if (settings.getString(USERNAME, "").equals("")) {
        	Intent intent = new Intent(this, LoginActivity.class);
        	intent.putExtra("activity", "start");
        	startActivity(intent);
        }
        else {
        	Intent intent = new Intent(this, MainMenuActivity.class);
        	intent.putExtra("activity", "start");
        	startActivity(intent);
        }
    }
    
    private void startLoginActivity() {
        
    }
    
    private void startMainMenuActivity() {
        
    }
}