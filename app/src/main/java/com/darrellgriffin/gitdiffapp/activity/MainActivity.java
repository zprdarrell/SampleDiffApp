package com.darrellgriffin.gitdiffapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import timber.log.Timber;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;

import com.darrellgriffin.gitdiffapp.DiffApp;
import com.darrellgriffin.gitdiffapp.R;
import com.darrellgriffin.gitdiffapp.adapter.RepoBindingAdapter;
import com.darrellgriffin.gitdiffapp.adapter.RepoRecyclerAdapter;
import com.darrellgriffin.gitdiffapp.databinding.ActivityMainBinding;
import com.darrellgriffin.gitdiffapp.fragment.DiffFragment;
import com.darrellgriffin.gitdiffapp.fragment.PullRequestFragment;
import com.darrellgriffin.gitdiffapp.model.PullRequest;
import com.darrellgriffin.gitdiffapp.util.Constants;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModel;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModelFactory;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements RepoRecyclerAdapter.PullRequestSelectedListener{
    public ActivityMainBinding binding;
    private PullViewModel viewModel;

    @Inject
    PullViewModelFactory factory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((DiffApp)getApplication())
                .getMyComponent()
                .inject(MainActivity.this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViews();
    }

    private void initViews(){
        viewModel = ViewModelProviders.of(this, factory).get(PullViewModel.class);
        viewModel.loading.observe(this, this::toggleLoadingView);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(binding.fragmentContainer.getId(), new PullRequestFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void toggleLoadingView(boolean show){
        binding.loadingView.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onPullSelected(PullRequest request) {
        Timber.d("onPullSelcted %s", request);
        String diffHeader = "Diff for Pull Request " + String.valueOf(request.getNumber());
        binding.activityHeaderTV.setText(diffHeader);
        viewModel.fetchDiffFile(request.getDiffUrl());
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(binding.fragmentContainer.getId(), new DiffFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
