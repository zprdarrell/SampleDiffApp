package com.darrellgriffin.gitdiffapp.network;

import com.darrellgriffin.gitdiffapp.model.RepoRequests;
import com.squareup.moshi.Moshi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {
    private static final String BASE_REPO_URL = "https://api.github.com/repos";
    private static final String BASE_DIFF_URL = "https://github.com/";
    private static RetrofitClient instance;
    private static DataCallback listener;

    public interface DataCallback{
        void onRepoResponse();
        void onPullRequestResponse();
    }


    public RetrofitClient getInstance(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }
    public void registerListener(DataCallback callback){
        listener = callback;
    }
    public void unregisterListener(){
        listener = null;
    }

    private Retrofit getRetrofit(String baseUrl){
        Moshi moshi = new Moshi.Builder().build();
        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }
    public void getPullRequests(String owner, String projectName){
        getRetrofit(BASE_REPO_URL).create(RESTService.class).getOpenRequests(owner, projectName)
                .enqueue(new Callback<RepoRequests>() {
                    @Override
                    public void onResponse(Call<RepoRequests> call, Response<RepoRequests> response) {

                    }

                    @Override
                    public void onFailure(Call<RepoRequests> call, Throwable t) {

                    }
                });
    }
    public void getDiffFile(String owner, String projectname, String id){
        getRetrofit(BASE_DIFF_URL).create(RESTService.class).getDiffFile(owner, projectname, id)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }
}
