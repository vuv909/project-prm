package com.example.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.ProductDetailActivity;
import com.example.myapplication.activity.ShowAllAcitvity;
import com.example.myapplication.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{

    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
            Glide.with(context).load(categoryList.get(position).getImg()).placeholder(R.drawable.advert1).into(holder.catImage);
            holder.catImage.setImageResource(R.drawable.advert1); // Set a default image
            holder.catName.setText(categoryList.get(position).getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickPosition = holder.getAdapterPosition();
                    Intent intent = new Intent(context , ShowAllAcitvity.class);
                    intent.putExtra("type",categoryList.get(clickPosition).getType());
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        ImageView catImage;
        TextView catName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catImage = itemView.findViewById(R.id.cat_img);
            catName = itemView.findViewById(R.id.cat_name);
        }
    }
}
