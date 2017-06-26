package com.example.ammaryasser.androidnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Linking the recycler view in the layout with the code
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //Setting up layout manager for the recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //Initializing recycler view adapter
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<news>());
        recyclerView.setAdapter(recyclerViewAdapter);



    }
}
