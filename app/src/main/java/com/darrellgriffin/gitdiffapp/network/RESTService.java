package com.darrellgriffin.gitdiffapp.network;

import com.darrellgriffin.gitdiffapp.model.RequestList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface RESTService {
    @GET("/{owner}/{project}/pulls/?state=open")
    Call<RequestList> getOpenRequests(@Path("owner")String owner,
                                      @Path("project")String projectName);

    @GET()
    Call<String> getDiffFile(@Url String url);
}
