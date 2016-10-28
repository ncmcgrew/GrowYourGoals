package cs498r.growyourgoals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;

import cs498r.growyourgoals.model.Constants;
import cs498r.growyourgoals.model.Goal;

public class GardenFragment extends Fragment {

    private View.OnClickListener imageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getContext(), ViewGoal.class);
            int goalNum = Integer.parseInt(view.getTag().toString());
            intent.putExtra("goalNum", goalNum);
            startActivity(intent);
        }
    };

    public GardenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_garden, container, false);
        populateGrid(root);

        return root;
    }

    private void populateGrid(View root){
        //-----------------------------Add Plant Images-----------------------------//
        ArrayList<Goal> goals = ModelFacade.getSingleton().getGoals();
        GridLayout grid = (GridLayout)root.findViewById(R.id.garden_grid);


        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        int numCols = Constants.garden_num_columns;
        int screenWidth = metrics.widthPixels;
        int imgWidth = (screenWidth/numCols);
        int padding = 5;

        int row = 0;

        int goalSize = 0;

        try {
            goalSize = goals.size();
        }
        catch (Exception e){
        }

        for(int i = 0; i < goalSize; i++){
            int col = i%Constants.garden_num_columns;

            Goal goal = goals.get(i);
            ImageView img = new ImageView(getActivity());
            img.setAdjustViewBounds(true);
            img.setMaxWidth(imgWidth);

            GridLayout.Spec rowWeight = GridLayout.spec(row, 1);
            GridLayout.Spec colWeight = GridLayout.spec(col, 1);
            GridLayout.LayoutParams rowColWeight = new GridLayout.LayoutParams(rowWeight, colWeight);


//----------change imgResource to grab image based on health-----------------------//
            int imgResource = getPlantPicture(goal);
//---------------------------------------------------------------------------------//

            img.setImageResource(imgResource);
            img.setPadding(padding,padding,padding,padding);
            img.setTag((Integer)i);
            img.setOnClickListener(imageListener);
            grid.addView(img, rowColWeight);
            if(col == Constants.garden_num_columns-1) row ++;
        }
    }

    public int getPlantPicture(Goal goal){

        String plant = goal.getPlantType();

        //add 1 if dying
        //add 2 if healthy
        //add 3 is young
        int age = goal.getDayDifference(goal.getStartDate(),new Date());
        if(age<=2){//Young// if that plant is younger than two ore moreof the period of time
            plant += Constants.SEEDLING + Constants.HEALTHY;
        }else{//not Young
            if(age <= 5) plant += Constants.YOUNG;
            else plant += Constants.ADULT;
            if(goal.getHealth() > .9) plant += Constants.SUPERHEALTHY;
            else if(goal.getHealth()>.75){// healthy
                plant += Constants.HEALTHY;
            }
            else if (goal.getHealth() > .5){//not healthy
                plant += Constants.SICK;
            }
            else plant += Constants.DYING;
        }
        int baseIamge = Constants.PLANT_TYPES.get(plant);
        return baseIamge;

    }

}

