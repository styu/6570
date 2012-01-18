package edu.mit.printAtMIT;

import com.parse.Parse;

import android.app.Activity;
import android.os.Bundle;

public class PrintAtMITActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer", "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT"); 

    }
}