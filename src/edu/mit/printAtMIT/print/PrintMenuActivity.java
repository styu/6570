package edu.mit.printAtMIT.print;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import edu.mit.printAtMIT.R;


/***
 * Show list of things to print: Downloads(for now), Images, Chat, Email
 * Grid of icons
 * 
 * Menu buttons:
 *      Settings
 *      About    
 */
public class PrintMenuActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_menu);
        
         ImageView v = (ImageView) findViewById(R.id.downloads_image);
        v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(view.getContext(), PrintDownloadsActivity.class);
            	startActivity(intent);
			}
        });
        
        /*Button downloadsButton = (Button) findViewById(R.id.button01);
        downloadsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	Intent intent = new Intent(view.getContext(), PrintDownloadsActivity.class);
            	startActivity(intent);
            }
        });*/
    }
}
