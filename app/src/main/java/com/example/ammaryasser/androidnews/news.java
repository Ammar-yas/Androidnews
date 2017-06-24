package com.example.ammaryasser.androidnews;

//single news story class
public class news {
    private String title;
    private String date;
    private String section;
    private String url;

    public news(String title, String date, String section, String url) {
        this.title = title;
        this.date = date;
        this.section = section;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getSection() {
        return section;
    }

    public String getUrl() {
        return url;
    }
}
