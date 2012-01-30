package edu.mit.printAtMIT.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import edu.mit.printAtMIT.PrintAtMITActivity;
import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.list.ButtonItem;
import edu.mit.printAtMIT.list.EntryAdapter;
import edu.mit.printAtMIT.list.EntryItem;
import edu.mit.printAtMIT.list.Item;
import edu.mit.printAtMIT.list.SectionItem;
import edu.mit.printAtMIT.print.Lpr;

/**
 * Users can reset their kerberos ID here
 * 
 * Menu Items:
 *      Home
 */
public class SettingsActivity extends ListActivity {
    private String fileLoc;
    private String fileName;
    private String queue;
    private String userName;
    private int numCopies;
    private static final String hostName = "mitprint.mit.edu";
	
	ArrayList<Item> items = new ArrayList<Item>();
	private static final int ITEM_USERNAME = 1;
	private static final int ITEM_INKCOLOR = 3;
	private static final int ITEM_COPIES = 4;
	private static final int ITEM_PRINT_BUTTON = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
        items.add(new SectionItem("User info"));
        items.add(new EntryItem("Change Kerberos Id", userSettings.getString(PrintAtMITActivity.USERNAME, ""), ITEM_USERNAME));
        
        items.add(new SectionItem("Printer Preferences"));
        items.add(new EntryItem("Ink Color", userSettings.getString(PrintAtMITActivity.INKCOLOR, PrintAtMITActivity.BLACKWHITE), ITEM_INKCOLOR));
        items.add(new EntryItem("Copies", ""+userSettings.getInt(PrintAtMITActivity.COPIES, 1), ITEM_COPIES));
        items.add(new ButtonItem("Print Test Page", ITEM_PRINT_BUTTON));

        EntryAdapter adapter = new EntryAdapter(this, items);
        
        setListAdapter(adapter);
        
