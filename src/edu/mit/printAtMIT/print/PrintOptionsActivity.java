package edu.mit.printAtMIT.print;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
    NetworkTask networktask;
    Button btnStart;
    TextView textStatus;
    String fileLoc;
    String fileName;
    String queue;
    
    final String hostName = "mitprint.mit.edu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.print_options);
        Button btnStart = (Button)findViewById(R.id.button01);
        
        // Show file chosen -- need to add options, then print
        Bundle extras = getIntent().getExtras(); 
        String fileLoc = extras.getString("fileLoc");
        String fileName = extras.getString("fileName");
        
        Toast.makeText(getApplicationContext(), fileLoc, Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), fileName, Toast.LENGTH_SHORT).show();
        
        btnStart.setOnClickListener(btnStartListener);
        textStatus = (TextView)findViewById(R.id.textStatus);
        networktask = new NetworkTask(); //Create initial instance so SendDataToNetwork doesn't throw an error.

//        try {
//            lpr.printFile("res/drawable-hdpi/pdfs/go_club_midway.pdf", "mitprint.mit.edu", "bw", "bob");
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
    
    private OnClickListener btnStartListener = new OnClickListener() {
        public void onClick(View v){
            //btnStart.setVisibility(View.INVISIBLE);
            networktask = new NetworkTask(); //New instance of NetworkTask
            networktask.execute();
        }
    };
    
    public class NetworkTask extends AsyncTask<Void, byte[], Boolean> {
//        Socket nsocket; //Network Socket
//        InputStream nis; //Network Input Stream
//        OutputStream nos; //Network Output Stream

        @Override
        protected void onPreExecute() {
            Log.i("AsyncTask", "onPreExecute");
        }

        @Override
        protected Boolean doInBackground(Void... params) { //This runs on a different thread
            boolean result = false;
            Lpr lpr = new Lpr();

            try {
//                InputStream is = getResources().openRawResource(R.raw.go_club_midway);
//                File f = new File("/sdcard/download/possible.pdf");
//                f.createNewFile();
//                OutputStream os = new FileOutputStream(f);
//                byte buf[]=new byte[1024];
//                int len;
//                while((len=is.read(buf))>0) {
//                    os.write(buf,0,len);
//                    os.close();
//                    is.close();
//                }
            	//TODO: make this param. so we can input list of files to print
                //File f = new File("/sdcard/download/6570_notes.pdf");
            	boolean mExternalStorageAvailable = false;
            	boolean mExternalStorageWriteable = false;
            	String state = Environment.getExternalStorageState();
            	Log.d("LPR", "state: " + state.toString());
            	if (Environment.MEDIA_MOUNTED.equals(state)) {
            	    // We can read and write the media
            	    mExternalStorageAvailable = mExternalStorageWriteable = true;
            	} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            	    // We can only read the media
            	    mExternalStorageAvailable = true;
            	    mExternalStorageWriteable = false;
            	} else {
            	    // Something else is wrong. It may be one of many other states, but all we need
            	    //  to know is we can neither read nor write
            	    mExternalStorageAvailable = mExternalStorageWriteable = false;
            	}
            	
            	//File f = new File("/sdcard/download/designdoc.pdf");
            	File f = new File(fileLoc);
            	String userName = "sedris";
            	queue = "bw";
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

            
//            try {
//                Log.i("AsyncTask", "doInBackground: Creating socket");
//                
//                SocketAddress sockaddr = new InetSocketAddress("192.168.1.1", 80);
//                nsocket = new Socket();
//                nsocket.connect(sockaddr, 5000); //10 second connection timeout
//                if (nsocket.isConnected()) { 
//                    nis = nsocket.getInputStream();
//                    nos = nsocket.getOutputStream();
//                    Log.i("AsyncTask", "doInBackground: Socket created, streams assigned");
//                    Log.i("AsyncTask", "doInBackground: Waiting for inital data...");
//                    byte[] buffer = new byte[4096];
//                    int read = nis.read(buffer, 0, 4096); //This is blocking
//                    while(read != -1){
//                        byte[] tempdata = new byte[read];
//                        System.arraycopy(buffer, 0, tempdata, 0, read);
//                        publishProgress(tempdata);
//                        Log.i("AsyncTask", "doInBackground: Got some data");
//                        read = nis.read(buffer, 0, 4096); //This is blocking
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.i("AsyncTask", "doInBackground: IOException");
//                result = true;
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.i("AsyncTask", "doInBackground: Exception");
//                result = true;
//            } finally {
//                try {
//                    nis.close();
//                    nos.close();
//                    nsocket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Log.i("AsyncTask", "doInBackground: Finished");
//            }
//            return result;
            
        }

//        public void SendDataToNetwork(String cmd) { //You run this from the main thread.
//            try {
//                if (nsocket.isConnected()) {
//                    Log.i("AsyncTask", "SendDataToNetwork: Writing received message to socket");
//                    nos.write(cmd.getBytes());
//                } else {
//                    Log.i("AsyncTask", "SendDataToNetwork: Cannot send message. Socket is closed");
//                }
//            } catch (Exception e) {
//                Log.i("AsyncTask", "SendDataToNetwork: Message send failed. Caught an exception");
//            }
//        }

//        @Override
//        protected void onProgressUpdate(byte[]... values) {
//            if (values.length > 0) {
//                Log.i("AsyncTask", "onProgressUpdate: " + values[0].length + " bytes received.");
//                textStatus.setText(new String(values[0]));
//            }
//        }
        @Override
        protected void onCancelled() {
            Log.i("AsyncTask", "Cancelled.");
            btnStart.setVisibility(View.VISIBLE);
        }
        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Log.i("AsyncTask", "onPostExecute: Completed with an Error.");
                textStatus.setText("There was a connection error.");
            } else {
                Log.i("AsyncTask", "onPostExecute: Completed.");
                textStatus.setText("task completed. successful?");
            }
            btnStart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        networktask.cancel(true); //In case the task is currently running
    }
}