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
import com.example.myapplication.models.Product;

import java.util.List;

public class ShowAllAdapter extends RecyclerView.Adapter<ShowAllAdapter.ViewHolder> {
    private Context context;
    private List<Product> allModelList;

    public ShowAllAdapter(Context context, List<Product> allModelList) {
        this.context = context;
        this.allModelList = allModelList;
    }

    @NonNull
    @Override
    public ShowAllAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ShowAllAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.showall_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ShowAllAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(allModelList.get(position).getImg()).into(holder.img);
        holder.name.setText(allModelList.get(position).getName());
        holder.price.setText(String.valueOf(allModelList.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickPos = holder.getAdapterPosition();
                Intent intent = new Intent(context , ProductDetailActivity.class);
                intent.putExtra("detailInShowall",allModelList.get(clickPos));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return allModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name , price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_image);
            name = itemView.findViewById(R.id.item_nam);
            price = itemView.findViewById(R.id.item_cost);
        }
    }
}
