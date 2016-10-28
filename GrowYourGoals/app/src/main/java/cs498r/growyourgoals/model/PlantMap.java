package cs498r.growyourgoals.model;

import java.util.AbstractMap;
import java.util.HashMap;

/**
 * Created by Nate on 7/8/16.
 */
public class PlantMap extends HashMap<String, Integer> {
    public PlantMap(AbstractMap.SimpleEntry[] plant_image_pairs){
        for(AbstractMap.SimpleEntry plant_image_pair : plant_image_pairs){
            this.put((String )plant_image_pair.getKey(), (int)plant_image_pair.getValue());
        }
    }
}
