package com.darrellgriffin.gitdiffapp.dagger;

import com.darrellgriffin.gitdiffapp.activity.MainActivity;

public interface ViewModelComponent {
    void inject(MainActivity activity);
}
