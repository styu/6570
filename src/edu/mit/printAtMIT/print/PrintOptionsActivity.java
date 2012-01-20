package edu.mit.printAtMIT.print;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.mit.printAtMIT.PrintAtMITActivity;
import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.list.EntryAdapter;
import edu.mit.printAtMIT.list.EntryItem;
import edu.mit.printAtMIT.list.Item;
import edu.mit.printAtMIT.list.SectionItem;

/**
 * User selects print options:
 *      bw/color
 *      copies
 * Print button
 * 
 * Menu Items:
 *      Settings
 *      About     
 */

public class PrintOptionsActivity extends ListActivity {
    PrintTask printTask;
    String fileLoc;
    String fileName;
    String queue;
    String userName;
    
    //Button btnStart;
    //TextView textStatus;
    
	ArrayList<Item> items = new ArrayList<Item>();
	private static final int ITEM_USERNAME = 1;
	private static final int ITEM_INKCOLOR = 3;
	private static final int ITEM_COPIES = 4;
	
    final String hostName = "mitprint.mit.edu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.print_options);
        //btnStart = (Button)findViewById(R.id.button01);
        
        // Show file chosen -- need to add options (bw/color, copies, change print name), then print
        Bundle extras = getIntent().getExtras(); 
        Log.d("INTENT", extras.toString());
        fileLoc = extras.getString("fileLoc");
        fileName = extras.getString("fileName");

        Toast.makeText(getApplicationContext(), fileLoc, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();
        
        SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
        userName = userSettings.getString(PrintAtMITActivity.USERNAME, "");
        queue = "bw";
        
        //Toast.makeText(getApplicationContext(), fileLoc + userName + hostName + queue, Toast.LENGTH_SHORT).show();

        //btnStart.setOnClickListener(btnStartListener);
        //textStatus = (TextView)findViewById(R.id.textStatus);
        
        //items.add(new SectionItem("User info"));
        items.add(new EntryItem("Kerberos Id", userSettings.getString(PrintAtMITActivity.USERNAME, ""), ITEM_USERNAME));
        
        //items.add(new SectionItem("Printer Preferences"));
        items.add(new EntryItem("Ink Color", userSettings.getString(PrintAtMITActivity.INKCOLOR, PrintAtMITActivity.BLACKWHITE), ITEM_INKCOLOR));
        items.add(new EntryItem("Copies", ""+userSettings.getInt(PrintAtMITActivity.COPIES, 1), ITEM_COPIES));
        
        EntryAdapter adapter = new EntryAdapter(this, items);
        
        setListAdapter(adapter);
        
        
    }
    
    private OnClickListener btnStartListener = new OnClickListener() {
        public void onClick(View v){
            //btnStart.setVisibility(View.INVISIBLE);
            printTask = new PrintTask();
            printTask.execute();
        }
    };
    
    public class PrintTask extends AsyncTask<Void, byte[], Boolean> {
    	private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");
            dialog = ProgressDialog.show(PrintOptionsActivity.this, "", 
                    "Sending print job", true);
        }

        @Override
        protected Boolean doInBackground(Void... params) { //This runs on a different thread
            boolean result = false;
            Lpr lpr = new Lpr();
            try {
            	File f = new File(fileLoc);
            	//Log.d("LPR", "file: " + f.toString());
                lpr.printFile(f, userName, hostName, queue, fileName);
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
            //btnStart.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Boolean result) {
        	dialog.dismiss();
            if (result) {
                Toast.makeText(getApplicationContext(), "Error sending, try again", Toast.LENGTH_SHORT).show();
                Log.i("AsyncTask", "onPostExecute: Completed with an Error.");
                //textStatus.setText("There was a connection error.");
            } else {
                Toast.makeText(getApplicationContext(), "Successfully sent", Toast.LENGTH_SHORT).show();
                Log.i("AsyncTask", "onPostExecute: Completed.");
                //textStatus.setText("task completed. successful!");
            }
            //btnStart.setVisibility(View.VISIBLE);
        }
    }
}