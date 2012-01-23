package edu.mit.printAtMIT.view;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import edu.mit.printAtMIT.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Shows printer info; name, location, status, picture, relevant stuff
 */
public class PrinterInfoActivity extends Activity {

    private PrintersDbAdapter mDbAdapter;
    private boolean favorite;
    private Button button02;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("PrinterInfoActivity", "onCreate");
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        setContentView(R.layout.printer_info);

        Bundle extras = getIntent().getExtras();
        final String id = extras.getString("id");

        // set favorite state of printer
        mDbAdapter = new PrintersDbAdapter(this);
        mDbAdapter.open();
        favorite = mDbAdapter.isFavorite(id);

        // Parse makes call to cloud to retrieve printer information
        ParseQuery query = new ParseQuery("PrintersData");
        ParseObject printer;
        StringBuilder name = new StringBuilder("Printer Name: ");
        StringBuilder info = new StringBuilder("Front Panel Message: " + "\n");
        StringBuilder paperJam = new StringBuilder("Paper Jam: ");
        StringBuilder paperStatus = new StringBuilder("Paper Status: ");
        StringBuilder tonerStatus = new StringBuilder("Toner Status: ");

        try {
            printer = query.get(id);
            name.append(printer.getString("printerName"));
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
            if (printer.get("PaperJamStatus") != null) {
                paperJam.append(printer.get("PaperJamStatus"));
            }
            if (printer.get("PaperStatus") != null) {
                paperStatus.append(printer.get("PaperStatus"));
            }
            if (printer.get("TonerStatus") != null) {
                tonerStatus.append(printer.get("TonerStatus"));
            }
        } catch (ParseException e) {
            info.append("Parse nubfail, fool");
        }
        Button button01 = (Button) findViewById(R.id.button01);
        button02 = (Button) findViewById(R.id.button02);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),
                        PrinterMapActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        button02.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (favorite) {
                    mDbAdapter.removeFavorite(id);
                    button02.setText("Add to Favorites");
                    favorite = false;
                } else {
                    mDbAdapter.addToFavorites(id);
                    button02.setText("Remove from Favorites");
                    favorite = true;
                }
            }

        });
        TextView view = (TextView) findViewById(R.id.printer_info_text);
        view.setText(name.toString() + "\n\n" + info.toString() + "\n\n"
                + paperJam.toString() + "\n\n" + paperStatus.toString() + "\n\n"
                + tonerStatus.toString());
        mDbAdapter.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("PrinterInfoActivity", "onPause");
        mDbAdapter.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("PrinterInfoActivity", "onResume");
        if (favorite) {
            button02.setText("Remove from favorites");
        } else {
            button02.setText("Add to favorites");
        }
        mDbAdapter.open();
    }
}
