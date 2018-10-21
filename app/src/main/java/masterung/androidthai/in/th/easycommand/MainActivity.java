package masterung.androidthai.in.th.easycommand;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private String[] strings = new String[]{
            "TV",
            "lighter",
            "car"
    };

    private String string = "Please Speak Command";

    private boolean aBoolean = true;

    private TextView tvTextView, lighterTextView, carTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Mic Controller
        micController();

        tvTextView = findViewById(R.id.txtTV);
        lighterTextView = findViewById(R.id.txtLighter);
        carTextView = findViewById(R.id.txtCar);

        updateStatus();



    }   // Main Method

    private void updateStatus() {

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map map = (Map) dataSnapshot.getValue();

                tvTextView.setText(strings[0] + " " + String.valueOf(map.get("TV")));
                lighterTextView.setText(strings[1] + " " + String.valueOf(map.get("lighter")));
                carTextView.setText(strings[2] + " " + String.valueOf(map.get("car")));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {



            ArrayList<String> stringArrayList = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            final String resultString = stringArrayList.get(0);

            for (int i = 0; i < strings.length; i += 1) {

                if (resultString.equals(strings[i])) {

                    aBoolean = false;

                }   // if
            }   // for

            if (aBoolean) {
//                No Command
                showCommand("No " + resultString + " in Command");
            } else {
//                Update Command on Firebase
                showCommand("Upload " + resultString + " To Firebase");

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference();

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Map<String, Object> stringObjectMap = new HashMap<>();

                        if (resultString.equals(strings[0])) {
                            stringObjectMap.put("TV", "on");
                        } else if (resultString.equals(strings[1])) {
                            stringObjectMap.put("lighter", "on");
                        } else  {
                            stringObjectMap.put("car", "on");
                        }

                        databaseReference.updateChildren(stringObjectMap);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }   // if


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
