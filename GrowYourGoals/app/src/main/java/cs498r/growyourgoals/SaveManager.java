package cs498r.growyourgoals;

import android.content.Context;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;

import cs498r.growyourgoals.model.Constants;
import cs498r.growyourgoals.model.Goal;

/**
 * Created by Justin on 7/1/2016.
 */
public class SaveManager{

    // context should be a reference to getApplicationContext(), which must be passed from an activity.
    public  static void writeGoalsToFile(Context context, ArrayList<Goal> goals){
        JSONArray jsonGoals = new JSONArray();
        try {
            for (Goal goal : goals){
                jsonGoals.put(goal.toJson());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(Constants.saveFileName, Context.MODE_PRIVATE);
//            String goalString = "";
//            for (Goal goal : goals){
//                goalString += goal.toJson().toString();
//            }

            outputStream.write(jsonGoals.toString().getBytes());

            outputStream.close();
        }
        catch (Exception e){
            System.err.print("failed to write to file");
        }
    }

    public static ArrayList<Goal> loadGoalsFromFile(Context context) throws IOException, JSONException {
        FileInputStream input = context.openFileInput(Constants.saveFileName);;
        InputStreamReader reader = new InputStreamReader(input);
        BufferedReader bufferedReader = new BufferedReader(reader);
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while((line = bufferedReader.readLine()) != null){
            stringBuilder.append(line);
        }

        ArrayList<Goal> goals = new ArrayList<>();

        JSONArray jsonGoals = new JSONArray(stringBuilder.toString());
        for(int i = 0; i < jsonGoals.length(); i++){
            JSONObject o = (JSONObject)jsonGoals.get(i);
            Goal goal = new Goal(o);
            goals.add(goal);
        }

        return goals;
    }

    /*public ModelFacade loadModelFacade()throws FileNotFoundException, IOException, JSONException{
        File folderPath = Environment.getExternalStorageDirectory();
        System.out.println("loading from: " + folderPath.toString());
        File mypath=new File(folderPath, "description.json");
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(mypath));
        char[] buf = new char[1024];
        int numRead=0;

        while((numRead=reader.read(buf)) != -1)
        {
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        String response = fileData.toString();
        JSONObject modelData = new JSONObject(response);
        return new ModelFacade();
        //return new ModelFacade(modelData);                // return this when method is built
    }*/
    /*public static void saveModelFacade(){
        ObjectOutput out;
        try {
            File outFile = new File(Environment.getExternalStorageDirectory(), "appSaveState.json");
            out = new ObjectOutputStream(new FileOutputStream(outFile));
            out.writeObject(ModelFacade.getSingleton().getGoals());
            out.close();
        } catch (Exception e) {e.printStackTrace();}
    }

    public static void loadModelFacade(){
        ObjectInput in;
        ArrayList<Goal> goals = new ArrayList<>();
        try {
            File inFile = new File(Environment.getExternalStorageDirectory(), "appSaveState.json");
            in = new ObjectInputStream(new FileInputStream(inFile));
            goals=(ArrayList<Goal>) in.readObject();
            in.close();
        } catch (Exception e) {e.printStackTrace();}
        ModelFacade.getSingleton().setGoals(goals);
    }*/

}
