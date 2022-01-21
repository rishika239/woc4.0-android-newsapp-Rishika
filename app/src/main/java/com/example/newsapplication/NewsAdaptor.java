package com.example.newsapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsAdaptor extends RecyclerView.Adapter<NewsAdaptor.ViewHolder> {
    private ArrayList<Articles> articlesArrayList;
    private Context context;

    public NewsAdaptor(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_items,parent,false);
        return new NewsAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdaptor.ViewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.subtitle.setText(articles.getDescription());
        holder.title.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage()).into(holder.news);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,NewsDetailActivity.class);
                i.putExtra("title",articles.getTitle());
                i.putExtra("desc",articles.getDescription());
                i.putExtra("content",articles.getContent());
                i.putExtra("image",articles.getUrlToImage());
                i.putExtra("url",articles.getUrl());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title,subtitle;
        private ImageView news;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.newsheadline);
            subtitle=itemView.findViewById(R.id.subheading);
            news=itemView.findViewById(R.id.newsimage);
        }
    }
}
