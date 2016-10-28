package cs498r.growyourgoals.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Justin on 6/28/2016.
 */
public class Goal implements Comparable<Goal>{

    public enum Frequency{DAILY, WEEKLY, MONTHLY }
    public enum Quantity{BINARY, NUMERIC}
    public enum Importance {ONE_STAR, TWO_STAR, THREE_STAR, FOUR_STAR, FIVE_STAR}
    private static int DAYS_IN_WEEK = 7;
    private static int DAYS_IN_MONTH = 30;
    static long day = 86400000;

    private String name = "";
    private Frequency frequency;
    private Quantity quantity;
    private Importance importance;
    private String unit;
    private double goalAmount;
    private String plantType;
    private Update latest;

    private SortedSet<Update> updates = new TreeSet<Update>();
    Date startDate = new Date();

    public Goal(String name, Frequency frequency, Quantity quantity, Importance importance,
                String unit, Date startDate, double goalAmount, String plantType){
        this.name = name;
        this.frequency = frequency;
        this.quantity = quantity;
        this.importance = importance;
        this.goalAmount = goalAmount;
        this.plantType = plantType;
        if(quantity == Quantity.BINARY){
            this.unit = null;
        }else{
            this.unit = unit;
        }
        if(startDate == null){
            this.startDate = new Date();
        }else{
            this.startDate = startDate;
        }
        this.plantType = plantType;
    }

    public Goal(JSONObject json) throws JSONException{
        this.name = json.getString("name");
        this.startDate = new Date(json.getLong("startDate"));
        try{
            this.unit = json.getString("unit");
        }
        catch(Exception e){
            this.unit = "";
        }
        this.plantType = json.getString("plantType");
        this.goalAmount = json.getDouble("goalAmount");
        String freqString = json.getString("frequency");
        switch(freqString){
            case "daily":
                this.frequency = Frequency.DAILY;
                break;
            case "weekly":
                this.frequency = Frequency.WEEKLY;
                break;
            case "monthly":
                this.frequency = Frequency.MONTHLY;
                break;
            default:
                System.err.println("frequency deserialization issue. Input: " + freqString);
        }
        String quanString = json.getString("quantity");
        switch(quanString){
            case "binary":
                this.quantity = Quantity.BINARY;
                break;
            case "numeric":
                this.quantity = Quantity.NUMERIC;
                break;
            default:
                System.err.println("quantity deserialization issue. Input: " + quanString);
        }
        String impoString = json.getString("importance");
        switch(impoString){
            case "one":
                this.importance = Importance.ONE_STAR;
                break;
            case "two":
                this.importance = Importance.TWO_STAR;
                break;
            case "three":
                this.importance = Importance.THREE_STAR;
                break;
            case "four":
                this.importance = Importance.FOUR_STAR;
                break;
            case "five":
                this.importance = Importance.FIVE_STAR;
                break;
            default:
                System.err.println("importance deserialization issue. Input: " + impoString);
        }
        JSONArray updatesJSON = json.getJSONArray("updates");
        for(int i = 0; i < updatesJSON.length();i++){
            try {
                JSONObject updateJSON = updatesJSON.getJSONObject(i);
                int date = updateJSON.getInt("date");
                int period = updateJSON.getInt("period");
                double amount = updateJSON.getDouble("amount");
                updates.add(new Update(new Date(date),amount,period));
            }
            catch (JSONException e){

            }
        }

    }

    public JSONObject toJson() throws JSONException{
        JSONObject json = new JSONObject();
        json.put("startDate",startDate.getTime());
        json.put("name",name);
        json.put("unit",unit);
        json.put("goalAmount", goalAmount);
        json.put("plantType",plantType);
        String freqString = "";
        switch(frequency){
            case DAILY:
                freqString = "daily";
                break;
            case WEEKLY:
                freqString = "weekly";
                break;
            case MONTHLY:
                freqString = "monthly";
                break;
            default:
                System.err.println("frequency serialization issue, shouldn't happen");
        }
        json.put("frequency",freqString);
        String quanString = "";
        switch(quantity){
            case BINARY:
                quanString = "binary";
                break;
            case NUMERIC:
                quanString = "numeric";
                break;
            default:
                System.err.println("quantity serialization issue, shouldn't happen");
        }
        json.put("quantity",quanString);
        String impoString = "";
        switch(importance){
            case ONE_STAR:
                impoString = "one";
                break;
            case TWO_STAR:
                impoString = "two";
                break;
            case THREE_STAR:
                impoString = "three";
                break;
            case FOUR_STAR:
                impoString = "four";
                break;
            case FIVE_STAR:
                impoString = "five";
                break;
            default:
                System.err.println("importance serialization issue, shouldn't happen");
        }
        json.put("importance",impoString);

        JSONArray updateJSON = new JSONArray();
        for (Update update : updates) {
            updateJSON.put(update.toJson());
        }
        json.put("updates",updateJSON);
        return json;
    }

