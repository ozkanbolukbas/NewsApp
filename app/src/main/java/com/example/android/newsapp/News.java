package com.example.android.newsapp;

public class News {
    private String sectionName;
    private String title;
    private String time;
    private String url;
    private String authorName;

    public News(String sectionName, String title, String time, String url) {
        this.sectionName = sectionName;
        this.title = title;
        this.time = time;
        this.url = url;
    }

    public News(String sectionName, String title, String time, String url, String authorName) {
        this.sectionName = sectionName;
        this.title = title;
        this.time = time;
        this.url = url;
        this.authorName = authorName;

    }

    public String getSectionName() {
        return sectionName;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    public String getAuthorName() {
        return authorName;
    }


}
