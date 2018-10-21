package masterung.androidthai.in.th.easycommand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String[] strings = new String[]{
            "one",
            "two",
            "three"
    };

    private String string = "Please Speak Command";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showCommand(string);

    }   // Main Method

    private void showCommand(String textString) {
        Toast.makeText(MainActivity.this, textString, Toast.LENGTH_LONG).show();
    }



}   // Main Class
