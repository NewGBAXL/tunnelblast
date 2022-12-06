package com.newgbaxl.blastmaze;

import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.newgbaxl.blastmaze.Objects.ArrowSwitch;
import com.newgbaxl.blastmaze.databinding.ActivityMainBinding;
import com.newgbaxl.blastmaze.databinding.FragmentFirstBinding;
import com.newgbaxl.blastmaze.databinding.FragmentSettingsBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final boolean ARG_PARAM1 = true;
    private static final boolean ARG_PARAM2 = true;
    private static final boolean ARG_PARAM3 = true;
    private static final boolean ARG_PARAM4 = true;
    private static final boolean ARG_PARAM5 = true;

    private FragmentSettingsBinding binding;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(boolean settingInt[]) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putBoolean(String.valueOf(ARG_PARAM1), settingInt[0]);
        args.putBoolean(String.valueOf(ARG_PARAM2), settingInt[1]);
        args.putBoolean(String.valueOf(ARG_PARAM3), settingInt[2]);
        args.putBoolean(String.valueOf(ARG_PARAM4), settingInt[3]);
        args.putBoolean(String.valueOf(ARG_PARAM5), settingInt[4]);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if(!sharedPref.getBoolean("setting1",true)) {
            binding.setting1Onoff.setImageResource(R.drawable.offselected);
        }
        if(!sharedPref.getBoolean("setting2",true)) {
            binding.setting2Onoff.setImageResource(R.drawable.offselected);
        }
        if(!sharedPref.getBoolean("setting3",true)) {
            binding.setting3Onoff.setImageResource(R.drawable.offselected);
        }
        if(!sharedPref.getBoolean("setting4",true)) {
            binding.setting4Onoff.setImageResource(R.drawable.offselected);
        }
        if(!sharedPref.getBoolean("setting5",true)) {
            binding.setting5Onoff.setImageResource(R.drawable.offselected);
        }

        binding.buttonSettingsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Settings.this)
                        .navigate(R.id.action_settings_to_FirstFragment);
            }
        });

        binding.setting1Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    binding.setting1Onoff.setImageResource(R.drawable.onselected);
                    sharedPref.edit().putBoolean("setting1",true).apply();
            }
        });

        binding.setting1Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                binding.setting1Onoff.setImageResource(R.drawable.offselected);
                sharedPref.edit().putBoolean("setting1",false).apply();
            }
        });

        binding.setting2Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting2Onoff.setImageResource(R.drawable.onselected);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putBoolean("setting2",true).apply();
            }
        });

        binding.setting2Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting2Onoff.setImageResource(R.drawable.offselected);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putBoolean("setting2",false).apply();
            }
        });

        binding.setting3Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting3Onoff.setImageResource(R.drawable.onselected);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putBoolean("setting3",true).apply();
            }
        });

        binding.setting3Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting3Onoff.setImageResource(R.drawable.offselected);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putBoolean("setting3",false).apply();
            }
        });

        binding.setting4Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting4Onoff.setImageResource(R.drawable.onselected);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putBoolean("setting4",true).apply();
            }
        });

        binding.setting4Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting4Onoff.setImageResource(R.drawable.offselected);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putBoolean("setting4",false).apply();
            }
        });

        binding.setting5Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting5Onoff.setImageResource(R.drawable.onselected);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putBoolean("setting5",true).apply();
            }
        });

        binding.setting5Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting5Onoff.setImageResource(R.drawable.offselected);
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
                sharedPref.edit().putBoolean("setting5",false).apply();
            }
        });
    }
}