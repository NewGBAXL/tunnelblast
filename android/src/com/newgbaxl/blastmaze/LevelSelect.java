package com.newgbaxl.blastmaze;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LevelSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LevelSelect extends Fragment
{
    /*private static final int[] BUTTON_IDS =
    {
            R.id.buttonOne,
            R.id.buttonTwo,
            R.id.buttonThree,
            R.id.buttonFour,
            R.id.buttonFive,
            R.id.buttonSix,
            R.id.buttonSeven,
            R.id.buttonEight,
            R.id.buttonMid,
    };*/

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private byte[] ranks = new byte[20];

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_level_select, container, false);
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
}