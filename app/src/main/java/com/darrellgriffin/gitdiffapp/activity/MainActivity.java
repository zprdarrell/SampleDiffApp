package com.darrellgriffin.gitdiffapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.darrellgriffin.gitdiffapp.DiffApp;
import com.darrellgriffin.gitdiffapp.R;
import com.darrellgriffin.gitdiffapp.adapter.RepoBindingAdapter;
import com.darrellgriffin.gitdiffapp.adapter.RepoRecyclerAdapter;
import com.darrellgriffin.gitdiffapp.databinding.ActivityMainBinding;
import com.darrellgriffin.gitdiffapp.fragment.PullRequestFragment;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModel;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModelFactory;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
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
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(binding.fragmentContainer.getId(), new PullRequestFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
