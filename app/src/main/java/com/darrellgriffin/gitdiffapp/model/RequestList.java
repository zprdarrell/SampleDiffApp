package com.darrellgriffin.gitdiffapp.model;

import java.util.List;

public class RequestList {
    private String repoOwner;
    private String projectName;
    private List<PullRequest> pullRequestList;

    public String getRepoOwner() {
        return repoOwner;
    }

    public String getProjectName() {
        return projectName;
    }

    public List<PullRequest> getPullRequestList() {
        return pullRequestList;
    }

    public void setRepoOwner(String repoOwner) {
        this.repoOwner = repoOwner;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setPullRequestList(List<PullRequest> pullRequestList) {
        this.pullRequestList = pullRequestList;
    }
}
