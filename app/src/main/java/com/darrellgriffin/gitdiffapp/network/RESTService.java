package com.darrellgriffin.gitdiffapp.network;

import com.darrellgriffin.gitdiffapp.model.RepoRequests;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RESTService {
    @GET("/{owner}/{project}/pulls/?state=open")
    Call<RepoRequests> getOpenRequests(@Path("owner")String owner,
                                       @Path("project")String projectName);

    @GET("/{owner}/{project}/pull/{id}.diff")
    Call<String> getDiffFile(@Path("owner")String owner,
                                @Path("project")String projectName,
                                @Path("id")String id);
}
