package com.darrellgriffin.gitdiffapp.viewmodel;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class EventLiveData<T> extends MutableLiveData<T> {


    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        if(hasObservers()){
            try {
                throw new Throwable("Only one observer may be subscribed");
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        super.observe(owner, data -> {
            if(data == null)return;
            observer.onChanged(data);
            setValue(null);
        });
    }

    @MainThread
    public void sendAction(T data){
        setValue(data);
    }
}