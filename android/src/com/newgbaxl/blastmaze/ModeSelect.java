package com.newgbaxl.blastmaze;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newgbaxl.blastmaze.Objects.CarSkin;
import com.newgbaxl.blastmaze.databinding.FragmentModeSelectBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModeSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModeSelect extends Fragment
{

    private FragmentModeSelectBinding binding;
    int modeSelect = 1;
    int carSelect = 0;
    int weaponSelect = 0;

    public CarSkin carSkins[] = new CarSkin[9];
    public List<CarSkin> getFromDatabase = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    MediaPlayer mediaPlayer;

    public ModeSelect()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModeSelect.
     */
    // TODO: Rename and change types and number of parameters
    public static ModeSelect newInstance(String param1, String param2)
    {
        ModeSelect fragment = new ModeSelect();
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
        binding = FragmentModeSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        DatabaseHelper db = new DatabaseHelper(getActivity());
        getFromDatabase = db.getAllCars();
        if (getFromDatabase.size() < 1) {
            for (int i = 0; i < 9; ++i){
                db.insertCar(true,false,i*10);
            }
        }
        for (int i = 0; i < carSkins.length && i < getFromDatabase.size(); ++i)
            carSkins[i] = getFromDatabase.get(i);
        carSelect = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("equipped_car",0);
        displaySkin();

        binding.quickStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
                getActivity().startActivity(i3);
            }
        });

        binding.playGame.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startGame();
            }
        });

        binding.selectScenario.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                changeMode(1);
            }
        });

        binding.selectVs.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                changeMode(2);
            }
        });

        binding.selectLevel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                changeMode(3);
            }
        });

        binding.selectFreeplay.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                //todo: add navigation to Free Play (Eric)
                changeMode(4);
            }
        });

        binding.selectCarLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                carSelect = (carSelect >0)? carSelect -1:carSkins.length-1;
                if (carSkins[carSelect].purchaced) {
                    displaySkin();
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("equipped_car", carSelect).apply();
                } else
                    onClick(view);
            }
        });

        binding.selectCarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                carSelect = (carSelect <carSkins.length-1)? carSelect +1: 0;
                if (carSkins[carSelect].purchaced) {
                    displaySkin();
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("equipped_car", carSelect).apply();
                } else
                    onClick(view);
            }
        });

        binding.selectSpLeft.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
                startActivity(i3);
            }
        });

        binding.selectSpRight.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
                startActivity(i3);
            }
        });
    }

    void startGame()
    {
        if (modeSelect == 1)
        {
            //for all scenarios, while != completed, ++scenario;
            Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
            i3.putExtra("Car", carSelect);
            i3.putExtra("Weapon", weaponSelect);

            if (GlobalVars.globalRanks[0] == 0)
                GlobalVars.globalRanks[0]=1;

            int i = 0;
            while (i < 20 && GlobalVars.globalRanks[i] >= 1)
                ++i;
            --i;
            i3.putExtra("Level", i);

            startActivity(i3);
        }
        else if (modeSelect == 2)
        {
            NavHostFragment.findNavController(ModeSelect.this)
                    .navigate(R.id.action_modeSelect_to_vsSelect);
        }
        else if (modeSelect == 3)
        {
            NavHostFragment.findNavController(ModeSelect.this)
                    .navigate(R.id.action_modeSelect_to_levelSelect);
        }
        else if (modeSelect == 4)
        {
            NavHostFragment.findNavController(ModeSelect.this)
                    .navigate(R.id.action_modeSelect_to_freePlay);
        }
    }

    void changeMode(int switchTo)
    {
        switch (modeSelect)
        {
            case 1:
                binding.b1Left.setVisibility(View.INVISIBLE);
                binding.b1Right.setVisibility(View.INVISIBLE);
                break;
            case 2:
                binding.b2Left.setVisibility(View.INVISIBLE);
                binding.b2Right.setVisibility(View.INVISIBLE);
                break;
            case 3:
                binding.b3Left.setVisibility(View.INVISIBLE);
                binding.b3Right.setVisibility(View.INVISIBLE);
                break;
            default:
                binding.b4Left.setVisibility(View.INVISIBLE);
                binding.b4Right.setVisibility(View.INVISIBLE);
                break;
        }
        switch (switchTo)
        {
            case 1:
                binding.b1Left.setVisibility(View.VISIBLE);
                binding.b1Right.setVisibility(View.VISIBLE);
                break;
            case 2:
                binding.b2Left.setVisibility(View.VISIBLE);
                binding.b2Right.setVisibility(View.VISIBLE);
                break;
            case 3:
                binding.b3Left.setVisibility(View.VISIBLE);
                binding.b3Right.setVisibility(View.VISIBLE);
                break;
            default:
                binding.b4Left.setVisibility(View.VISIBLE);
                binding.b4Right.setVisibility(View.VISIBLE);
                break;
        }
        modeSelect = switchTo;
    }

    public void displaySkin(){
        switch (carSelect)
        {
            case 0:
                binding.carSkinModeSelView.setImageResource(R.drawable.car1);
                break;
            case 1:
                binding.carSkinModeSelView.setImageResource(R.drawable.car2);
                break;
            case 2:
                binding.carSkinModeSelView.setImageResource(R.drawable.car3);
                break;
            case 3:
                binding.carSkinModeSelView.setImageResource(R.drawable.car4);
                break;
            case 4:
                binding.carSkinModeSelView.setImageResource(R.drawable.car5);
                break;
            case 5:
                binding.carSkinModeSelView.setImageResource(R.drawable.car6);
                break;
            case 6:
                binding.carSkinModeSelView.setImageResource(R.drawable.car7);
                break;
            case 7:
                binding.carSkinModeSelView.setImageResource(R.drawable.car8);
                break;
            case 8:
                binding.carSkinModeSelView.setImageResource(R.drawable.car9);
                break;
        }
    }
}