package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    String title,desc,imageUrl,content,url;
    private TextView titlenews,subdesc,con;
    private Button readfull;
    private ImageView imagenews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("desc");
        imageUrl=getIntent().getStringExtra("image");
        content=getIntent().getStringExtra("content");
        url=getIntent().getStringExtra("url");
        titlenews=findViewById(R.id.titlenewsdetail);
        subdesc=findViewById(R.id.subdescnewsdetail);
        con=findViewById(R.id.contentnewsdetail);
        readfull=findViewById(R.id.readfullnews);
        imagenews=findViewById(R.id.imagenewsdetail);
        titlenews.setText(title);
        subdesc.setText(desc);
        con.setText(content);
        Picasso.get().load(imageUrl).into(imagenews);
        readfull.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}