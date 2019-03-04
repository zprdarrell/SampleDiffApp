package com.darrellgriffin.gitdiffapp.viewmodel;

import com.darrellgriffin.gitdiffapp.model.PullRequest;
import com.darrellgriffin.gitdiffapp.network.RetrofitClient;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import timber.log.Timber;

public class PullViewModel extends ViewModel implements RetrofitClient.DataCallback{
    private RetrofitClient client;

    public LiveData<List<PullRequest>> getPullList() {
        return pullList;
    }
    private final MutableLiveData<List<PullRequest>> pullList = new MutableLiveData<>();

    @Inject
    public PullViewModel(){
        client = RetrofitClient.getInstance();
        RetrofitClient.registerListener(this);
    }

    @Override
    public void onDiffResponse(String diff) {
        Timber.d("Posting diff response.");

    }

    @Override
    public void onRepoListResponse(List<PullRequest> requestList) {
        Timber.d("Posting values from request %s");
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
