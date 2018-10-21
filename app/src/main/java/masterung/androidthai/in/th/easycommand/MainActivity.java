package masterung.androidthai.in.th.easycommand;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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



//        Mic Controller
        micController();

    }   // Main Method

    private void micController() {
        ImageView imageView = findViewById(R.id.imvMicrophone);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCommand(string);

            }   // onClick
        });
    }

    private void showCommand(String textString) {
        Toast.makeText(MainActivity.this, textString, Toast.LENGTH_LONG).show();
    }



}   // Main Class
