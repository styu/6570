package edu.mit.printAtMIT.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.Map;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.list.EntryAdapter;
import edu.mit.printAtMIT.list.Item;
import edu.mit.printAtMIT.list.PrinterEntryItem;
import edu.mit.printAtMIT.list.SectionItem;

/**
 * Lists all the printers from database. Shows name, location, status from each
 * printer List of favorite printers on top, then list of all printers
 * 
 * Menu Item: Settings About Home Refresh
 * 
 * Context Menu Items: Favorite, Info, MapView
 */

public class PrinterListActivity extends ListActivity {
    public static final String TAG = "PrinterListActivity";
    private static final String REFRESH_ERROR = "Error connecting to network, please try again later";
    private static final int REFRESH_ID = Menu.FIRST;
    
    private Map<String, PrinterEntryItem> curr_map = new HashMap<String, PrinterEntryItem>();
    private Map<String, PrinterEntryItem> all_map = new HashMap<String, PrinterEntryItem>();
    private PrintersDbAdapter mDbAdapter;
    private PrinterComparator comparator = new PrinterComparator();
    private final Context self = PrinterListActivity.this;

    // Comparator to sort printers alphabetically
    public class PrinterComparator implements Comparator<PrinterEntryItem> {

        @Override
        public int compare(PrinterEntryItem item1, PrinterEntryItem item2) {
            return item1.printerName.compareTo(item2.printerName);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("PrinterListActivity", "Calling onCreate()");

        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        setContentView(R.layout.printer_list);
        
        SharedPreferences listType = getPreferences(MODE_PRIVATE);
        Bundle extras = getIntent().getExtras(); 

        if (extras != null) {
        	SharedPreferences.Editor editor = listType.edit();
        	editor.putString(PrintListMenuActivity.LIST_TYPE, getIntent().getStringExtra(PrintListMenuActivity.LIST_TYPE));
        	editor.commit();
        }
        mDbAdapter = new PrintersDbAdapter(this);
        /*
        Button button01 = (Button) findViewById(R.id.button01);

        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                        PrinterMapActivity.class);
                intent.putExtra("allPrinterView", true);
                startActivity(intent);
            }
        });*/
        
        RefreshListTask task = new RefreshListTask();
        task.execute(isConnected(self));
//        if (isConnected(self)) {
//            ParseQuery query = new ParseQuery("PrintersData");
//            List<ParseObject> objects = null;
//            try {
//                objects = query.find();
//            } catch (ParseException e) {
//                // swallow exception
//                // e.printStackTrace();
//            }
//            setListViewData(objects);
//        } else {
//            Toast.makeText(getApplicationContext(), "Network error",
//                    Toast.LENGTH_SHORT).show();
//            setListViewData(null);
//
//            TextView tv = (TextView) findViewById(R.id.printer_list_error);
//            tv.setText(REFRESH_ERROR);
//
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("PrinterListActivity", "Calling onResume()");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PrinterListActivity", "Calling onPause()");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, REFRESH_ID, 0, "Refresh");
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
        case REFRESH_ID:
            RefreshListTask task = new RefreshListTask();
            task.execute(isConnected(self));
            return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    /**
     * Sets Views Should be called in UI thread
     */
    private void setListViewData(List<ParseObject> objects) {
    	SharedPreferences listSettings = getPreferences(MODE_PRIVATE);
    	String listType = listSettings.getString(PrintListMenuActivity.LIST_TYPE, PrintListMenuActivity.LIST_ALL);
        TextView tv = (TextView) findViewById(R.id.printer_list_error);
        tv.setText("");
        // resets map if not null
        if (objects != null) {
            curr_map = new HashMap<String, PrinterEntryItem>();
            all_map = new HashMap<String, PrinterEntryItem>();
            for (ParseObject o : objects) {
                all_map.put(o.getObjectId(), new PrinterEntryItem(o.getObjectId(),
                            o.getString("printerName"), o.getString("location"),
                            Integer.parseInt(o.getString("status"))));
            	if (listType.equals(PrintListMenuActivity.LIST_ALL)) {
            		PrinterEntryItem item = new PrinterEntryItem(o.getObjectId(),
                            o.getString("printerName"), o.getString("location"),
                            Integer.parseInt(o.getString("status")));
                    curr_map.put(o.getObjectId(), item);
            	}
            	else if (listType.equals(PrintListMenuActivity.LIST_DORM)) {
            		if (o.getBoolean("residence")) {
            			PrinterEntryItem item = new PrinterEntryItem(o.getObjectId(),
                                o.getString("printerName"), o.getString("location"),
                                Integer.parseInt(o.getString("status")));
                        curr_map.put(o.getObjectId(), item);
            		}
            	}
            	else if (listType.equals(PrintListMenuActivity.LIST_CAMPUS)) {
            		if (!o.getBoolean("residence")) {
            			PrinterEntryItem item = new PrinterEntryItem(o.getObjectId(),
                                o.getString("printerName"), o.getString("location"),
                                Integer.parseInt(o.getString("status")));
                        curr_map.put(o.getObjectId(), item);
            		}
            	}
            	else {
            		PrinterEntryItem item = new PrinterEntryItem(o.getObjectId(),
                            o.getString("printerName"), o.getString("location"),
                            Integer.parseInt(o.getString("status")));
                    curr_map.put(o.getObjectId(), item);
            	}
            }
        } else {
//            // changes all status to unknown
//            Map<String, PrinterEntryItem> tmp = new HashMap<String, PrinterEntryItem>();
//            for (String key : map.keySet()) {
//                PrinterEntryItem item = map.get(key);
//                tmp.put(key, new PrinterEntryItem(item.parseId,
//                        item.printerName, item.location, item.status));
//                map = tmp;
//            }
            //handle error
            curr_map = new HashMap<String, PrinterEntryItem>();
        }

        final List<Item> items = new ArrayList<Item>();
        items.add(new SectionItem(listType+" Printers"));
        List<PrinterEntryItem> printers = null;
        if (listType.equals(PrintListMenuActivity.LIST_FAVORITE)) {
            mDbAdapter.open();
            List<String> ids = mDbAdapter.getFavorites();
            printers = new ArrayList<PrinterEntryItem>();
            for (String id : ids) {
                if (all_map.containsKey(id)) {
                    printers.add(all_map.get(id));
                }
            }
            mDbAdapter.close();

        }
        else {
            printers = new ArrayList<PrinterEntryItem>(curr_map.values());

        }
        Collections.sort(printers, comparator);


        for (PrinterEntryItem item : printers) {
            items.add(item);
        }

        Log.i(TAG, new Integer(items.size()).toString());
        EntryAdapter adapter = new EntryAdapter(this, (ArrayList<Item>) items);
        setListAdapter(adapter);

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent intent = new Intent(view.getContext(),
                        PrinterInfoActivity.class);

                if (!items.get(position).isSection()) {
                    intent.putExtra("id",
                            ((PrinterEntryItem) items.get(position)).parseId);
                }

                startActivity(intent);
            }

        });
        Log.i(TAG, "end of fillListData()");
    }

    /**
     * Background task that refreshes the hashmap of printers. Modifies map.
     */
    public class RefreshListTask extends
            AsyncTask<Boolean, byte[], List<ParseObject>> {
        private ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "RefreshTask onPreExecute");
            dialog = ProgressDialog.show(PrinterListActivity.this, "",
                    "Refreshing Data", true);
        }

        @Override
        protected List<ParseObject> doInBackground(Boolean... arg0) { // happens
                                                                      // in
                                                                      // background
                                                                      // thread
            List<ParseObject> objects = null;
            if (arg0[0]) {
                ParseQuery query = new ParseQuery("PrintersData");
                try {
                    objects = query.find();
                } catch (ParseException e) {
                    // swallow exception
                    // e.printStackTrace();
                    Log.e(TAG, "PARSE NUBFAIL in refresh list task");
                }
            }
            return objects;
        }

        @Override
        protected void onCancelled() {
            Log.i(TAG, "RefreshTask Cancelled.");
        }

        @Override
        protected void onPostExecute(List<ParseObject> objects) { // happens in
                                                                  // UI thread
            // Bad practice, but meh, it'd be better if java had tuples
            if (objects == null) {
                Toast.makeText(getApplicationContext(),
                        "Error getting data, please try again later",
                        Toast.LENGTH_SHORT).show();
                Log.i(TAG,
                        "RefreshHashMapTask onPostExecute: Completed with an Error.");
                TextView tv = (TextView) findViewById(R.id.printer_list_error);
                tv.setText(REFRESH_ERROR);
            }
            setListViewData(objects);

            dialog.dismiss();
        }
    }

    /**
     * Checks to see if user is connected to wifi or 3g
     * 
     * @return
     */
    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {

            networkInfo = connectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (!networkInfo.isAvailable()) {
                networkInfo = connectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            }
        }
        return networkInfo == null ? false : networkInfo.isConnected();
    }
}
