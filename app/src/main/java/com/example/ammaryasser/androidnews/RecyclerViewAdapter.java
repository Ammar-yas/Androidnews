package com.example.ammaryasser.androidnews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<news> newsList;

    RecyclerViewAdapter(List<news> newsList) {
        this.newsList = newsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View newsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_layout,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(newsView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(newsList.get(position).getTitle());
        holder.section.setText(newsList.get(position).getSection());
        holder.date.setText(newsList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView section;
        TextView date;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            section = (TextView) itemView.findViewById(R.id.section);
            date = (TextView) itemView.findViewById(R.id.date);
        }
    }

}
