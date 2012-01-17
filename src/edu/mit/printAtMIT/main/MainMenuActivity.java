package edu.mit.printAtMIT.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
        
        Button button01 = (Button) findViewById(R.id.button01);
        Button button02 = (Button) findViewById(R.id.button02);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	Intent intent = new Intent(view.getContext(), PrintMenuActivity.class);
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
}
