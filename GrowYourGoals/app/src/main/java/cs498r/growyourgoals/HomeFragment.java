package cs498r.growyourgoals;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    private AdapterView.OnItemClickListener selectGoal = new AdapterView.OnItemClickListener(){

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getContext(), ViewGoal.class);
            System.out.println("making intent with goalNum " + position);
            //Intent intent = new Intent(getContext(), UpdatePage.class);
            intent.putExtra("goalNum", position);
            startActivity(intent);
        }
    };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        try {
            ArrayList<String> list = ModelFacade.getSingleton().getGoalNames();//this is for testing
            ListView view = (ListView) root.findViewById(R.id.listView);
            view.setOnItemClickListener(selectGoal);
            //this line keeps going red
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(),R.layout.activity_listview);
            adapter.addAll(list);
            view.setAdapter(adapter);
        }
        catch (Exception e){

        }
        return root;
    }

}
