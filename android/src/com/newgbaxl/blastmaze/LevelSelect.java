package com.newgbaxl.blastmaze;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.newgbaxl.blastmaze.databinding.FragmentFirstBinding;
import com.newgbaxl.blastmaze.databinding.FragmentLevelSelectBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LevelSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelSelect extends Fragment
{
    private FragmentLevelSelectBinding binding;

    private static final String[] GAME_MODES =
    {
            "Evade",
            "Hunt",
            "Coin Collect",
            "Destroy",
            "Bomb Enemy",
            "Box in NPC",
            "God Mode",
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private byte[] ranks = new byte[20];
    private byte[] modeTypes = {0,1,3,0,4,1,0,1,4,0,3,1,0,1,4,0,5,1,0,2};

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
    public static LevelSelect newInstance(String param1, String param2)
    {
        LevelSelect fragment = new LevelSelect();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // or slightly better
        // buttons = new ArrayList<Button>(BUTTON_IDS.length);
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLevelSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 20; ++i)
                    ranks[i] = 0;
                update();
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 20; ++i)
                    ranks[i] = 1;
                update();
            }
        });

        update();

    }

    //rename this f'n
    public void whenAppFirstRun()
    {
        //0 locked
        //1 unlocked (Rank D)
        //2 Rank C
        //3 Rank B
        //4 Rank A
        //5 Rank S (optional)

        //unlock first scenario
        if (ranks[0] == 0)
            ranks[0] = 1;

        for (int i = 0; i < 20; ++i)
        {
            //set the color and clickability of a button if it is 0, not 0
            //set the rank emblem if rank is 2-5
        }

        int startedLvl = 1; //set to clicked button #
        //start game

        ranks[startedLvl] = 0; //getResult()

        //#af9500, #c9b037, #d7d7d7, #b4b4b4, #6a3805 and #ad8a56.
    }
    /*
    @Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.game_board_view);

    buttons = new ArrayList<Button>();
    // or slightly better
    // buttons = new ArrayList<Button>(BUTTON_IDS.length);
    for(int id : BUTTON_IDS) {
        Button button = (Button)findViewById(id);
        button.setOnClickListener(this); // maybe
        buttons.add(button);
    }
}
     */

    public void update()
    {
        binding.buttonlvl1.setText(GAME_MODES[modeTypes[0]]);
        binding.buttonlvl2.setText(GAME_MODES[modeTypes[1]]);
        binding.buttonlvl3.setText(GAME_MODES[modeTypes[2]]);
        binding.buttonlvl4.setText(GAME_MODES[modeTypes[3]]);
        binding.buttonlvl5.setText(GAME_MODES[modeTypes[4]]);
        binding.buttonlvl6.setText(GAME_MODES[modeTypes[5]]);
        binding.buttonlvl7.setText(GAME_MODES[modeTypes[6]]);
        binding.buttonlvl8.setText(GAME_MODES[modeTypes[7]]);
        binding.buttonlvl9.setText(GAME_MODES[modeTypes[8]]);
        binding.buttonlvl10.setText(GAME_MODES[modeTypes[9]]);
        binding.buttonlvl11.setText(GAME_MODES[modeTypes[10]]);
        binding.buttonlvl12.setText(GAME_MODES[modeTypes[11]]);
        binding.buttonlvl13.setText(GAME_MODES[modeTypes[12]]);
        binding.buttonlvl14.setText(GAME_MODES[modeTypes[13]]);
        binding.buttonlvl15.setText(GAME_MODES[modeTypes[14]]);
        binding.buttonlvl16.setText(GAME_MODES[modeTypes[15]]);
        binding.buttonlvl17.setText(GAME_MODES[modeTypes[16]]);
        binding.buttonlvl18.setText(GAME_MODES[modeTypes[17]]);
        binding.buttonlvl19.setText(GAME_MODES[modeTypes[18]]);

        int i = 19;
        binding.buttonlvl20.setText((ranks[i] != 0)?GAME_MODES[modeTypes[i]]:"LOCKED");
        binding.buttonlvl20.setBackgroundColor((ranks[i] != 0)?Color.BLUE:getResources().getColor(R.color.silver2));
        binding.buttonlvl20.setClickable(ranks[i] > 0);

        //todo: make loop for all buttons

        //unlock first scenario
        if (ranks[0] == 0)
            ranks[0] = 1;

        binding.buttonlvl1.setText(GAME_MODES[modeTypes[0]]);
        binding.buttonlvl1.setClickable(ranks[0] > 0);

        binding.buttonlvl5.setClickable(ranks[0] > 0);

        //todo:remove the colors platinum,gold,bronze; rename silver to locked

        //for (Button b : )
    }
}