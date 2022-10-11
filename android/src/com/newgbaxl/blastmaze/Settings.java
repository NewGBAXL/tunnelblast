package com.newgbaxl.blastmaze;

import androidx.fragment.app.Fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newgbaxl.blastmaze.Objects.ArrowSwitch;
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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public boolean setting1;
    public boolean setting2;
    public boolean setting3;
    public boolean setting4;
    public boolean setting5;

    private FragmentSettingsBinding binding;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
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
        //causes app to crash
        setting1 = true;
        setting2 = true;
        setting3 = true;
        setting4 = true;
        setting5 = true;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                    binding.setting1Onoff.setImageResource(R.drawable.onselected);
                    setting1 = true;
            }
        });

        binding.setting1Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting1Onoff.setImageResource(R.drawable.offselected);
                setting1 = true;
            }
        });

        binding.setting2Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting2Onoff.setImageResource(R.drawable.onselected);
                setting2 = true;
            }
        });

        binding.setting2Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting2Onoff.setImageResource(R.drawable.offselected);
                setting2 = true;
            }
        });

        binding.setting3Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting3Onoff.setImageResource(R.drawable.onselected);
                setting3 = true;
            }
        });

        binding.setting3Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting3Onoff.setImageResource(R.drawable.offselected);
                setting3 = true;
            }
        });

        binding.setting4Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting4Onoff.setImageResource(R.drawable.onselected);
                setting4 = true;
            }
        });

        binding.setting4Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting4Onoff.setImageResource(R.drawable.offselected);
                setting4 = true;
            }
        });

        binding.setting5Left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting5Onoff.setImageResource(R.drawable.onselected);
                setting5 = true;
            }
        });

        binding.setting5Right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.setting5Onoff.setImageResource(R.drawable.offselected);
                setting5 = true;
            }
        });
    }
}