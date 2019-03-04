package com.darrellgriffin.gitdiffapp.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darrellgriffin.gitdiffapp.R;
import com.darrellgriffin.gitdiffapp.activity.MainActivity;
import com.darrellgriffin.gitdiffapp.adapter.RepoBindingAdapter;
import com.darrellgriffin.gitdiffapp.adapter.RepoRecyclerAdapter;
import com.darrellgriffin.gitdiffapp.databinding.FragmentPullRequestBinding;
import com.darrellgriffin.gitdiffapp.model.PullRequest;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModel;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModelFactory;

import javax.inject.Inject;

/**
 * A simple {@link Fragment} subclass.
 */
public class PullRequestFragment extends Fragment{

    private PullViewModel viewModel;
    FragmentPullRequestBinding binding;

    @Inject
    PullViewModelFactory factory;

    public PullRequestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Timber.d("PullRequestFragment created");
        binding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_pull_request, container, false);
        viewModel = ViewModelProviders.of(getActivity(), factory).get(PullViewModel.class);
        RepoBindingAdapter.setAdapter(binding.prRecyclerView, new RepoRecyclerAdapter(viewModel, getActivity(), (MainActivity)getActivity()));
        return binding.getRoot();
    }


}
