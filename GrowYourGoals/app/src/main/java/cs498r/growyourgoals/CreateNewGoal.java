package cs498r.growyourgoals;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cs498r.growyourgoals.model.Goal;

public class CreateNewGoal extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    AdapterView.OnItemSelectedListener quanListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position == 1){
                findViewById(R.id.amount).setVisibility(View.VISIBLE);
                findViewById(R.id.unit).setVisibility(View.VISIBLE);
            }
            else{
                findViewById(R.id.amount).setVisibility(View.GONE);
                findViewById(R.id.unit).setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_goal);
        ///////////////FREQUENCY SPINNER//////////////////
        // Spinner element
        Spinner spinnerFreq = (Spinner) findViewById(R.id.spinnerFreq);

        // Spinner click listener
//        spinnerFreq.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> frequency = new ArrayList<String>();
        frequency.add("Daily");
        frequency.add("Weekly");
        frequency.add("Monthly");
        frequency.add ("Choose Frequncy");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterFreq = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };

        // Drop down layout style - list view with radio button
        dataAdapterFreq.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterFreq.addAll(frequency);
        // attaching data adapter to spinner
        spinnerFreq.setAdapter(dataAdapterFreq);
        spinnerFreq.setSelection(dataAdapterFreq.getCount()); //display hint



        ///////////////Importance SPINNER//////////////////
        // Spinner element
        Spinner spinnerImpor = (Spinner) findViewById(R.id.spinnerImpor);

        // Spinner click listener
//        spinnerImpor.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> importance = new ArrayList<String>();
        importance.add("One Star");
        importance.add("Two Star");
        importance.add("Three Star");
        importance.add("Four Star");
        importance.add("Five Star");
        importance.add("Choose Importance");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterImport = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }
        };

        // Drop down layout style - list view with radio button
        dataAdapterImport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterImport.addAll(importance);
        // attaching data adapter to spinner
        spinnerImpor.setAdapter(dataAdapterImport);
        spinnerImpor.setSelection(dataAdapterImport.getCount()); //display hint



        ///////////////Quantity SPINNER//////////////////
        // Spinner element
        Spinner spinnerQuan = (Spinner) findViewById(R.id.spinnerQuan);

        // Spinner click listener
        spinnerQuan.setOnItemSelectedListener(quanListener);

        // Spinner Drop down elements
        List<String> quantity = new ArrayList<String>();
        quantity.add("Completion");
        quantity.add("Amount");
        quantity.add("Choose Goal Type");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterQuan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView)v.findViewById(android.R.id.text1)).setText("");
                    ((TextView)v.findViewById(android.R.id.text1)).setHint(getItem(getCount())); //"Hint to be displayed"
                }

                return v;
            }

            @Override
            public int getCount() {
                return super.getCount()-1; // you dont display last item. It is used as hint.
            }


        };

        // Drop down layout style - list view with radio button
        dataAdapterQuan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterQuan.addAll(quantity);
        // attaching data adapter to spinner
        spinnerQuan.setAdapter(dataAdapterQuan);
        spinnerQuan.setSelection(dataAdapterQuan.getCount()); //display hint
        /////////////////////////////////////////////////

//        spinnerQuan.setOnItemClickListener();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void choosePlant(View view){

        ArrayList<Goal> list = ModelFacade.getSingleton().getGoals();// this is test for the singlton
        EditText goalNameTextBox = (EditText)findViewById(R.id.GoalName);
        String goalName = goalNameTextBox.getText().toString();

        Spinner freqSpinner = (Spinner)findViewById(R.id.spinnerFreq);
        String freq = freqSpinner.getSelectedItem().toString();

        Spinner imporSpinner = (Spinner)findViewById(R.id.spinnerImpor);
        String impor;
        switch (imporSpinner.getSelectedItem().toString()){
            case "One Star":
                impor = "1";
                break;
            case "Two Star":
                impor = "2";
                break;
            case "Three Star":
                impor = "3";
                break;
            case "Four Star":
                impor = "4";
                break;
            case "Five Star":
                impor = "5";
                break;
            default:
                impor = "0";
                break;
        }

        Spinner quanSpinner = (Spinner)findViewById(R.id.spinnerQuan);
        String quan;

        EditText amountBox = (EditText)findViewById(R.id.amount);
        String goalAmount = amountBox.getText().toString();

        EditText unitBox = (EditText)findViewById(R.id.unit);
        String unit = unitBox.getText().toString();

        switch (quanSpinner.getSelectedItem().toString()){
            case "Completion":
                quan = "1";
                unit = "none";
                goalAmount = "0";
                break;
            default:
                quan = "0";
                break;
        }

        Intent intent = new Intent(this, ChoosePlant.class);
        intent.putExtra("Goal Name",goalName);
        intent.putExtra("Frequency",freq);
        intent.putExtra("Importance",impor);
        intent.putExtra("Quantity",quan);
        intent.putExtra("GoalAmount", goalAmount);
        intent.putExtra("Unit", unit);
        startActivity(intent);
    }
}