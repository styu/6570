package edu.mit.printAtMIT.view;

import com.parse.Parse;

import edu.mit.printAtMIT.R;
import android.app.Activity;
import android.os.Bundle;

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

    }
}
