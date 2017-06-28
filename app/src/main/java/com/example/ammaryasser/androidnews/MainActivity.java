package com.example.ammaryasser.androidnews;

import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements android.app.LoaderManager.LoaderCallbacks<List<news>> {

    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?q=android&tag=technology/google&api-key=test";
    RecyclerView recyclerView;
    TextView statusText;
    ProgressBar progressBar;
    RecyclerViewAdapter recyclerViewAdapter;
    List<news> news = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        statusText = (TextView) findViewById(R.id.statusText);

        //Linking the recycler view in the layout with the code
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //Setting up layout manager for the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //Initializing recycler view adapter
        recyclerViewAdapter = new RecyclerViewAdapter(news);
        recyclerView.setAdapter(recyclerViewAdapter);
        android.app.LoaderManager loaderManager = getLoaderManager();
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (isConnected) {
            loaderManager.initLoader(1, null, MainActivity.this);
        } else {
            recyclerView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            statusText.setText(R.string.no_internet);
        }
    }

    @Override
    public Loader<List<news>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<news>> loader, List<news> data) {
        progressBar.setVisibility(View.GONE);
        news.clear();
        if (data != null && !data.isEmpty()) {
            news.addAll(data);
            recyclerViewAdapter.notifyDataSetChanged();
        } else {
            statusText.setText(R.string.no_news);
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<news>> loader) {
        news.clear();
    }
}
