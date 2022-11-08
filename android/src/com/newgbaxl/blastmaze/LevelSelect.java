package com.newgbaxl.blastmaze;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LevelSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelSelect extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LevelSelect() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LevelSelect.
     */
    // TODO: Rename and change types and number of parameters
    public static LevelSelect newInstance(String param1, String param2) {
        LevelSelect fragment = new LevelSelect();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_level_select, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        //set simple contents of listView
        ListView level_list = (ListView) getActivity().findViewById(R.id.list_levels);
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 1; i <= 20; ++i)
            nums.add(i);
        ArrayAdapter<Integer> nums_adapter = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_list_item_1, nums);
        level_list.setAdapter(nums_adapter);

        //Create and set OnItemClickListener
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> listView,
                                            View itemView,
                                            int position,
                                            long id) {
                        startGame((int) id);
                        //Toast.makeText(getActivity(), "Item No: " + position, Toast.LENGTH_SHORT).show();
                    }
                };
        level_list.setOnItemClickListener(itemClickListener);
    }

    void startGame(int level)
    {

        // TODO: get Car/Weapon/Coins from player config database
        Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
        i3.putExtra("Car", 0);
        i3.putExtra("Weapon", 0);
        i3.putExtra("Level", level);
        i3.putExtra("Money", PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("currencyAmnt",0));
        startActivity(i3);
    }
}