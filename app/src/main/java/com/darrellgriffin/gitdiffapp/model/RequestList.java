package com.darrellgriffin.gitdiffapp.model;

import java.util.ArrayList;
import java.util.List;

public class RequestList {

    private List<PullRequest> pullRequestList;

    public List<PullRequest> getPullRequestList() {
        return pullRequestList != null ? pullRequestList : new ArrayList<>();
    }

    public void setPullRequestList(List<PullRequest> pullRequestList) {
        this.pullRequestList = pullRequestList;
    }
}
