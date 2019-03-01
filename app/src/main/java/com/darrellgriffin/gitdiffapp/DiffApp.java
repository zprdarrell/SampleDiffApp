package com.darrellgriffin.gitdiffapp;

import android.app.Application;

import com.darrellgriffin.gitdiffapp.dagger.DaggerViewModelComponent;
import com.darrellgriffin.gitdiffapp.dagger.ViewModelComponent;
import com.darrellgriffin.gitdiffapp.dagger.ViewModelModule;

import timber.log.Timber;

public class DiffApp extends Application {
    private ViewModelComponent viewModelComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        viewModelComponent = createProgramComponent();
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }else{
            //TODO: Add crash reporting
        }
    }

    public ViewModelComponent getMyComponent() {
        return viewModelComponent;
    }

    private ViewModelComponent createProgramComponent() {
        return DaggerViewModelComponent
                .builder()
                .viewModelModule(new ViewModelModule())
                .build();
    }
}
