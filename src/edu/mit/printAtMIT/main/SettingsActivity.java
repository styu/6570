package edu.mit.printAtMIT.main;

import com.parse.Parse;

import edu.mit.printAtMIT.R;
import android.app.Activity;
import android.os.Bundle;

/**
 * Users can reset their kerberos ID here
 * 
 * Menu Items:
 *      Home
 */
public class SettingsActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer", "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT"); 

    }
}
