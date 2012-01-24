package edu.mit.printAtMIT.print;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.main.MainMenuActivity;
import edu.mit.printAtMIT.main.SettingsActivity;

/***
 * Show list of things to print: Downloads(for now), Images, Chat, Email Grid of
 * icons
 * 
 * Menu buttons: Settings About
 */
public class PrintMenuActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.print_menu);

		Button downloadsButton = (Button) findViewById(R.id.downloads_image);
		downloadsButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						PrintDownloadsActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.printmenu_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		Intent intent;
		switch (item.getItemId()) {
		case R.id.home:
			intent = new Intent(
					findViewById(android.R.id.content).getContext(),
					MainMenuActivity.class);
			startActivity(intent);
			return true;
		case R.id.setting:
			intent = new Intent(
					findViewById(android.R.id.content).getContext(),
					SettingsActivity.class);
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
}
