package edu.mit.printAtMIT.view;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.mit.printAtMIT.R;

/**
 * Lists all the printers from database. Shows name, location, status from each
 * printer List of favorite printers on top, then list of all printers
 * 
 * Menu Item: Settings About Home Refresh
 * 
 * Context Menu Items: Favorite, Info, MapView
 */

public class PrinterListActivity extends ListActivity {
    private final List<ParseObject> mPrinters = new ArrayList<ParseObject>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        setContentView(R.layout.printer_list);
        Button button01 = (Button) findViewById(R.id.button01);

        setPrinterList();
        
        String[] printerList = new String[mPrinters.size()];
        
        //creates the text for each list item
        //TODO: ADD UI
        for (int i=0; i < mPrinters.size(); i++) {
            ParseObject printer = mPrinters.get(i);
            printerList[i] = printer.getString("printerName") + "\t\t" + printer.getString("location") + "\t\t" + printer.getString("status");
        }
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, printerList));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long id) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(view.getContext(), PrinterInfoActivity.class);
                intent.putExtra("id", mPrinters.get(position).getObjectId());
                startActivity(intent);
            }
            
        });
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), PrinterMapActivity.class);
                intent.putExtra("allPrinterView", true);
                startActivity(intent);
            }
        });
    }
    
    private void setPrinterList() {
        Log.i("printerlist", "creating ParseQuery PrintersData");
        ParseQuery query = new ParseQuery("PrintersData");
        Log.i("printerlist", "created ParseQuery PrintersData");
        try {
            List<ParseObject> objects = query.find();
            for (ParseObject o : objects) {
                mPrinters.add(o);
            }
        } catch (ParseException e1) {
            Log.e("printerList", "query.find() FAILED");
        }

    }
}
