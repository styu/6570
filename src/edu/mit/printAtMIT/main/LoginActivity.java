package edu.mit.printAtMIT.main;

import com.parse.Parse;
import com.parse.ParseObject;

import edu.mit.printAtMIT.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is the first activity a user will see upon first opening this app.
 * User inputs Kerberos ID.
 */
public class LoginActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Parse.initialize(this, "KIb9mNtPKDtkDk7FJ9W6b7MiAr925a10vNuCPRer",
                "dSFuQYQXSvslh9UdznzzS9Vb0kDgcKnfzgglLUHT");
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
        Button button01 = (Button) findViewById(R.id.button01);
        button01.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            	Intent intent = new Intent(view.getContext(), MainMenuActivity.class);
            	startActivity(intent);
            }
        });
    }
}
