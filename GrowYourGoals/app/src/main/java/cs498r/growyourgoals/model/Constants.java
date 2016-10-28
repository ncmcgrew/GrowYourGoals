package cs498r.growyourgoals.model;

import java.util.AbstractMap;

import cs498r.growyourgoals.R;

/**
 * Created by Nate on 7/6/2016.
 */
public class Constants {
    public static final String ADULT = "_adult";
    public static final String YOUNG = "_young";
    public static final String SEEDLING = "_seedling";
    public static final String SUPERHEALTHY = "_superhealthy";
    public static final String HEALTHY = "_healthy";
    public static final String SICK = "_sick";
    public static final String DYING = "_dying";

    public static final String CACTUS = "cactus";
    public static final String CACTUS_ADULT_SUPERHEALTHY = CACTUS+ADULT+SUPERHEALTHY;
    public static final String CACTUS_ADULT_HEALTHY =CACTUS+ADULT+HEALTHY;
    public static final String CACTUS_ADULT_SICK = CACTUS+ADULT+SICK;
    public static final String CACTUS_ADULT_DYING = CACTUS+ADULT+DYING;
    public static final String CACTUS_YOUNG_SUPERHEALTHY = CACTUS+YOUNG+SUPERHEALTHY;
    public static final String CACTUS_YOUNG_HEALTHY = CACTUS+YOUNG+HEALTHY;
    public static final String CACTUS_YOUNG_SICK = CACTUS+YOUNG+SICK;
    public static final String CACTUS_YOUNG_DYING = CACTUS+YOUNG+DYING;
    public static final String CACTUS_SEEDLING_HEALTHY = CACTUS+SEEDLING+HEALTHY;

    public static final String FLOWER = "flower";
    public static final String FLOWER_ADULT_SUPERHEALTHY = FLOWER+ADULT+SUPERHEALTHY;
    public static final String FLOWER_ADULT_HEALTHY = FLOWER+ADULT+HEALTHY;
    public static final String FLOWER_ADULT_SICK = FLOWER+ADULT+SICK;
    public static final String FLOWER_ADULT_DYING = FLOWER+ADULT+DYING;
    public static final String FLOWER_YOUNG_SUPERHEALTHY = FLOWER+YOUNG+SUPERHEALTHY;
    public static final String FLOWER_YOUNG_HEALTHY = FLOWER+YOUNG+HEALTHY;
    public static final String FLOWER_YOUNG_SICK = FLOWER+YOUNG+SICK;
    public static final String FLOWER_YOUNG_DYING = FLOWER+YOUNG+DYING;
    public static final String FLOWER_SEEDLING_HEALTHY = FLOWER+SEEDLING+HEALTHY;

    public static final String TREE = "tree";
    public static final String TREE_ADULT_SUPERHEALTHY = TREE+ADULT+SUPERHEALTHY;
    public static final String TREE_ADULT_HEALTHY = TREE+ADULT+HEALTHY;
    public static final String TREE_ADULT_SICK = TREE+ADULT+SICK;
    public static final String TREE_ADULT_DYING = TREE+ADULT+DYING;
    public static final String TREE_YOUNG_SUPERHEALTHY = TREE+YOUNG+SUPERHEALTHY;
    public static final String TREE_YOUNG_HEALTHY = TREE+YOUNG+HEALTHY;
    public static final String TREE_YOUNG_SICK = TREE+YOUNG+SICK;
    public static final String TREE_YOUNG_DYING = TREE+YOUNG+DYING;
    public static final String TREE_SEEDLING_HEALTHY = TREE+SEEDLING+HEALTHY;

    // As more kinds of plants are added, insert the plant's name in the BASIC_PLANT_NAMES array, as well as appropriate key-value pairs for the plant's stages
    // Only for images that go in ChoosePlant
    public static final String[] BASIC_PLANT_NAMES = {CACTUS, FLOWER, TREE};

