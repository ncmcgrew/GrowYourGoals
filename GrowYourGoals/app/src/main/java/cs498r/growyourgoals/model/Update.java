package cs498r.growyourgoals.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Justin on 6/28/2016.
 */
public class Update implements Comparable<Update>{
    Date date = new Date();
    double amount;
    int period;

    public Update(Date date, double amount, int period){
        this.date = date;
        this.amount = amount;
        this.period = period;
    }

    public Update(JSONObject json)throws JSONException{
        this.date = new Date(json.getLong("date"));
        this.amount = json.getDouble("amount");
        this.period = json.getInt("period");
    }

    public JSONObject toJson()throws JSONException{
        JSONObject json = new JSONObject();
        json.put("date",date.getTime());
        json.put("amount",amount);
        json.put("period",period);
        return json;
    }

    public double getAmount(){
        return amount;
    }

    public Date getDate(){
        return date;
    }

    public int getPeriod(){
        return period;
    }



    @Override
    public int compareTo(Update update) {
        if(this.period == update.period){
            System.err.println("Same period for update: "+ this.period);
            return 0;
        }
        return date.compareTo(update.getDate());
    }
}
