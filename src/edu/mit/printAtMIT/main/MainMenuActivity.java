package edu.mit.printAtMIT.main;

import com.parse.Parse;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.print.PrintMenuActivity;
import edu.mit.printAtMIT.view.PrinterListActivity;

/**
 * Show "print" and "view printers" buttons.
 * Menu buttons:
 *      Setting
 *      About
 */
public class MainMenuActivity extends Activity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer", "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT"); 
        
        Button button01 = (Button) findViewById(R.id.button01);
        Button button02 = (Button) findViewById(R.id.button02);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	Intent intent = new Intent(view.getContext(), PrintMenuActivity.class);
            	intent.putExtra("activity", "mainmenu");
            	startActivity(intent);
            }
        });
        
        button02.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	Intent intent = new Intent(view.getContext(), PrinterListActivity.class);
            	startActivity(intent);
            }
        });
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.setting:
        	Intent intent = new Intent(findViewById(android.R.id.content).getContext(), SettingsActivity.class);
        	startActivity(intent);
            return true;
        case R.id.about:
        	Dialog dialog = new Dialog(this);

			dialog.setContentView(R.layout.about_dialog);
			dialog.setTitle("About");
			dialog.show();
            super.onOptionsItemSelected(item);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    
    @Override
    public void onBackPressed() {
       Intent intent = getIntent();
       String activity = intent.getStringExtra("activity");
       if (activity.equals("start")) {
    	   return;
       }
       else if (activity.equals("home_button")) {
           return;
       }
       else {
    	   super.onBackPressed();
       }
    }

}
