package com.darrellgriffin.gitdiffapp.fragment;


import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;

import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.darrellgriffin.gitdiffapp.R;
import com.darrellgriffin.gitdiffapp.databinding.FragmentDiffBinding;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModel;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModelFactory;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiffFragment extends Fragment {

    FragmentDiffBinding binding;
    private PullViewModel viewModel;
    private String diffString;

    @Inject
    PullViewModelFactory factory;

    public DiffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.d("DiffFragment created");
        viewModel = ViewModelProviders.of(getActivity(), factory).get(PullViewModel.class);
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_diff, container, false);
        binding.leftDiffView.setLeftSide(true);
        binding.rightDiffView.setLeftSide(false);
        binding.rightDiffView.setMovementMethod(new ScrollingMovementMethod());
        binding.leftDiffView.setMovementMethod(new ScrollingMovementMethod());
        binding.leftDiffView.setBreakStrategy(Layout.BREAK_STRATEGY_BALANCED);
        viewModel.getDiff().observe(getActivity(), diffFile -> {

            if(diffFile != null){
                diffString = diffFile;
                binding.rightDiffView.setText(diffString, TextView.BufferType.SPANNABLE);
                binding.leftDiffView.setText(diffString, TextView.BufferType.SPANNABLE);
                binding.divider.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

}
