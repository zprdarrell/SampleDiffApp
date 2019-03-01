package com.darrellgriffin.gitdiffapp.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darrellgriffin.gitdiffapp.R;
import com.darrellgriffin.gitdiffapp.databinding.FragmentDiffBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiffFragment extends Fragment {

    FragmentDiffBinding binding;
    public DiffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_diff, container, false);
        return binding.getRoot();
    }

}
