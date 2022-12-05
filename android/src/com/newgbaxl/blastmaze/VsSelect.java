package com.newgbaxl.blastmaze;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.newgbaxl.blastmaze.databinding.FragmentModeSelectBinding;
import com.newgbaxl.blastmaze.databinding.FragmentVsSelectBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VsSelect#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VsSelect extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentVsSelectBinding binding;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VsSelect() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VsSelect.
     */
    // TODO: Rename and change types and number of parameters
    public static VsSelect newInstance(String param1, String param2) {
        VsSelect fragment = new VsSelect();
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        binding = FragmentVsSelectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.startGuest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
                i3.putExtra("VsMode", 2);
                startActivity(i3);
            }
        });

            binding.startHost.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i3 = new Intent(getActivity(), AndroidLauncher.class);
                i3.putExtra("VsMode", 1);
                startActivity(i3);
            }
        });
    }
}