package com.darrellgriffin.gitdiffapp.viewmodel;

import com.darrellgriffin.gitdiffapp.model.RequestList;
import com.darrellgriffin.gitdiffapp.network.RetrofitClient;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PullViewModel extends ViewModel implements RetrofitClient.DataCallback{
    private RetrofitClient client;

    public LiveData<RequestList> getPullList() {
        return pullList;
    }
    private final MutableLiveData<RequestList> pullList = new MutableLiveData<>();

    @Inject
    public PullViewModel(){
        client = RetrofitClient.getInstance();
        RetrofitClient.registerListener(this);
    }

    @Override
    public void onDiffResponse(String diff) {

    }

    @Override
    public void onRepoListResponse(RequestList requestList) {
        pullList.postValue(requestList);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        RetrofitClient.unregisterListener(this);
    }

    public void fetchRepoData(String owner, String projectName){
        client.getPullRequests(owner, projectName);
    }
    public void fetchDiffFile(String url){
        client.getDiffFile(url);
    }
}
