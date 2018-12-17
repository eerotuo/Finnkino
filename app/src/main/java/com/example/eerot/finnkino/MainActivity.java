package com.example.eerot.finnkino;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.time.LocalTime;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {


    ReadXML readXML = ReadXML.getInstance();
    //ArrayList teatterit = new ArrayList();
    EditText editText, editText3= null, editText2;
    TextView textView;

    String valinta= null;

    Editable pvmAlk = null;
    LocalTime klo= null;
    LocalTime klo2= null;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        spinner =findViewById(R.id.spinner);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        textView = findViewById(R.id.textView);

        //teatterit = readXML.theatres();

        editText3.setText(null);
        //readXML.theatres();


        dropdown();
    }


    public void dropdown(){

        int id;

        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, readXML.theatres());
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                valinta = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }


        });
    }

    public void Find(View v){

        int id = readXML.getID(valinta);
        textView.setText("");
        pvmAlk = editText.getText();
        klo = null;
        klo2 = null;


        if (!editText3.getText().toString().equals("")) {
            klo = LocalTime.parse((editText3.getText()));
            System.out.println("!!!!11");
        }

        if (!editText2.getText().toString().equals("")) {
            klo2 = LocalTime.parse((editText2.getText()));
            System.out.println("!!!!22");
        }

        readXML.printMovies(id, textView,pvmAlk,klo,klo2);

    }

}