    // Map the address of the image to the plant type
    private static AbstractMap.SimpleEntry[] plant_image_pairs = {
//-----------------------------CACTUS-----------------------------//
            new AbstractMap.SimpleEntry(CACTUS, R.drawable.cactus_adult_healthy),

            new AbstractMap.SimpleEntry(CACTUS_ADULT_SUPERHEALTHY, R.drawable.cactus_adult_superhealthy),
            new AbstractMap.SimpleEntry(CACTUS_ADULT_HEALTHY, R.drawable.cactus_adult_healthy),
            new AbstractMap.SimpleEntry(CACTUS_ADULT_SICK, R.drawable.cactus_adult_sick),
            new AbstractMap.SimpleEntry(CACTUS_ADULT_DYING, R.drawable.cactus_adult_dying),

            new AbstractMap.SimpleEntry(CACTUS_YOUNG_SUPERHEALTHY, R.drawable.cactus_young_superhealthy),
            new AbstractMap.SimpleEntry(CACTUS_YOUNG_HEALTHY, R.drawable.cactus_young_healthy),
            new AbstractMap.SimpleEntry(CACTUS_YOUNG_SICK, R.drawable.cactus_young_sick),
            new AbstractMap.SimpleEntry(CACTUS_YOUNG_DYING, R.drawable.cactus_young_dying),

            new AbstractMap.SimpleEntry(CACTUS_SEEDLING_HEALTHY, R.drawable.cactus_seedling_healthy),

//-----------------------------FLOWER-----------------------------//
            new AbstractMap.SimpleEntry(FLOWER, R.drawable.plant_flower),

            new AbstractMap.SimpleEntry(FLOWER_ADULT_SUPERHEALTHY, R.drawable.flower_adult_superhealthy),
            new AbstractMap.SimpleEntry(FLOWER_ADULT_HEALTHY, R.drawable.flower_adult_healthy),
            new AbstractMap.SimpleEntry(FLOWER_ADULT_SICK, R.drawable.flower_adult_sick),
            new AbstractMap.SimpleEntry(FLOWER_ADULT_DYING, R.drawable.flower_adult_dying),

            new AbstractMap.SimpleEntry(FLOWER_YOUNG_SUPERHEALTHY, R.drawable.flower_young_superhealthy),
            new AbstractMap.SimpleEntry(FLOWER_YOUNG_HEALTHY, R.drawable.flower_young_healthy),
            new AbstractMap.SimpleEntry(FLOWER_YOUNG_SICK, R.drawable.flower_young_sick),
            new AbstractMap.SimpleEntry(FLOWER_YOUNG_DYING, R.drawable.flower_young_dying),

            new AbstractMap.SimpleEntry(FLOWER_SEEDLING_HEALTHY, R.drawable.flower_seedling),

//-----------------------------TREE-----------------------------//
            new AbstractMap.SimpleEntry(TREE, R.drawable.tree_adult_healthy),

            new AbstractMap.SimpleEntry(TREE_ADULT_SUPERHEALTHY, R.drawable.tree_adult_superhealthy),
            new AbstractMap.SimpleEntry(TREE_ADULT_HEALTHY, R.drawable.tree_adult_healthy),
            new AbstractMap.SimpleEntry(TREE_ADULT_SICK, R.drawable.tree_adult_sick),
            new AbstractMap.SimpleEntry(TREE_ADULT_DYING, R.drawable.tree_adult_dying),

            new AbstractMap.SimpleEntry(TREE_YOUNG_SUPERHEALTHY, R.drawable.tree_young_superhealthy),
            new AbstractMap.SimpleEntry(TREE_YOUNG_HEALTHY, R.drawable.tree_young_healthy),
            new AbstractMap.SimpleEntry(TREE_YOUNG_SICK, R.drawable.tree_young_sick),
            new AbstractMap.SimpleEntry(TREE_YOUNG_DYING, R.drawable.tree_young_dying),

            new AbstractMap.SimpleEntry(TREE_SEEDLING_HEALTHY, R.drawable.tree_seedling_healthy)

    };

    public static final PlantMap PLANT_TYPES = new PlantMap(plant_image_pairs);
    public static final int garden_num_columns = 4;

    public static final String saveFileName = "appSaveState.json";
}