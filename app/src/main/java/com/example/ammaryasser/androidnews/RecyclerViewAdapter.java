package com.example.ammaryasser.androidnews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String LOCATION_SEPARATOR = "T";
    static List<news> newsList;

    RecyclerViewAdapter(List<news> newsList) {
        this.newsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View newsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout,
                parent, false);
        return new ViewHolder(newsView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(newsList.get(position).getTitle());
        holder.section.setText(newsList.get(position).getSection());
        String[] webPublicationDate = newsList.get(position).getDate().split(LOCATION_SEPARATOR);
        holder.date.setText(webPublicationDate[0]);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        TextView section;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            section = (TextView) itemView.findViewById(R.id.section);
            date = (TextView) itemView.findViewById(R.id.date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            news story = newsList.get(getAdapterPosition());
            Uri newsUri = Uri.parse(story.getUrl());
            Intent webUrlIntent = new Intent(Intent.ACTION_VIEW, newsUri);
            v.getContext().startActivity(webUrlIntent);
        }
    }
}
