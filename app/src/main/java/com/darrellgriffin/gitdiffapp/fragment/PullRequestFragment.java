package com.darrellgriffin.gitdiffapp.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darrellgriffin.gitdiffapp.R;
import com.darrellgriffin.gitdiffapp.databinding.FragmentPullRequestBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PullRequestFragment extends Fragment {

    FragmentPullRequestBinding binding;

    public PullRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_pull_request, container, false);

        return binding.getRoot();
    }

}
