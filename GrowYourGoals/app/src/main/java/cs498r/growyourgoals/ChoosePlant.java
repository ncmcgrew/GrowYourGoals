package cs498r.growyourgoals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.Date;

import cs498r.growyourgoals.model.Constants;


public class ChoosePlant extends AppCompatActivity {
    private Intent startingIntent;
    private String name;
    private String freq;
    private String importance;
    private String quantity;
    private String unit;
    private Date   startDate;
    private double goalAmount;
    private String plantType;

    private View.OnClickListener choosePlantListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        plantType = view.getTag().toString();

        ModelFacade modelFacade = ModelFacade.getSingleton();
        modelFacade.addGoal(name, freq, Integer.parseInt(quantity), Integer.parseInt(importance), unit, startDate, goalAmount, plantType);
        Intent intent = new Intent(getBaseContext(), HomeSwipeActivity.class);
        startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_plant);

        GridLayout imageGrid = (GridLayout)findViewById(R.id.image_grid);


        String pkgName = this.getPackageName();

        int numCols = Constants.garden_num_columns;
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int screenWidth = metrics.widthPixels;
        int maxWidth = (screenWidth/numCols);
        int padding = 5;


        int row = 0;
        for(int i = 0; i < Constants.BASIC_PLANT_NAMES.length; i++){
            String plantName = Constants.BASIC_PLANT_NAMES[i];
            int col = i%Constants.garden_num_columns;

            ImageView image = new ImageView(this);

            int ident = Constants.PLANT_TYPES.get(plantName);

            image.setImageResource(ident);
            image.setMaxWidth(maxWidth);

            image.setAdjustViewBounds(true);
            image.setTag(plantName);
            image.setOnClickListener(choosePlantListener);
            image.setPadding(padding,padding,padding,padding);

            GridLayout.Spec rowWeight = GridLayout.spec(row, 1);
            GridLayout.Spec colWeight = GridLayout.spec(col, 1);
            GridLayout.LayoutParams rowColWeight = new GridLayout.LayoutParams(rowWeight, colWeight);

            imageGrid.addView(image, rowColWeight);
            if(col == Constants.garden_num_columns-1) row ++;
        }

        startingIntent = getIntent();

        name = startingIntent.getStringExtra("Goal Name");
        freq = startingIntent.getStringExtra("Frequency");
        importance = startingIntent.getStringExtra("Importance");
        quantity = startingIntent.getStringExtra("Quantity");
        unit = startingIntent.getStringExtra("Unit");
        String receivedAmount = startingIntent.getStringExtra("GoalAmount");
        try{
            goalAmount = Double.parseDouble(receivedAmount);
        }
        catch(Exception e){
            goalAmount = 0;
        }

        startDate = new Date();
    }
}
