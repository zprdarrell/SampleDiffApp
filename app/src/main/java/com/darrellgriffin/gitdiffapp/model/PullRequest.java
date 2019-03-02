package com.darrellgriffin.gitdiffapp.model;

import com.squareup.moshi.Json;

public class PullRequest {
    @Json(name = "url") private String url;
    @Json(name = "id") private int id;
    @Json(name = "diff_url") private String diffUrl;
    @Json(name = "number") private int number;
    @Json(name = "title") private String title;
    @Json(name = "body") private String body;


    public String getUrl() {
        return url;
    }

    public int getId() {
        return id;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public int getNumber() {
        return number;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }
}
