package com.example.t9_3;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView info;
    Spinner sPostSpin, dateSpin, countrySpin;
    EditText startTime, endTime;

    Machines mc = Machines.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        info = findViewById(R.id.infoView);
        sPostSpin = findViewById(R.id.smartPostSpinner);
        dateSpin = findViewById(R.id.dateSpinner);
        countrySpin = findViewById(R.id.countrySpinner);
        startTime = findViewById(R.id.startText);
        endTime = findViewById(R.id.endText);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        spinners();
    }

    public void print(View v) {
        mc.print();
    }

    public void spinners() {
        ArrayList<String> smartPostNames = new ArrayList<>();


        for (int x = 0; x < mc.machines.size(); x++) {
            smartPostNames.add(mc.machines.get(x).getName());
        }
        smartPostNames.add(0, "Valitse SmartPost automaatti");



        ArrayAdapter<String> smartPosts = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, smartPostNames);

        smartPosts.setDropDownViewResource(android.R.layout.simple_spinner_item);

        sPostSpin.setAdapter(smartPosts);

        sPostSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!parent.getItemAtPosition(position).equals("Valitse SmartPost automaatti")) {
                    String choice = parent.getItemAtPosition(position).toString();
                    int index = mc.findInvIndex(choice);
                    String information = "Nimi: " + mc.machines.get(index).getName() + "\n" + "Osoite: " +
                            mc.machines.get(index).getAddress() + "\n" +
                            "Aukioloajat: " + mc.machines.get(index).getAvailability() + "\n";
                    info.setText(information);
                    System.out.print("Nimi: ");
                    System.out.println(mc.machines.get(index).getName());
                    System.out.print("Osoite: ");
                    System.out.println(mc.machines.get(index).getAddress());
                    System.out.print("Aukioloajat: ");
                    System.out.println(mc.machines.get(index).getAvailability());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}
