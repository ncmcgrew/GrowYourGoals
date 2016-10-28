package cs498r.growyourgoals;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cs498r.growyourgoals.model.Goal;
import cs498r.growyourgoals.model.Update;

public class ViewGoal extends AppCompatActivity {

    private Intent startingIntent;
    private int goalNum;
    Goal goal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goal);
        startingIntent = getIntent();
        goalNum = startingIntent.getIntExtra("goalNum", 0);

        goal = ModelFacade.getSingleton().getGoal(goalNum);
        //goalName
        TextView goalNameTxt =new TextView(this);

        goalNameTxt=(TextView)findViewById(R.id.GoalName);
        goalNameTxt.setText(goal.getName());
        //LastUpdateInfo
        TextView LastUpdateInfo =new TextView(this);

        goalNameTxt=(TextView)findViewById(R.id.LastUpdateInfo);
        Update lastUpdate = goal.getLatest();
        if(lastUpdate ==null){
            goalNameTxt.setText("N/A");
        }else{
            DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
            goalNameTxt.setText(df.format(lastUpdate.getDate()));
        }

        // PlantHealthInfo
        TextView PlantHealthInfo =new TextView(this);

        goalNameTxt=(TextView)findViewById(R.id.PlantHealthInfo);
        DecimalFormat df = new DecimalFormat("0.00");
        double health = goal.getHealth();
        String toPrint;
        if(health >= .995){
            toPrint = "100";
        }else if(health <=.0049999) {
            toPrint = "0";
        }else{
                String healthNum = df.format(health);
                toPrint = healthNum.substring(2);
        }

        goalNameTxt.setText(toPrint+"%");

        //LongestStreakInfo
        TextView LongestStreakInfo =new TextView(this);

        goalNameTxt=(TextView)findViewById(R.id.LongestStreakInfo);
        int x = goal.getLongestStreak();
        goalNameTxt.setText(Integer.toString(x));//goal.getLongestStreak()));
        populateGraph();

    }
    protected void populateGraph(){
        // got to http://www.android-graphview.org/ for more information
        GraphView graph = (GraphView) findViewById(R.id.graph);
        /*LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });*/
        //graph.addSeries(series);
        if(goal.getQuantity()==Goal.Quantity.BINARY) {
            double data[] = goal.getData();
            int numberofPositiveUpdates = 0;
            int numberOfNegativeUpdates = 0;
            int numberOfNoReports = 0;
            for (int i = 0; i < data.length; i++) {
                double report = data[i];
                if (report == 0) {
                    numberOfNegativeUpdates++;
                } else if (report == 1) {
                    numberofPositiveUpdates++;
                } else {//report == -1
                    numberOfNoReports++;
                }
            }
            BarGraphSeries<DataPoint> series = new BarGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(1, numberofPositiveUpdates),//yes
                    new DataPoint(2, numberOfNegativeUpdates),//no
                    new DataPoint(3, numberOfNoReports)//no answer
            });
            graph.addSeries(series);
        }else {//is based on completion or amount
            double data[] = goal.getData();
            DataPoint[] dataPoints = new DataPoint[data.length];
            DataPoint[] goalDataPoints = new DataPoint[data.length];
            //-1 no report anything else is reported
            for (int i = 0; i < data.length; i++) {
                //http://www.android-graphview.org/documentation/category/dates-as-labels
                //review that website to figure this out
                ///// try and convert date to calendar
                Date day = goal.getDateFromPeriod(i);
                ////
                dataPoints[i] = new DataPoint(i+1/*dateToCalendar(day).getTime()*/, data[i]);
                goalDataPoints[i] = new DataPoint(i+1/*dateToCalendar(day).getTime()*/,goal.getGoalAmount());
            }
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            LineGraphSeries<DataPoint> seriesGoal = new LineGraphSeries<>(goalDataPoints);
            graph.addSeries(series);
            graph.addSeries(seriesGoal);
            // legend
            series.setTitle("Data");
            seriesGoal.setTitle("Goal");
            int c = seriesGoal.getColor();
            //https://developer.android.com/reference/android/graphics/Color.html
            //look there for colors
            seriesGoal.setColor(-16711936);
            graph.getLegendRenderer().setVisible(true);
            graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);
            graph.getViewport().setScrollable(true);
        }

    }
    public void UpdateGoal(View view){
        Intent intent = new Intent(this, UpdatePage.class);
        intent.putExtra("goalNum", goalNum);
        ViewGoal.this.startActivity(intent);
    }
    //Convert Date to Calendar
    private Calendar dateToCalendar(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;

    }

}
