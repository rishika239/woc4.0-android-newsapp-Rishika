package com.example.newsapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdaptor extends RecyclerView.Adapter<CategoryAdaptor.ViewHolder> {
    private ArrayList<CategoryModel> categoryModel;
    private Context context;

    public CategoryAdaptor(ArrayList<CategoryModel> categoryModel, Context context, CategoryOnClickInterface categoryOnClickInterface) {
        this.categoryModel = categoryModel;
        this.context = context;
        this.categoryOnClickInterface = categoryOnClickInterface;
    }

    private CategoryOnClickInterface categoryOnClickInterface;



    @NonNull
    @Override
    public CategoryAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.headline_list_items,parent,false);
        return new CategoryAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdaptor.ViewHolder holder, int position) {
        CategoryModel categoryModels=categoryModel.get(position);
        holder.categoryText.setText(categoryModels.getCategory());
        Picasso.get().load(categoryModels.getCategoryImageUrl()).into(holder.categoryImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryOnClickInterface.OnCategoryClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryModel.size();
    }

    public interface CategoryOnClickInterface{
        void OnCategoryClick(int position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView categoryText;
        private ImageView categoryImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryText=itemView.findViewById(R.id.categorytext);
            categoryImage=itemView.findViewById(R.id.categoryimage);
        }
    }
}
