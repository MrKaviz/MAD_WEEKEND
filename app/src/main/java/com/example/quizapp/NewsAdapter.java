package com.example.quizapp;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder>{

    private List<NewsModel> newsModels;
    private Context mCtx;

    public NewsAdapter( Context mCtx, List<NewsModel> newsModels) {
        this.mCtx = mCtx;
        this.newsModels = newsModels;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(
                LayoutInflater.from(mCtx).inflate(R.layout.news, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsModel news = newsModels.get(position);

        holder.textViewName.setText(news.getName());
        holder.textViewBrand.setText(news.getBrand());
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    class NewsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView textViewName, textViewBrand;

        public NewsViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textview_name);
            textViewBrand = itemView.findViewById(R.id.textview_brand);

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            NewsModel news = newsModels.get(getAdapterPosition());
            Intent intent = new Intent(mCtx, UpdateNews.class);
            intent.putExtra("News", news);
            mCtx.startActivity(intent);
        }

    }
}