        // set print options
        userName = userSettings.getString(PrintAtMITActivity.USERNAME, "");
        numCopies = userSettings.getInt(PrintAtMITActivity.COPIES, 1);
        if (userSettings.getString(PrintAtMITActivity.INKCOLOR, PrintAtMITActivity.BLACKWHITE).equals("Color"))
        	queue = "color";
        else
        	queue = "bw";
    }
    
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.home:
        	Intent intent = new Intent(findViewById(android.R.id.content).getContext(), MainMenuActivity.class);
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
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
      super.onCreateContextMenu(menu, v, menuInfo);
      menu.setHeaderTitle("Ink Color Settings");
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.inkcolor_menu, menu);
      //checks the correct option based on saved user preference
      //default is black and white
	    SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
	    String color = userSettings.getString(PrintAtMITActivity.INKCOLOR, PrintAtMITActivity.BLACKWHITE);
	    if (color.equals(PrintAtMITActivity.COLOR)) {
	    	MenuItem item = (MenuItem) menu.findItem(R.id.color);
	    	item.setChecked(true);
	    }
	    else {
	    	MenuItem item = (MenuItem) menu.findItem(R.id.bw);
	    	item.setChecked(true);
	    }
    }
    
    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
    	SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
    	SharedPreferences.Editor editor = userSettings.edit();
    	EntryAdapter adapter;
    	//changes user preference based on what user has selected
       switch(item.getItemId())
       {
       case R.id.bw:
    	   queue = "bw";
    	   editor.putString(PrintAtMITActivity.INKCOLOR, PrintAtMITActivity.BLACKWHITE);
    	   items.set(ITEM_INKCOLOR, new EntryItem("Ink Color", PrintAtMITActivity.BLACKWHITE, ITEM_INKCOLOR));
    	   editor.commit();
           adapter = new EntryAdapter(this, items);
           setListAdapter(adapter);
          return true;
       case R.id.color:
    	  queue = "color";
          editor.putString(PrintAtMITActivity.INKCOLOR, PrintAtMITActivity.COLOR);
          items.set(ITEM_INKCOLOR, new EntryItem("Ink Color", PrintAtMITActivity.COLOR, ITEM_INKCOLOR));
          editor.commit();
          adapter = new EntryAdapter(this, items);
          setListAdapter(adapter);
          return true;
       default:
          return super.onContextItemSelected(item);
       }
    }
    
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	
    	if(!items.get(position).isSection()){
    		
    		switch(position) {
    		//popup dialog appears for username
    		//saves user-inputted username
    		case ITEM_USERNAME:
    			final Dialog dialog = new Dialog(this);

    			dialog.setContentView(R.layout.username_dialog);
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
    	                
    	                userName = textfield.getText().toString();
    	      
    	                items.set(ITEM_USERNAME, new EntryItem("Change Kerberos Id", textfield.getText().toString(), ITEM_USERNAME));
    	                EntryAdapter adapter = new EntryAdapter(view.getContext(), items);
    	                setListAdapter(adapter);
    	                dialog.dismiss();
    	            }
    	        });
    	        
        		return;
        	//context menu appears for ink color
    		case ITEM_INKCOLOR: 
    			registerForContextMenu( v ); 
    		    v.setLongClickable(false); 
    		    this.openContextMenu(v);
    		   
    			break;
    		case ITEM_COPIES:
    			final View view = v;
    			
    			final SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
      		  	final SharedPreferences.Editor editor = userSettings.edit();
      		  
                final EditText copy = new EditText(this);
                copy.setInputType(InputType.TYPE_CLASS_NUMBER);
                
        		AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        		builder.setMessage("Number of copies:")
          	         .setCancelable(false)
          	         .setView(copy)
          	         .setPositiveButton("Save", new DialogInterface.OnClickListener() {
          	             public void onClick(DialogInterface dialog, int id) {
          	            	 
	        	    		 int copies = userSettings.getInt(PrintAtMITActivity.COPIES, 1);
	        	    		 String text = copy.getText().toString();
	        	        	 copies = (text.equals("") || text.equals("0")) ? copies : Integer.parseInt(copy.getText().toString());
	        	             
	        	        	 editor.putInt(PrintAtMITActivity.COPIES, copies);
	        	          	 editor.commit();
	        	          	 numCopies = copies;
          	       	      
           	                items.set(ITEM_COPIES, new EntryItem("Copies", "" + copies, ITEM_COPIES));

           	                EntryAdapter adapter = new EntryAdapter(view.getContext(), items);
           	                setListAdapter(adapter);
           	                
           	                dialog.dismiss();
          	             }
          	         })
          	         .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
          	             public void onClick(DialogInterface dialog, int id) {
          	                  dialog.cancel();
          	             }
          	         });
          	  AlertDialog alert = builder.create();
          	  alert.show();
          	  // have soft keyboard automatically pop up
          	  alert.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

          	  break;
    		case ITEM_PRINT_BUTTON:
    			String intStorageDirectory = getFilesDir().toString();
    	        File f = new File(intStorageDirectory, "printAtMIT_testPage.pdf");
    	        
		        InputStream in = null;
		        OutputStream out = null;
		        try {
		          in = getAssets().open("printAtMIT_testPage.pdf");
		          out = new FileOutputStream(f.getPath());
		          copyFile(in, out);
		          in.close();
		          in = null;
		          out.flush();
		          out.close();
		          out = null;
		        } catch(Exception e) {
		            Log.e("AssetTransfer", e.getMessage());
		        }       
    	        
    	        fileLoc = f.getPath();
    	        fileName = f.getName();
    			PrintTask printTask = new PrintTask();
                printTask.execute();
                break;
    		default: Toast.makeText(this, "herp derp", Toast.LENGTH_SHORT).show(); break;
    		}
    		
    	}
    	
    	super.onListItemClick(l, v, position, id);
    }
    
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
          out.write(buffer, 0, read);
        }
    }
    
    public class PrintTask extends AsyncTask<Void, Void, Boolean> {
    	private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");
            dialog = ProgressDialog.show(SettingsActivity.this, "", 
                    "Sending print job...", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) { //This runs on a different thread
            boolean result = false;
            Lpr lpr = new Lpr();
            try {
            	File f = new File(fileLoc);
                lpr.printFile(f, userName, hostName, queue, fileName, numCopies);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();      
                Log.i("AsyncTask", "doInBackground: IOException");
                result = true;
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i("AsyncTask", "doInBackground: Exception");
                result = true;
            }
            return result;
        }
        
        @Override
        protected void onCancelled() {
            Log.i("AsyncTask", "Cancelled.");
        }
        @Override
        protected void onPostExecute(Boolean result) {
        	dialog.dismiss();
			String intStorageDirectory = getFilesDir().toString();
	        File f = new File(intStorageDirectory, "printAtMIT_testPage.pdf");
            f.delete();

            if (result) {
            	finish();
                Toast.makeText(getApplicationContext(), "Error sending, try again", Toast.LENGTH_SHORT).show();
                Log.i("AsyncTask", "onPostExecute: Completed with an Error.");
            } else {
            	finish();
                Toast.makeText(getApplicationContext(), "Successfully sent", Toast.LENGTH_SHORT).show();
                Log.i("AsyncTask", "onPostExecute: Completed.");
            }
        }
    }
}