package cs498r.growyourgoals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cs498r.growyourgoals.R;
import cs498r.growyourgoals.model.Goal;

/**
 * Created by Justin on 7/15/2016.
 */
public class UpdatePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Intent startingIntent;
    EditText inputAmount;
    private int goalNum;
    Spinner calendarSpinner;
    Goal goal;
    double data[];
    static long day = 86400000;

    private double amount;

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            try{
                amount = Double.parseDouble(s.toString());
                System.out.println(s.toString());
                findViewById(R.id.updateButton).setEnabled(true);
            }
            catch (Exception e){
                findViewById(R.id.updateButton).setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_progress);

        startingIntent = getIntent();
        goalNum = startingIntent.getIntExtra("goalNum", 0);
        System.out.println("goalNum is: " + goalNum);

        goal = ModelFacade.getSingleton().getGoal(goalNum);
        TextView goalName = (TextView)findViewById(R.id.goalName1);
        goalName.setText(goal.getName());
        data = goal.getData();
        Goal.Frequency freq = goal.getFrequency();
        Date startDate = goal.getStartDate();
        //https://github.com/dharmin007/Android-Calendar-Widget

        // Spinner element
        calendarSpinner = (Spinner) findViewById(R.id.calendarSpinner);

        // Spinner click listener
        calendarSpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> spinnerData;
        if(freq == Goal.Frequency.DAILY){
            spinnerData = fillDays(startDate, data);
        }else if(freq == Goal.Frequency.MONTHLY){
            spinnerData = fillMonths(startDate, data);
        }else if(freq == Goal.Frequency.WEEKLY){
            spinnerData = fillWeeks(startDate, data);
        }else{
            System.err.println("error in making calendar");
            spinnerData = new ArrayList<String>();
        }

        ArrayAdapter<String> calendarAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        calendarAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        calendarAdapter.addAll(spinnerData);
        // attaching data adapter to spinner
        calendarSpinner.setAdapter(calendarAdapter);

        calendarSpinner.setSelection(calendarAdapter.getCount()-1);

        EditText input = (EditText)findViewById(R.id.numberInput);
        TextView unit = (TextView)findViewById(R.id.unitText);


        if(goal.getQuantity().equals(Goal.Quantity.NUMERIC)){

            input.addTextChangedListener(watcher);
            input.setVisibility(View.VISIBLE);

            unit.setText(goal.getUnit());
            unit.setVisibility(View.VISIBLE);
            System.out.println(goal.getQuantity());
        }
        else{
            findViewById(R.id.radio_buttons).setVisibility(View.VISIBLE);
        }

    }

    public void onRadioButtonClicked(View view){
        boolean checked = ((RadioButton) view).isChecked();
        if(checked){
            findViewById(R.id.updateButton).setEnabled(true);
            switch(view.getId()){
                case R.id.radio_yes:
                    amount = 1;
                    break;

                default:
                    amount = 0;
                    break;
            }
        }
        else findViewById(R.id.updateButton).setEnabled(false);

    }

    public List<String> fillWeeks(Date startDate, double[] data){
        List<String> toReturn = new ArrayList<String>();
        int month = startDate.getMonth();
        int year = startDate.getYear() + 1900;
        for(int i = 0; i < data.length; i++){
            Date weekStart = new Date(startDate.getTime() + 7*(i * day)); //86400000 milliseconds per day
            Date weekFinish = new Date(weekStart.getTime() + (6* day)); // six days after weekStart
            String input = getMonth(weekStart.getMonth());
            input += " " + weekStart.getDate() + "\t-";
            input += " " + getMonth(weekFinish.getMonth());
            input += " " + weekFinish.getDate() + "\t";
            input += "(";
            if(data[i] != -1){
                input += "x";
            }else{
                input += " ";
            }
            input += ")";
            toReturn.add(input);
        }
        return toReturn;
    }

    public List<String> fillMonths(Date startDate, double[] data){
        List<String> toReturn = new ArrayList<String>();
        int month = startDate.getMonth();
        int year = startDate.getYear() + 1900;
        for(int i = 0; i < data.length; i++){
            String input = getMonth(month);
            input += " " + year + "\t";
            input += "(";
            if(data[i] != -1){
                input += "x";
            }else{
                input += " ";
            }
            input += ")";
            toReturn.add(input);
            if(month >= 11){
                month = 0;
                year++;
            }else {
                month++;
            }
        }

        return toReturn;
    }

    public List<String> fillDays(Date startDate, double[] data){
        List<String> toReturn = new ArrayList<String>();
        for(int i = 0; i < data.length; i++){
            Date thisDay = new Date(startDate.getTime() + (i * day)); //86400000 milliseconds per day
            int dayOfMonth = thisDay.getDate();
            String input = getDayOfWeek(thisDay.getDay());
            input += " " + getMonth(thisDay.getMonth());
            input += " " + dayOfMonth + "\t";
            input += "(";
            if(data[i] != -1){
                input += "x";
            }else{
                input += " ";
            }
            input += ")";
            toReturn.add(input);
        }
        return toReturn;
    }

    public String getMonth(int i){
        switch(i){
            case 0:
                return "Jan ";
            case 1:
                return "Feb ";
            case 2:
                return "Mar, ";
            case 3:
                return "Apr ";
            case 4:
                return "May, ";
            case 5:
                return "Jun ";
            case 6:
                return "Jul ";
            case 7:
                return "Aug ";
            case 8:
                return "Sep ";
            case 9:
                return "Oct ";
            case 10:
                return "Nov ";
            case 11:
                return "Dec ";
        }
        return "000 ";
    }

    public String getDayOfWeek(int i){
        switch(i){
            case 0:
                return "Sun, ";
            case 1:
                return "Mon, ";
            case 2:
                return "Tue, ";
            case 3:
                return "Wed, ";
            case 4:
                return "Thu, ";
            case 5:
                return "Fri, ";
            case 6:
                return "Sat, ";
        }
        return "000, ";
    }
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(data[i] != -1) {
            int period = i;
            EditText input = (EditText) findViewById(R.id.numberInput);
            input.setText(Double.toString(data[i]));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    System.out.println("here");
    }

    public void updateButtonHit(View view){
        System.out.println();
        EditText input = (EditText)findViewById(R.id.numberInput);
        Editable foo = input.getText();
//        double num = Double.parseDouble(input.getText().toString());
        double num = amount;
        int period = calendarSpinner.getSelectedItemPosition();
        System.out.println("period: " + period);
        boolean addNewUpdate = !goal.containsPeriodUpdate(period);
        Date inputDate = goal.getDateFromPeriod(period);
        if(addNewUpdate){
            goal.addUpdate(inputDate,num);
        }else{
            goal.removeUpdate(period);
            goal.addUpdate(inputDate,num);
        }

        SaveManager.writeGoalsToFile(getApplicationContext(), ModelFacade.getSingleton().getGoals());

        Intent intent = new Intent(this, HomeSwipeActivity.class);
        startActivity(intent);
    }
}
