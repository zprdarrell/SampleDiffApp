package com.darrellgriffin.gitdiffapp.dagger;

import com.darrellgriffin.gitdiffapp.activity.MainActivity;

import dagger.Component;

@Component(modules = ViewModelModule.class)
public interface ViewModelComponent {
    void inject(MainActivity activity);
}
