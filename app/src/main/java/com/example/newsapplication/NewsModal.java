package com.example.newsapplication;

import java.util.ArrayList;

public class NewsModal {
    private int totalResults;
    private String status;

    public NewsModal(int totalResults, String status, ArrayList<Articles> articles) {
        this.totalResults = totalResults;
        this.status = status;
        this.articles = articles;
    }

    private ArrayList<Articles> articles;

    public int getTotalResult() {
        return totalResults;
    }

    public void setTotalResult(int totalResult) {
        this.totalResults = totalResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Articles> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<Articles> articles) {
        this.articles = articles;
    }


}
