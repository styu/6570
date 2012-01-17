package edu.mit.printAtMIT.print;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import edu.mit.printAtMIT.R;

/**
 * Lists files from Downloads folder
 * 
 * Menu Items:
 *      Settings
 *      About
 */
public class PrintDownloadsActivity extends FileViewActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_downloads);
        
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	Intent intent = new Intent(view.getContext(), PrintOptionsActivity.class);
            	startActivity(intent);
            }
        });
    }
}
