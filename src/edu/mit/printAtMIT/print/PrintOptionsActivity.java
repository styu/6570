package edu.mit.printAtMIT.print;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
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

public class PrintOptionsActivity extends Activity {
    PrintTask printTask;
    String fileLoc;
    String fileName;
    String queue;
    String userName;
    
    Button btnStart;
    TextView textStatus;
    
    final String hostName = "mitprint.mit.edu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_options);
        btnStart = (Button)findViewById(R.id.button01);
        
        // Show file chosen -- need to add options, then print
        Bundle extras = getIntent().getExtras(); 
        fileLoc = extras.getString("fileLoc");
        fileName = extras.getString("fileName");
        
        Toast.makeText(getApplicationContext(), fileLoc, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();
        
        SharedPreferences userSettings = getSharedPreferences(PrintAtMITActivity.PREFS_NAME, MODE_PRIVATE);
        userName = userSettings.getString(PrintAtMITActivity.USERNAME, "");
        queue = "bw";
        
        //Toast.makeText(getApplicationContext(), fileLoc + userName + hostName + queue, Toast.LENGTH_SHORT).show();

        btnStart.setOnClickListener(btnStartListener);
        textStatus = (TextView)findViewById(R.id.textStatus);
    }
    
    private OnClickListener btnStartListener = new OnClickListener() {
        public void onClick(View v){
            btnStart.setVisibility(View.INVISIBLE);
            printTask = new PrintTask();
            printTask.execute();
        }
    };
    
    public class PrintTask extends AsyncTask<Void, byte[], Boolean> {
        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... params) { //This runs on a different thread
            boolean result = false;
            Lpr lpr = new Lpr();
            try {
            	File f = new File(fileLoc);

            	Log.d("LPR", "file: " + f.toString());
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
            if (result) {
                Log.i("AsyncTask", "onPostExecute: Completed with an Error.");
                textStatus.setText("There was a connection error.");
            } else {
                Log.i("AsyncTask", "onPostExecute: Completed.");
                textStatus.setText("task completed. successful!");
            }
            btnStart.setVisibility(View.VISIBLE);
        }
    }

/*    @Override
    protected void onDestroy() {
        super.onDestroy();
        //printTask.cancel(true); //In case the task is currently running
    }*/
}