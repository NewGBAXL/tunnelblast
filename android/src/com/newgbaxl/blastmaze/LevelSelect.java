package com.newgbaxl.blastmaze;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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

    public Button buttons[] = new Button[20];
    public ImageView medals[] = new ImageView[20];

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

        buttons[0] =  binding.buttonlvl1;
        buttons[1] =  binding.buttonlvl2;
        buttons[2] =  binding.buttonlvl3;
        buttons[3] =  binding.buttonlvl4;
        buttons[4] =  binding.buttonlvl5;
        buttons[5] =  binding.buttonlvl6;
        buttons[6] =  binding.buttonlvl7;
        buttons[7] =  binding.buttonlvl8;
        buttons[8] =  binding.buttonlvl9;
        buttons[9] =  binding.buttonlvl10;
        buttons[10] =  binding.buttonlvl11;
        buttons[11] =  binding.buttonlvl12;
        buttons[12] =  binding.buttonlvl13;
        buttons[13] =  binding.buttonlvl14;
        buttons[14] =  binding.buttonlvl15;
        buttons[15] =  binding.buttonlvl16;
        buttons[16] =  binding.buttonlvl17;
        buttons[17] =  binding.buttonlvl18;
        buttons[18] =  binding.buttonlvl19;
        buttons[19] =  binding.buttonlvl20;

        medals[0] =  binding.ranklvl1;
        medals[1] =  binding.ranklvl2;
        medals[2] =  binding.ranklvl3;
        medals[3] =  binding.ranklvl4;
        medals[4] =  binding.ranklvl5;
        medals[5] =  binding.ranklvl6;
        medals[6] =  binding.ranklvl7;
        medals[7] =  binding.ranklvl8;
        medals[8] =  binding.ranklvl9;
        medals[9] =  binding.ranklvl10;
        medals[10] =  binding.ranklvl11;
        medals[11] =  binding.ranklvl12;
        medals[12] =  binding.ranklvl13;
        medals[13] =  binding.ranklvl14;
        medals[14] =  binding.ranklvl15;
        medals[15] =  binding.ranklvl16;
        medals[16] =  binding.ranklvl17;
        medals[17] =  binding.ranklvl18;
        medals[18] =  binding.ranklvl19;
        medals[19] =  binding.ranklvl20;

        update();
        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 20; ++i)
                    GlobalVars.globalRanks[i] = 0;
                    //ranks[i] = 0;
                update();
            }
        });

        binding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 20; ++i)
                    GlobalVars.globalRanks[i] = 1;
                    //ranks[i] = 1;
                update();
            }
        });

        binding.button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < 20; ++i)
                    GlobalVars.globalRanks[i] = 4;
                //ranks[i] = 1;
                update();
            }
        });

        for (int i = 0; i < 20; ++i)
        {
            int num = i;
            buttons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
                    i3.putExtra("Level", num);
                    getActivity().startActivity(i3);
                }
            });
        }
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
        if (GlobalVars.globalRanks[0] == 0)
            GlobalVars.globalRanks[0] = 1;

        /*for (int i = 0; i < 20; ++i)
        {
            //set the color and clickability of a button if it is 0, not 0
            //set the rank emblem if rank is 2-5
        }*/

        //int startedLvl = 1; //set to clicked button #
        //start game

        //ranks[startedLvl] = 0; //getResult()

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
        for (int i = 0; i < 20; ++i)
        {
            buttons[i].setText((GlobalVars.globalRanks[i] != 0)?GAME_MODES[modeTypes[i]]:"LOCKED");
            buttons[i].setBackgroundColor((GlobalVars.globalRanks[i] != 0)?Color.BLUE:getResources().getColor(R.color.silver2));
            buttons[i].setClickable(GlobalVars.globalRanks[i] > 0);

            medals[i].setVisibility((GlobalVars.globalRanks[i] > 1)?View.VISIBLE:View.INVISIBLE);
            switch (GlobalVars.globalRanks[i])
            {
                case 2:
                    medals[i].setImageResource(R.drawable.bronze);
                    break;
                case 3:
                    medals[i].setImageResource(R.drawable.silver);
                    break;
                case 4:
                    medals[i].setImageResource(R.drawable.gold);
                    break;
                case 5:
                    medals[i].setImageResource(R.drawable.platinum);
                    break;
            }
        }

        //unlock first scenario
        if (GlobalVars.globalRanks[0] == 0)
            GlobalVars.globalRanks[0] = 1;

        //todo:remove the colors platinum,gold,bronze; rename silver to locked

    }
}