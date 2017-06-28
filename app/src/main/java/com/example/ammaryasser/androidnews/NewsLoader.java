package com.example.ammaryasser.androidnews;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader {

    private String url;

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }

    public NewsLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    public List<news> loadInBackground() {
        return QueryUtils.fetchNewsData(url);
    }
}
