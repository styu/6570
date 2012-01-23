package edu.mit.printAtMIT.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Comparator;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

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
    private final Map<String, PrinterEntryItem> map = new HashMap<String, PrinterEntryItem>();
    private PrintersDbAdapter mDbAdapter;
    private PrinterComparator comparator = new PrinterComparator();

    public class PrinterComparator implements Comparator<PrinterEntryItem> {

        @Override
        public int compare(PrinterEntryItem item1, PrinterEntryItem item2) {
            return item1.printerName.compareTo(item2.printerName);
        }
        
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        setContentView(R.layout.printer_list);
        Button button01 = (Button) findViewById(R.id.button01);
        mDbAdapter = new PrintersDbAdapter(this);

        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                        PrinterMapActivity.class);
                intent.putExtra("allPrinterView", true);
                startActivity(intent);
            }
        });

        fillListData();
    }

    private void setPrinterMap() {
        ParseQuery query = new ParseQuery("PrintersData");
        try {
            List<ParseObject> objects = query.find();
            for (ParseObject o : objects) {
                PrinterEntryItem item = new PrinterEntryItem(o.getObjectId(),
                        o.getString("printerName"), o.getString("location"),
                        Integer.parseInt(o.getString("status")));
                map.put(o.getObjectId(), item);


            }
        } catch (ParseException e1) {
            Log.e("printerList", "query.find() FAILED");
            e1.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("PrinterListActivity", "Calling onResume()");
        fillListData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PrinterListActivity", "Calling onPause()");
    }

    private void fillListData() {
        final List<Item> items = new ArrayList<Item>();
        items.add(new SectionItem("Favorites"));

        setPrinterMap();
        mDbAdapter.open();

        Log.i("PrinterListActivity", "mDbAdapter.getFavorites()");
        final List<String> ids = mDbAdapter.getFavorites();
        List<PrinterEntryItem> favorites = new ArrayList<PrinterEntryItem>();
        for (String id : ids) {
            favorites.add(map.get(id));
        }
        Collections.sort(favorites, comparator);
        for (PrinterEntryItem item : favorites) {
            items.add(item);
        }
        items.add(new SectionItem("All Printers"));
        
        List<PrinterEntryItem> printers = new ArrayList<PrinterEntryItem>(map.values());
        Collections.sort(printers, comparator);
        
        for (PrinterEntryItem item : printers) {
            items.add(item);
        }

        EntryAdapter adapter = new EntryAdapter(this, (ArrayList<Item>)items);
        setListAdapter(adapter);
        mDbAdapter.close();

        ListView lv = getListView();
        lv.setTextFilterEnabled(true);

        lv.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
                Intent intent = new Intent(view.getContext(),
                        PrinterInfoActivity.class);
                
                if (!items.get(position).isSection()) {
                    intent.putExtra("id", ((PrinterEntryItem) items.get(position)).parseId);
                }

                startActivity(intent);
            }

        });
    }

}
