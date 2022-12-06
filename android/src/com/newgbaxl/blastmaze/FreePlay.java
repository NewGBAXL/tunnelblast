package com.newgbaxl.blastmaze;

import android.content.Intent;
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
        timer.setHint(Integer.toString(GlobalVars.timer));
        bombs.setHint(Integer.toString(GlobalVars.bombs));
        enemies.setHint(Integer.toString(GlobalVars.enemies));
        walls.setHint(Integer.toString(GlobalVars.walls));
        setSelectedMode();


        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickStart(view);
            }
        });

        binding.evadeSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEvade(view);
            }
        });

        binding.huntSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickHunt(view);
            }
        });

        binding.coinSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickCoin(view);
            }
        });

        binding.bombSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBomb(view);
            }
        });

        binding.wallSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickWall(view);
            }
        });
    }
    private int editPref(EditText text, int def, int min, int max) {
        if (text.getText().toString().equals("")) {
            return def;
        }
        int val = Integer.parseInt(text.getText().toString());
        if (val < min || val > max) {
            val = def;
        }
        return val;
    }
    public void onClickStart(View view) {
        GlobalVars.timer=editPref(timer, GlobalVars.timer, 1, 999);
        GlobalVars.bombs=editPref(bombs,GlobalVars.bombs,0,99);
        GlobalVars.enemies=editPref(enemies,GlobalVars.enemies,0,10);
        GlobalVars.walls=editPref(walls,GlobalVars.walls,0,99);

        //every time these vars are updated with UI change file variables
        Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
        i3.putExtra("Timer", GlobalVars.timer);
        i3.putExtra("Bombs", GlobalVars.timer);
        i3.putExtra("Walls", GlobalVars.walls);
        i3.putExtra("Enemies",GlobalVars.enemies);
        startActivity(i3);
    }

    public void onClickEvade(View view) {
        GlobalVars.mode=0;
    }

    public void onClickHunt(View view) {
        GlobalVars.mode=1;
    }

    public void onClickCoin(View view) {
        GlobalVars.mode=2;
    }

    public void onClickBomb(View view) {
        GlobalVars.mode=3;
    }

    public void onClickWall(View view) {
        GlobalVars.mode=4;
    }

    public void setSelectedMode() {
        switch(GlobalVars.mode) {
            case 1:
                binding.huntSelect.setChecked(true);
                return;
            case 2:
                binding.coinSelect.setChecked(true);
                return;
            case 3:
                binding.bombSelect.setChecked(true);
                return;
            case 4:
                binding.wallSelect.setChecked(true);
                return;
            default:
                binding.evadeSelect.setChecked(true);
                return;
        }
    }
}