    public void addUpdate(Date date, double amount){
        int daysSinceStart = getDayDifference(startDate,date);
        int period;
        switch(frequency){
            case WEEKLY:
                period = daysSinceStart/DAYS_IN_WEEK;
                break;
            case MONTHLY:
                period = daysSinceStart/DAYS_IN_MONTH;
                break;
            default:
                period = daysSinceStart;
        }
        Update newUpdate = new Update(date,amount,period);
        int daysToPresent = getDayDifference(newUpdate.getDate(), new Date());
        if(this.latest ==null){
            this.latest = newUpdate;
        }else {
            int latestDaysToPresent = getDayDifference(this.latest.getDate(), new Date());
            if (daysToPresent <= latestDaysToPresent) {
                latest = newUpdate;
            }
        }
        if(this.containsPeriodUpdate(period)){
            System.err.println("Update already exists for period: " + period);
        }
        updates.add(newUpdate);
    }
    public int getLongestStreak (){
        double[] dataArray = getData();
        int streak =0;
        int maxStreak=0;
            for(int i =0; i <dataArray.length; i++) {
                //if the goal is binary the amount will always be one
                if (dataArray[i] >= this.getGoalAmount()){// they reported and they met their goal
                    streak++;
                }else{ // they haven't reported or they did and they didn't meet the goal.
                    streak=0;
                }
                if(streak > maxStreak){
                    maxStreak = streak;
                }
            }

        return maxStreak;
    }


    public double getOverallHealth(){
        double[] data = this.getData();
        int periods = data.length;
        double expected = periods * goalAmount;
        if (expected == 0){
            return 0;
        }
        double hits = 0;
        for(int i = 0; i < periods; i++){
            if(data[i] > 0) {
                hits += data[i];
            }
        }
        return hits/expected;
    }

    public double getLastDaysHealth(int days){
        double[] data = this.getData();
        int size = data.length;
        if(size == 0){
            return 0;
        }
        double denominator = 0;
        double numerator = 0;
        int index = size - 1;//set index to last index in data
        while(index >= 0 && index + days >= size){
            if(data[index] > 0){
                numerator += data[index];
            }
            denominator += goalAmount;//goalAmount should be 1 for binary goals
            index--;
        }
        return numerator/denominator;
    }

    public double getHealth(){
        if(getData().length < 2){
            return .75;
        }
        double overall = this.getOverallHealth();
        double lastFive = this.getLastDaysHealth(5);
        double lastTwo = this.getLastDaysHealth(2);
        return Math.min(.4 * overall + .5 * lastFive + .1 * lastTwo,1);
    }

    public Date getStartDate(){
        return startDate;
    }

    public void removeUpdate(int period){
        Iterator<Update> iterator = updates.iterator();
        while(iterator.hasNext()){
            Update update = iterator.next();
            if(update.getPeriod() == period){
                iterator.remove();
            }
        }
    }

    public boolean containsPeriodUpdate(int period){
        Iterator<Update> iterator = updates.iterator();
        while(iterator.hasNext()){
            Update update = iterator.next();
            if(update.getPeriod() == period){
                return true;
            }
        }
        return false;
    }

    public int getWeekDifference(Date start, Date other){
        int weeksBack = 0;
        Date temp = start;
        int i = 1;
        while(temp.compareTo(other) < 0){
            weeksBack +=1;
            temp = new Date (start.getTime() + i*7*day);
            i++;
        }
        return weeksBack;
    }

    public int getMonthDifference(Date start, Date other){
        int monthsBack = 0;
        int checkMonth = start.getMonth();
        int checkYear = start.getYear();
        int otherMonth = other.getMonth();
        int otherYear = other.getYear();
        while(!(checkMonth == otherMonth && checkYear == otherYear)){
            monthsBack +=1;
            checkMonth++;
            if(checkMonth >= 12){
                checkYear++;
                checkMonth = 0;
            }
        }
        return monthsBack;
    }

    public double[] getData(){
        double[] toReturn;
        int periods;
        if(this.frequency == Frequency.DAILY){
            periods = getDayDifference(startDate, new Date()) +1;
        } else if (this.frequency == Frequency.WEEKLY){
            periods = getWeekDifference(startDate, new Date()) +1;
        } else{
            periods = getMonthDifference(startDate, new Date()) +1;
        }
        toReturn = new double[periods];
        Iterator<Update> iterator = updates.iterator();
        for(int i = 0; i < toReturn.length; i++){
            toReturn[i] = -1;
        }
        while(iterator.hasNext()){
            Update update = iterator.next();
            toReturn[update.getPeriod()] = update.getAmount();
        }
        return toReturn;
    }

    public int getDayDifference(Date d1, Date d2){
        return (int) Math.round((d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }

    /*
    pre: period must be between 0 and the current period inclusive.
     */
    public Date getDateFromPeriod(int period){
        int i = 0;
        if(i < 0){
            return null;
        }
        while(true){
            Date tryDate = new Date( startDate.getTime() + i * day); //86400000 milliseconds in a day
            int daysSinceStart = getDayDifference(startDate,tryDate);
            int tryperiod;
            switch(frequency){
                case WEEKLY:
                    tryperiod = i/DAYS_IN_WEEK;
                    break;
                case MONTHLY:
                    tryperiod = i/DAYS_IN_MONTH;
                    break;
                default:
                    tryperiod = i;
            }
            if(tryperiod == period){
                return tryDate;
            }
            i++;
        }
    }


    @Override
    public int compareTo(Goal goal) {
        return this.getStartDate().compareTo(goal.getStartDate());
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public Importance getImportance() {
        return importance;
    }

    public void setImportance(Importance importance) {
        this.importance = importance;
    }

    public String getPlantType() {
        return plantType;
    }

    public double getGoalAmount() {
        return goalAmount;
    }

    public void setPlantType(String plantType) {
        this.plantType = plantType;
    }

    public void setGoalAmount(double goalAmount) {
        this.goalAmount = goalAmount;
    }

    public SortedSet<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(SortedSet<Update> updates) {
        this.updates = updates;
    }


    public Update getLatest() {
        return latest;
    }

    public void setLatest(Update latest) {
        this.latest = latest;
    }

    public String getUnit() {
        return unit;
    }

}
