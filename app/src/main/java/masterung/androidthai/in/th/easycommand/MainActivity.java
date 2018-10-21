package masterung.androidthai.in.th.easycommand;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private String[] strings = new String[]{
            "TV",
            "lighter",
            "car"
    };

    private String string = "Please Speak Command";

    private boolean aBoolean = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



//        Mic Controller
        micController();

    }   // Main Method


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {



            ArrayList<String> stringArrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String resultString = stringArrayList.get(0);

            for (int i = 0; i < strings.length; i += 1) {

                if (resultString.equals(strings[i])) {

                    aBoolean = false;

                }   // if
            }   // for

            if (aBoolean) {
                showCommand("No " + resultString + " in Command");
            } else {
                showCommand("Upload " + resultString + " To Firebase");
            }


        }

    }

    private void micController() {
        ImageView imageView = findViewById(R.id.imvMicrophone);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                aBoolean = true;

                showCommand(string);

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Cannot Use Speak To Text");

                try {

                    startActivityForResult(intent, 100);

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }   // onClick
        });
    }

    private void showCommand(String textString) {
        Toast.makeText(MainActivity.this, textString, Toast.LENGTH_LONG).show();
    }


}   // Main Class
