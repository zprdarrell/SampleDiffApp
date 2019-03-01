package com.darrellgriffin.gitdiffapp.viewmodel;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class PullViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>,Provider<ViewModel>> mViewModelProvider;

    @Inject
    public PullViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap) {
        mViewModelProvider= providerMap;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) mViewModelProvider.get(modelClass).get();
    }
}
