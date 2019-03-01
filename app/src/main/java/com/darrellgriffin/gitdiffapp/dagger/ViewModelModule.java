package com.darrellgriffin.gitdiffapp.dagger;

import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModel;
import com.darrellgriffin.gitdiffapp.viewmodel.PullViewModelFactory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import javax.inject.Provider;

import androidx.lifecycle.ViewModel;
import dagger.MapKey;
import dagger.Provides;
import dagger.multibindings.IntoMap;

public class ViewModelModule {

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    @interface ViewModelKey {
        Class<? extends ViewModel> value();
    }
    @Provides
    PullViewModelFactory pullViewModelFactory(Map<Class<? extends ViewModel>, Provider<ViewModel>> providerMap){
        return new PullViewModelFactory(providerMap);
    }
    @Provides
    @IntoMap
    @ViewModelKey(PullViewModel.class)

    ViewModel viewModel1(PullViewModel pullViewModel) {
        return pullViewModel;
    }

}
