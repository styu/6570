package edu.mit.printAtMIT.main;

import com.parse.Parse;

import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.PrintAtMITActivity;
import edu.mit.printAtMIT.print.PrintMenuActivity;
import android.app.Activity;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.PopupMenu;


import java.util.ArrayList;
import edu.mit.printAtMIT.list.*;

/**
 * Users can reset their kerberos ID here
 * 
 * Menu Items:
 *      Home
 */
public class SettingsActivity extends ListActivity {
	
	ArrayList<Item> items = new ArrayList<Item>();
	private static final int ITEM_USERNAME = 1;
	private static final int ITEM_INKCOLOR = 3;
	private static final int ITEM_COPIES = 4;
	private static boolean registered = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.settings);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer", "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT"); 
        
        //EditText username = (EditText) findViewById(R.id.entry);
        SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
        //username.setText(settings.getString(PrintAtMITActivity.USERNAME, ""));
        items.add(new SectionItem("User info"));
        items.add(new EntryItem("Change Kerberos Id", userSettings.getString(PrintAtMITActivity.USERNAME, ""), ITEM_USERNAME));
        
        items.add(new SectionItem("Printer Preferences"));
        items.add(new EntryItem("Ink Color", userSettings.getString(PrintAtMITActivity.INKCOLOR, PrintAtMITActivity.BLACKWHITE), ITEM_INKCOLOR));
        items.add(new EntryItem("Copies", ""+userSettings.getInt(PrintAtMITActivity.COPIES, 1), ITEM_COPIES));
        
        EntryAdapter adapter = new EntryAdapter(this, items);
        
        setListAdapter(adapter);
    }
    
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);
      menu.setHeaderTitle("Change Kerberos Id");
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.username_menu, menu);
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	if(!items.get(position).isSection()){
    		
    		//EntryItem item = (EntryItem)items.get(position);
    		switch(position) {
    		case ITEM_USERNAME:
    			final Dialog dialog = new Dialog(this);

    			dialog.setContentView(R.menu.username_menu);
    			dialog.setTitle("Change Kerberos Id");
    			dialog.show();
    			
    			Button saveButton = (Button) dialog.findViewById(R.id.save);
    			EditText textfield = (EditText) dialog.findViewById(R.id.change_username);
    			textfield.setImeOptions(EditorInfo.IME_ACTION_DONE);
    	        
    	        saveButton.setOnClickListener(new View.OnClickListener() {

    	            public void onClick(View view) {
    	            	SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
    	            	EditText textfield = (EditText) dialog.findViewById(R.id.change_username);
    	            	SharedPreferences.Editor editor = userSettings.edit();
    	                editor.putString(PrintAtMITActivity.USERNAME, textfield.getText().toString());
    	                editor.commit();
    	      
    	                items.set(ITEM_USERNAME, new EntryItem("Change Kerberos Id", textfield.getText().toString(), ITEM_USERNAME));
    	                EntryAdapter adapter = new EntryAdapter(view.getContext(), items);
    	                setListAdapter(adapter);
    	                dialog.dismiss();
    	            }
    	        });
    	        
        		return;
    		case ITEM_INKCOLOR: Toast.makeText(this, "select ink color here", Toast.LENGTH_SHORT).show(); break;
    		case ITEM_COPIES: Toast.makeText(this, "change number of copies here", Toast.LENGTH_SHORT).show(); break;
    		default: Toast.makeText(this, "herp derp", Toast.LENGTH_SHORT).show(); break;
    		}
    		//Toast.makeText(this, "You clicked " + item.title , Toast.LENGTH_SHORT).show();

    		
    	}
    	
    	super.onListItemClick(l, v, position, id);
    }
        /*
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	Intent intent = new Intent(view.getContext(), PrintMenuActivity.class);
            	intent.putExtra("activity", "mainmenu");
            	startActivity(intent);
            }
        });*/
}
