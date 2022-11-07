package com.newgbaxl.blastmaze;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewbinding.ViewBinding;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.newgbaxl.blastmaze.Objects.CarSkin;
import com.newgbaxl.blastmaze.databinding.FragmentSettingsBinding;
import com.newgbaxl.blastmaze.databinding.FragmentStoreBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Store#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Store extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentStoreBinding binding;

    //Probably should make it all list-based, but I don't want to change too many things
    public CarSkin carSkins[] = new CarSkin[9];
    public List<CarSkin> getFromDatabase = new ArrayList<>();
    //move these to Persistant Storage!
    public int selectedSkin = 0;

    public Store() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Store.
     */
    // TODO: Rename and change types and number of parameters
    public static Store newInstance(String param1, String param2) {
        Store fragment = new Store();
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
        binding = FragmentStoreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper db = new DatabaseHelper(getActivity());
        getFromDatabase = db.getAllCars();
        if (getFromDatabase.size() < 1)
        {
            for (int i = 0; i < 9; ++i)
                db.insertCar(true,false,i*10);
        }
        for (int i = 0; i < carSkins.length && i < getFromDatabase.size(); ++i)
            carSkins[i] = getFromDatabase.get(i);

        binding.displayCurrency.setText("$" + PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("currencyAmnt",0));
        binding.purchaseStore.setText(carSkins[selectedSkin].purchaced?"PURCHASED":"PURCHASE");

        if(selectedSkin == PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("equipped_car",-1))
            binding.purchaseStore.setText("EQUIPPED");

        binding.buttonBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                NavHostFragment.findNavController(Store.this)
                        .navigate(R.id.action_store_to_FirstFragment);
            }
        });
        binding.addCurrency.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //add currency
                //todo: change currency to db so that it can be accessed/modified by game
                PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("currencyAmnt",PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("currencyAmnt",0) + 1).apply();
                binding.displayCurrency.setText("$" + PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("currencyAmnt",0));
            }
        });

        binding.arrowLeftStore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectedSkin = (selectedSkin>0)?selectedSkin-1:carSkins.length-1;
                if (carSkins[selectedSkin].unlocked)
                    displaySkin();
                else
                    onClick(view);
            }
        });

        binding.arrowRightStore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                selectedSkin = (selectedSkin<carSkins.length-1)?selectedSkin+1: 0;
                if (carSkins[selectedSkin].unlocked)
                    displaySkin();
                else
                    onClick(view);
            }
        });

        binding.purchaseStore.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                int money = PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("currencyAmnt",0);
                if (carSkins[selectedSkin].purchaced)
                {
                    binding.purchaseStore.setText("EQUIPPED");
                    PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("equipped_car",selectedSkin).apply();
                }
                else
                {
                    if (money >= carSkins[selectedSkin].price)
                    {
                        PreferenceManager.getDefaultSharedPreferences(getActivity()).edit().putInt("currencyAmnt",money - carSkins[selectedSkin].price).apply();
                        money-=carSkins[selectedSkin].price;
                        binding.displayCurrency.setText("$" + money);
                        binding.purchaseStore.setText("EQUIP");
                        carSkins[selectedSkin].purchaced = true;
                        DatabaseHelper db = new DatabaseHelper(getActivity());
                        db.updateCar(carSkins[selectedSkin]);
                    }
                    else
                    {
                        Toast.makeText(getContext(),
                                "You do not have enough coins.\n Play the game more or -- to earn more.", Toast.LENGTH_SHORT);
                    }
                }
            }
        });

    }

    public void displaySkin(){
        binding.purchaseStore.setText(carSkins[selectedSkin].purchaced?"PURCHASED":"PURCHASE");
        if(selectedSkin == PreferenceManager.getDefaultSharedPreferences(getActivity()).getInt("equipped_car",-1)) {
            binding.purchaseStore.setText("EQUIPPED");
        }
        binding.priceStore.setText("Price: $" + String.valueOf(carSkins[selectedSkin].price));

        switch (selectedSkin)
        {
            case 0:
                binding.carSkinView.setImageResource(R.drawable.car1);
                break;
            case 1:
                binding.carSkinView.setImageResource(R.drawable.car2);
                break;
            case 2:
                binding.carSkinView.setImageResource(R.drawable.car3);
                break;
            case 3:
                binding.carSkinView.setImageResource(R.drawable.car4);
                break;
            case 4:
                binding.carSkinView.setImageResource(R.drawable.car5);
                break;
            case 5:
                binding.carSkinView.setImageResource(R.drawable.car6);
                break;
            case 6:
                binding.carSkinView.setImageResource(R.drawable.car7);
                break;
            case 7:
                binding.carSkinView.setImageResource(R.drawable.car8);
                break;
            case 8:
                binding.carSkinView.setImageResource(R.drawable.car9);
                break;
        }
    }
}