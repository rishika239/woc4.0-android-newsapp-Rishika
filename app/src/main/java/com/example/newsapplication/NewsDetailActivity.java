package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    String title,desc,imageURL,content,url;
    private TextView titleTV,subDescTV,contentTV;
    private ImageView newsIV;
    private Button readnewsBtn;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title=getIntent().getStringExtra("title");
        desc=getIntent().getStringExtra("desc");
        content=getIntent().getStringExtra("content");
        imageURL=getIntent().getStringExtra("image");
        url=getIntent().getStringExtra("url");

        titleTV=findViewById(R.id.idTVTitle);
        subDescTV=findViewById(R.id.idTVSubDesc);
        contentTV=findViewById(R.id.idTVContent);
        newsIV=findViewById(R.id.idIVNews);
        readnewsBtn=findViewById(R.id.idBtnReadNews);

        titleTV.setText(title);
        subDescTV.setText(desc);
        contentTV.setText(content);
        Picasso.get().load(imageURL).into(newsIV);
        readnewsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.viewWeb)
        {
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }
        else if(id==R.id.share)
        try{
            Intent i=new Intent(Intent.ACTION_SEND);
            i.setType("text/plan");
            i.putExtra(Intent.EXTRA_SUBJECT,content);
            String body=title + "\n" + url +"\n" + "Share From the news app" + "\n";
            i.putExtra(Intent.EXTRA_TEXT, body);
            startActivity(Intent.createChooser(i,"Share with:"));
            return true;
        }catch(Exception e)
        {
            Toast.makeText(this, "Hmm..Sorry! You can't share this!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}