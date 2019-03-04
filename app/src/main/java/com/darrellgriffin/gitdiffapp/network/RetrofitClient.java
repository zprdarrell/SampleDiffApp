package com.darrellgriffin.gitdiffapp.network;

import com.darrellgriffin.gitdiffapp.model.PullRequest;
import com.squareup.moshi.Moshi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import timber.log.Timber;

public class RetrofitClient {
    private static final String BASE_REPO_URL = "https://api.github.com/";
    private static final String BASE_DIFF_URL = "https://github.com/";
    private static RetrofitClient instance;
    private static DataCallback listener;

    public interface DataCallback{
        void onDiffResponse(String diffFileString);
        void onRepoListResponse(List<PullRequest> pullRequestList);
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
        Moshi moshi = new Moshi.Builder()
                .build();

        return new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create(moshi).asLenient())
                .validateEagerly(true)
                .build();
    }
    public void getPullRequests(String owner, String projectName){
        getRetrofit(BASE_REPO_URL).create(RESTService.class).getOpenRequests(owner, projectName)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Timber.d("Received response %s", response.body());
                        List<PullRequest> requestList = new ArrayList<>();
                        try {
                            JSONArray responseArray = new JSONArray(response.body().string());
                            for(int i = 0; i < responseArray.length(); i++){
                                JSONObject responseObject = responseArray.getJSONObject(i);
                                String url = responseObject.getString("url");
                                int id = responseObject.getInt("id");
                                String difUrl = responseObject.getString("diff_url");
                                int number = responseObject.getInt("number");
                                String title = responseObject.getString("title");
                                String body = responseObject.getString("body");
                                PullRequest pullRequest = new PullRequest(url, id, difUrl, number, title, body);
                                requestList.add(pullRequest);
                            }

                        } catch (JSONException| IOException e) {
                            e.printStackTrace();
                        }
                        listener.onRepoListResponse(requestList);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
    public void getDiffFile(String url){
        getRetrofit(BASE_DIFF_URL).create(RESTService.class).getDiffFile(url)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Timber.d("onResponse %s", response);
                        try {
                            listener.onDiffResponse(response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
}
