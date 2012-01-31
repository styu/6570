package edu.mit.printAtMIT.view;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.main.MainMenuActivity;
import edu.mit.printAtMIT.main.SettingsActivity;

/***
 * Show list of things to print: Downloads(for now), Images, Chat, Email Grid of
 * icons
 * 
 * Menu buttons: Settings About
 */
public class PrintListMenuActivity extends Activity {
	
	public static final String LIST_TYPE = "listtype";
	public static final String LIST_ALL = "All";
	public static final String LIST_DORM = "Dorm";
	public static final String LIST_CAMPUS = "Campus";
	public static final String LIST_FAVORITE = "Favorite";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.print_list_menu);

		Button dormListButton = (Button) findViewById(R.id.dorm_list_button);
		Button campusListButton = (Button) findViewById(R.id.campus_list_button);
		Button favoritesListButton = (Button) findViewById(R.id.favorite_list_button);
		Button mapButton = (Button) findViewById(R.id.map_button);
		Button completeListButton = (Button) findViewById(R.id.complete_list_button);
		
		dormListButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						PrinterListActivity.class);
				intent.putExtra(PrintListMenuActivity.LIST_TYPE, PrintListMenuActivity.LIST_DORM);
				startActivity(intent);
			}
		});
		
		campusListButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						PrinterListActivity.class);
				intent.putExtra(PrintListMenuActivity.LIST_TYPE, PrintListMenuActivity.LIST_CAMPUS);
				startActivity(intent);
			}
		});
		
		favoritesListButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						PrinterListActivity.class);
				intent.putExtra(PrintListMenuActivity.LIST_TYPE, PrintListMenuActivity.LIST_FAVORITE);
				startActivity(intent);
			}
		});
		
		completeListButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
						PrinterListActivity.class);
				intent.putExtra(PrintListMenuActivity.LIST_TYPE, PrintListMenuActivity.LIST_ALL);
				startActivity(intent);
			}
		});
		
		mapButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent intent = new Intent(view.getContext(),
                        PrinterMapActivity.class);
                intent.putExtra("allPrinterView", true);
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
			showAboutDialog();
			super.onOptionsItemSelected(item);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void showAboutDialog() {
		showDialog(0);
	}
	@Override
	protected Dialog onCreateDialog(int id) {
		final Dialog dialog = new Dialog(this);
    	dialog.setContentView(R.layout.about_dialog);
    	dialog.setTitle("About");
    	TextView tv = (TextView) dialog.findViewById(R.id.about_text);
    	Linkify.addLinks(tv, Linkify.ALL);
    	tv.setMovementMethod(LinkMovementMethod.getInstance());
		return dialog;
	}
}
