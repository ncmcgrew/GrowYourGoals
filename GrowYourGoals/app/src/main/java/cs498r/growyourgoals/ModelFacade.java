package cs498r.growyourgoals;

import android.content.Context;

import java.util.ArrayList;
import java.util.Date;

import cs498r.growyourgoals.model.Constants;
import cs498r.growyourgoals.model.Goal;

/**
 * Created by ckbzo on 6/29/16.
 */
public class ModelFacade {
    private static ModelFacade singleton;
    private ArrayList<Goal> goals;
    private Context appContext;

// need me to figure out load from json and save to json stuff

    public  ModelFacade(Context appContext){
        this.appContext = appContext;
        goals = new ArrayList<>();
//        goals.add(new Goal("testing",convertFrequency("Daily"),convertQuantity(1),convertImportance(3),"dont know what unit is",null, 1, Constants.CACTUS_ADULT_HEALTHY));

        try {
            goals = SaveManager.loadGoalsFromFile(appContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        goals = provideDummyData(); //uncomment out above, comment out this line
//        if(goals.size() < 1)dummyData();
    }

    public static ModelFacade getSingleton(){
        return singleton;
    }

    public static ModelFacade getSingleton(Context appContext){
        singleton = new ModelFacade(appContext);
        return singleton;
    }

    public void setGoals(ArrayList<Goal> goals) {
        this.goals = goals;
    }

    public ArrayList<Goal> getGoals(){
        return goals;
    }

    public void addGoal(String name, String freq, int quantity,
                        int importance, String unit, Date startDate, double goalAmount, String plantType){
        goals.add(new Goal(name,convertFrequency(freq),convertQuantity(quantity),convertImportance(importance),unit,startDate, goalAmount, plantType));
        SaveManager.writeGoalsToFile(this.appContext, goals);
    }

    public void removeGoal(Goal goal){
        goals.remove(goal);
    }

    public void changeGoalName(Goal goal, String newName){
        goal.setName(newName);
    }

    public double[] getProgress(Goal goal, int index){
        double data[] = goal.getData();
        return data;
    }

    public void addUpdate(Goal goal, Date date, double amount){
        goal.addUpdate(date,amount);
    }

    public void removeUpdate(Goal goal, int timeIndex){
        goal.removeUpdate(timeIndex);
    }

    public Goal getGoal(int index){
        if(index >= 0 && index < goals.size()){
            return goals.get(index);
        }else{
            return null;
        }
    }

    public Goal.Frequency getFrequency(int index){
        return goals.get(index).getFrequency();
    }

    public ArrayList<String> getGoalNames(){
        ArrayList<String> toReturn = new ArrayList<>();
        for(int i = 0; i < goals.size(); i++){
            toReturn.add(goals.get(i).getName());
        }
        return toReturn;
    }

    public void ChangeImportance (Goal goal, int importance){
        goal.setImportance(convertImportance(importance));
    }

    public void ChangeFrequency (Goal goal, String freq){
        goal.setFrequency(convertFrequency(freq));
    }

    private Goal.Importance convertImportance(int importance){
        if(importance == 1){
            return Goal.Importance.ONE_STAR;
        }else if(importance == 2){
            return Goal.Importance.TWO_STAR;
        }else if(importance == 3){
            return Goal.Importance.THREE_STAR;
        }else if(importance == 4){
            return Goal.Importance.FOUR_STAR;
        }else{
            return Goal.Importance.FIVE_STAR;
        }
    }

    private Goal.Frequency convertFrequency (String freq){
        if(freq.equals("Daily")){
            return Goal.Frequency.DAILY;
        }else if(freq.equals("Weekly")){
            return Goal.Frequency.WEEKLY;
        }else{//monthly
            return Goal.Frequency.MONTHLY;
        }
    }
    private Goal.Quantity convertQuantity(int quantity){
        if(quantity==1){
            return Goal.Quantity.BINARY;
        }else{
            return Goal.Quantity.NUMERIC;
        }
    }
    //public Goal(String name, Frequency frequency, Quantity quantity, Importance importance,
    //String unit, Date startDate, double goalAmount, String plantType){
    //
    private ArrayList<Goal> provideDummyData(){
        ArrayList<Goal> dummy = new ArrayList<>();
        long day = 86400000; //milliseconds in a day
        //add one goal with three updates
        long time1 = new Date().getTime();
        long time2 = 4*day; /// x days ago;
        Date date1 = new Date( time1 - time2);// date was 70 days ago
        Goal goal1 = new Goal("Small daily Binary2", Goal.Frequency.DAILY, Goal.Quantity.BINARY, Goal.Importance.THREE_STAR, "times", date1, 1, Constants.CACTUS);


        Date date11 = new Date(date1.getTime()); //input on start date

        Date date12 = new Date(date1.getTime() + 1*day);//input one day after start date

        Date date13 = new Date(date1.getTime() + 2*day);//input two days after start date

        goal1.addUpdate(date11,1);
        goal1.addUpdate(date12,1);
        goal1.addUpdate(date13,0);

        //end goal one


        Date date2 = new Date( new Date().getTime() - 10*day);// date was Ten days ago
        Goal goal2 = new Goal(" Daily numberic", Goal.Frequency.DAILY, Goal.Quantity.NUMERIC, Goal.Importance.FIVE_STAR, "pounds", date2, 50, Constants.TREE);


        Date date21 = new Date(date2.getTime()); //input on start date

        Date date22 = new Date(date2.getTime() + 1*day);//input one day after start date

        Date date23 = new Date(date2.getTime() + 2*day);//input two days after start date

        goal2.addUpdate(date21,30);
        goal2.addUpdate(date22,45);
        goal2.addUpdate(date23,49);
        //end goal two
        dummy.add(goal1);
        dummy.add(goal2);
        return dummy;
    }

    private void dummyData(){
        long day = 86400000;
        Date date1 = new Date( new Date().getTime() - 365*day);
        goals.add(new Goal("Daily Binary", Goal.Frequency.DAILY, Goal.Quantity.BINARY, Goal.Importance.THREE_STAR, "", date1, 1, Constants.FLOWER));
        goals.add(new Goal("Daily Numeric", Goal.Frequency.DAILY, Goal.Quantity.NUMERIC, Goal.Importance.ONE_STAR, "lbs", date1, 2, Constants.FLOWER));
        goals.add(new Goal("Weekly Binary", Goal.Frequency.WEEKLY, Goal.Quantity.BINARY, Goal.Importance.FIVE_STAR, "", date1, 1, Constants.CACTUS));
        goals.add(new Goal("Weekly Numeric", Goal.Frequency.WEEKLY, Goal.Quantity.NUMERIC, Goal.Importance.FIVE_STAR, "lbs", date1, 3, Constants.CACTUS));
        goals.add(new Goal("Monthly Binary", Goal.Frequency.MONTHLY, Goal.Quantity.BINARY, Goal.Importance.FIVE_STAR, "", date1, 1, Constants.TREE));
        goals.add(new Goal("Monthly Numeric", Goal.Frequency.MONTHLY, Goal.Quantity.NUMERIC, Goal.Importance.FIVE_STAR, "lbs", date1, 20, Constants.TREE));
        for (Goal goal: provideDummyData()) {
            goals.add(goal);
        }
        SaveManager.writeGoalsToFile(this.appContext, goals);
    }
}
