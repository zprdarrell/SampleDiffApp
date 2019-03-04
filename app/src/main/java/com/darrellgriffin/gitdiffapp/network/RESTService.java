package com.darrellgriffin.gitdiffapp.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RESTService {
    @GET("repos/{owner}/{project}/pulls?state=open")
    Call<ResponseBody> getOpenRequests(@Path("owner")String owner,
                                       @Path("project")String projectName);

    @GET()
    Call<String> getDiffFile(@Url String url);
}
