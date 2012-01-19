package edu.mit.printAtMIT.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.mit.printAtMIT.R;
import edu.mit.printAtMIT.print.PrintOptionsActivity;

/**
 * Lists all the printers from database. Shows name, location, status from each
 * printer List of favorite printers on top, then list of all printers
 * 
 * Menu Item: Settings About Home Refresh
 * 
 * Context Menu Items: Favorite, Info, MapView
 */

public class PrinterListActivity extends ListActivity {
    private final List<String> mPrinterList = new ArrayList<String>();
    private final List<ParseObject> mPrinters = new ArrayList<ParseObject>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
//        setContentView(R.layout.printer_list);
        
        //TODO: button for full map view
//        Button button01 = (Button) findViewById(R.id.button01);
//        Button button02 = (Button) findViewById(R.id.button02);

        Log.i("printerlist", "creating ParseQuery PrintersData");

        ParseQuery query = new ParseQuery("PrintersData");
        Log.i("printerlist", "created ParseQuery PrintersData");
        try {
            List<ParseObject> objects = query.find();
            for (ParseObject o : objects) {
                StringBuilder sb = new StringBuilder();
                sb.append(o.getString("printerName") + "\t\t");
                sb.append(o.getString("location") + "\t\t");
                sb.append(o.getString("status") + "");
                Log.i("PRINTERLIST", "added " + sb.toString());
                mPrinterList.add(sb.toString());
                mPrinters.add(o);
            }
        } catch (ParseException e1) {
            Log.e("printerList", "query.find() FAILED");
        }
//        query.findInBackground(new FindCallback() {
//            public void done(List<ParseObject> objects, ParseException e) {
//                if (e == null) {
//                    for (ParseObject o : objects) {
//                        StringBuilder sb = new StringBuilder();
//                        sb.append(o.getString("printerName") + "\t");
//                        sb.append(o.getString("location") + "\t");
//                        sb.append(o.getInt("status"));
//                        Log.i("PRINTERLIST", "added " + sb.toString());
//                        printerList.add(sb.toString());
//                    }
//                        
//                } else {
//                    Log.i("PRINTERLIST", "object retreival SUCCESS =DDDD");
//                        
//                }
//            }
//     
//        });
        
//        StringBuilder test = new StringBuilder("Data should follow: " + mPrinterList.size());
//        for (String s: mPrinterList) {
//            test.append(s);
//        }
        String[] list = new String[mPrinterList.size()];
        String[] printerList = new String[mPrinters.size()];
        for (int i=0; i < mPrinterList.size(); i++) {
            list[i] = mPrinterList.get(i);
            ParseObject printer = mPrinters.get(i);
            printerList[i] = printer.getString("printerName") + "\t\t" + printer.getString("location") + "\t\t" + printer.getString("status");
        }
        
//        TextView data = (TextView) findViewById(R.id.test);
//        data.setText(test.toString());
        
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_item, printerList));
        Log.i("printerList", new Integer(list.length).toString());
        ListView lv = getListView();
        Log.i("printerList", lv.toString());
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
//        button01.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), PrinterMapActivity.class);
//                startActivity(intent);
//            }
//        });
//        button02.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(),
//                        PrinterInfoActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}
