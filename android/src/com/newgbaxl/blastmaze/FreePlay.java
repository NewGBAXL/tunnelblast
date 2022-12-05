package com.newgbaxl.blastmaze;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.newgbaxl.blastmaze.databinding.FragmentFreePlayBinding;
import com.newgbaxl.blastmaze.databinding.FragmentLevelSelectBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FreePlay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FreePlay extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentFreePlayBinding binding;
    private RadioGroup modeSelect;
    private EditText timer;
    private EditText enemies;
    private  EditText bombs;
    private EditText walls;

    public FreePlay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FreePlay.
     */
    // TODO: Rename and change types and number of parameters
    public static FreePlay newInstance(String param1, String param2) {
        FreePlay fragment = new FreePlay();
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
        binding = FragmentFreePlayBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        modeSelect = binding.modeSelect;
        timer = binding.editTime;
        bombs = binding.editBombs;
        enemies = binding.editEnemies;
        walls = binding.editWalls;
        timer.setHint(GlobalVars.timer);
        bombs.setHint(GlobalVars.bombs);
        enemies.setHint(GlobalVars.enemies);
        walls.setHint(GlobalVars.walls);
    }
    private int editPref(EditText text, int def, int min, int max) {
        int val = Integer.parseInt(text.getText().toString());
        if (val < min || val > max) {
            val = def;
        }
        return val;
    }
    public void onStart(View view) {
        GlobalVars.timer=editPref(timer, 90, 1, 999);
        GlobalVars.bombs=editPref(bombs,15,0,99);
        GlobalVars.enemies=editPref(enemies,1,0,10);
        GlobalVars.walls=editPref(walls,15,0,99);
        NavHostFragment.findNavController(FreePlay.this).navigate(R.id.action_freePlay_to_modeSelect);
    }
}