package edu.mit.printAtMIT.view;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.mit.printAtMIT.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Shows printer info; name, location, status, picture, relevant stuff
 */
public class PrinterInfoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        setContentView(R.layout.printer_info);
        Bundle extras = getIntent().getExtras(); 
        String id = extras.getString("id");
        StringBuilder info = new StringBuilder("Front Panel Message: " + "\n");
        //Parse makes call to cloud to retreive printer information
        ParseQuery query = new ParseQuery("PrintersData");
        ParseObject printer;
        try {
            printer = query.get(id);
            info.append(printer.get("FrontPanelMessage") + "\n");
            if (printer.get("line2") != null) {
                info.append(printer.get("line2") + "\n");
            }    
            if (printer.get("line3") != null) {
                info.append(printer.get("line3") + "\n");
            } 
            if (printer.get("line4") != null) {
                info.append(printer.get("line4") + "\n");
            } 
            if (printer.get("line5") != null) {
                info.append(printer.get("line5"));
            } 
        } catch (ParseException e) {
            info.append("Parse nubfail, fool");
        }

        TextView view = (TextView) findViewById(R.id.printer_info_text);
        view.setText(info.toString());
    }
}
