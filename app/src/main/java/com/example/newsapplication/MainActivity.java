package com.example.newsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdaptor.CategoryOnClickInterface {

    //6608f98563444f3b9b07f57f1f4b4f53
    private RecyclerView news,category;
    private ProgressBar loading;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryModel> categoryModelArrayList;
    private CategoryAdaptor categoryAdaptor;
    private NewsAdaptor newsAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        news = findViewById(R.id.News);
        category = findViewById(R.id.Category);
        loading = findViewById(R.id.Loading);
        articlesArrayList = new ArrayList<>();
        categoryModelArrayList = new ArrayList<>();
        categoryAdaptor = new CategoryAdaptor(categoryModelArrayList,this,this::OnCategoryClick);
        newsAdaptor = new NewsAdaptor(articlesArrayList,this);
        news.setLayoutManager(new LinearLayoutManager(this));
        news.setAdapter(newsAdaptor);
        category.setAdapter(categoryAdaptor);
        getCategories();
        getNews("ALL");
        newsAdaptor.notifyDataSetChanged();
    }

    private void getNews(String category){
        loading.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryUrl="https://newsapi.org/v2/top-headlines?country=in&category="+category+"&apiKey=6608f98563444f3b9b07f57f1f4b4f53";
        String Url="https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=6608f98563444f3b9b07f57f1f4b4f53";
        String BaseUrl="https://newsapi.org";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if(category.equals("ALL"))
        {
            call = retrofitAPI.getAllNews(Url);
        }
        else
        {
            call = retrofitAPI.getNewsByCategory(categoryUrl);
        }

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loading.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for (int i = 0; i < articles.size(); i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(), articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                newsAdaptor.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Fail to get News...Try again!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCategories(){
        categoryModelArrayList.add(new CategoryModel("ALL","https://images.unsplash.com/photo-1457369804613-52c61a468e7d?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MTN8fGdlbmVyYWx8ZW58MHx8MHx8&auto=format&fit=crop&w=800&q=60"));
        categoryModelArrayList.add(new CategoryModel("Technology","https://images.unsplash.com/photo-1523961131990-5ea7c61b2107?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1674&q=80"));
        categoryModelArrayList.add(new CategoryModel("Science","https://images.unsplash.com/photo-1576086213369-97a306d36557?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8c2NpZW5jZXxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60"));
        categoryModelArrayList.add(new CategoryModel("Sports","https://media.istockphoto.com/photos/various-sport-equipments-on-grass-picture-id949190736?b=1&k=20&m=949190736&s=170667a&w=0&h=f3ofVqhbmg2XSVOa3dqmvGtHc4VLA_rtbboRGaC8eNo="));
        categoryModelArrayList.add(new CategoryModel("Entertainment","https://media.istockphoto.com/photos/popcorn-and-clapperboard-picture-id1191001701?b=1&k=20&m=1191001701&s=170667a&w=0&h=uVqDpnXNtnfbhB-F4sWac_t3oL_YSrDuHeCKdaJGS3U="));
        categoryModelArrayList.add(new CategoryModel("Health","https://images.unsplash.com/photo-1511688878353-3a2f5be94cd7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=800&q=60"));
        categoryModelArrayList.add(new CategoryModel("General","https://images.unsplash.com/photo-1494059980473-813e73ee784b?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8Z2VuZXJhbHxlbnwwfHwwfHw%3D&auto=format&fit=crop&w=800&q=60"));
        categoryModelArrayList.add(new CategoryModel("Business","https://images.unsplash.com/photo-1508385082359-f38ae991e8f2?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=774&q=80"));

        categoryAdaptor.notifyDataSetChanged();
    }

    @Override
    public void OnCategoryClick(int position) {
        String category=categoryModelArrayList.get(position).getCategory();
        getNews(category);
    }
}