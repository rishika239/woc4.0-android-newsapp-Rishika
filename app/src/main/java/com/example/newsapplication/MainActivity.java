package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategoryClickInterface {

    //6608f98563444f3b9b07f57f1f4b4f53
    private RecyclerView newsRV,categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);


        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList,this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList,this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("ALL");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL="https://newsapi.org/v2/top-headlines?country=in&category="+ category +"&apiKey=6608f98563444f3b9b07f57f1f4b4f53";
        String url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=6608f98563444f3b9b07f57f1f4b4f53";
        String BASE_URL="https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;
        if(category.equals("ALL"))
        {
            call = retrofitAPI.getAllNews(url);
        }
        else
        {
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Fail to get News...Try again!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("ALL","https://images.unsplash.com/photo-1457369804613-52c61a468e7d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fGdlbmVyYWx8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology","https://images.unsplash.com/photo-1523961131990-5ea7c61b2107?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1674&q=80"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science","https://images.unsplash.com/photo-1576086213369-97a306d36557?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8c2NpZW5jZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports","https://media.istockphoto.com/photos/various-sport-equipments-on-grass-picture-id949190736?b=1&k=20&m=949190736&s=170667a&w=0&h=f3ofVqhbmg2XSVOa3dqmvGtHc4VLA_rtbboRGaC8eNo="));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment","https://media.istockphoto.com/photos/popcorn-and-clapperboard-picture-id1191001701?b=1&k=20&m=1191001701&s=170667a&w=0&h=uVqDpnXNtnfbhB-F4sWac_t3oL_YSrDuHeCKdaJGS3U="));
        categoryRVModalArrayList.add(new CategoryRVModal("Health","https://images.unsplash.com/photo-1511688878353-3a2f5be94cd7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("General","https://images.unsplash.com/photo-1494059980473-813e73ee784b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business","https://images.unsplash.com/photo-1508385082359-f38ae991e8f2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"));

        categoryRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(int position) {
        String category= categoryRVModalArrayList.get(position).getCategory();
        getNews(category);

    }


}
