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
import com.example.myapplication.models.ProductModel;

import java.util.List;

public class ListProductAdapter extends RecyclerView.Adapter<ListProductAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> listproduct;

    public ListProductAdapter(Context context, List<ProductModel> listproduct) {
        this.context = context;
        this.listproduct = listproduct;
    }

    @NonNull
    @Override
    public ListProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListProductAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(listproduct.get(position).getImg()).into(holder.view);
        holder.name.setText(listproduct.get(position).getName());
        holder.price.setText(String.valueOf(listproduct.get(position).getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int clickPos = holder.getAdapterPosition();
                Intent intent = new Intent(context , ProductDetailActivity.class);
                intent.putExtra("detailInList",listproduct.get(clickPos));
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listproduct.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView view;
        TextView name  , price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView.findViewById(R.id.pro_img);
            name = itemView.findViewById(R.id.pro_name);
            price = itemView.findViewById(R.id.pro_price);
        }
    }
}
