package com.darrellgriffin.gitdiffapp.network;

import com.darrellgriffin.gitdiffapp.model.RequestList;
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
        void onDiffResponse(String diffFileString);
        void onRepoListResponse(RequestList pullRequestList);
    }


    public static RetrofitClient getInstance(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }
    public static void registerListener(DataCallback callback){
        listener = callback;
    }
    public static void unregisterListener(DataCallback callback){
        if(listener == callback)listener = null;
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
                .enqueue(new Callback<RequestList>() {
                    @Override
                    public void onResponse(Call<RequestList> call, Response<RequestList> response) {
                        listener.onRepoListResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<RequestList> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
    public void getDiffFile(String url){
        getRetrofit(BASE_DIFF_URL).create(RESTService.class).getDiffFile(url)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        listener.onDiffResponse(response.body());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
