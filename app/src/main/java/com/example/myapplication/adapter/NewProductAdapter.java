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

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {

    private Context context;
    private List<Product> productsList;

    public NewProductAdapter(Context context, List<Product> productsList) {
        this.context = context;
        this.productsList = productsList;
    }

    @NonNull
    @Override
    public NewProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_product,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewProductAdapter.ViewHolder holder, int position) {

            Glide.with(context).load(productsList.get(position).getImg()).placeholder(R.drawable.loading).into(holder.newImg);
            String text = productsList.get(position).getName();
             if (text.length() > 20) {

            holder.newName.setText(text.substring(0, 20)+"...");
            } else {

            holder.newName.setText(text);
            }


             //price
            double price = productsList.get(position).getPrice();


            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
            String formattedPrice = format.format(price);


             holder.newPrice.setText(formattedPrice);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int clickedPosition = holder.getAdapterPosition();
                    Intent intent = new Intent(context, ProductDetailActivity.class);
                    intent.putExtra("detailpro", productsList.get(clickedPosition));
                    context.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView newImg;
        TextView newName,newPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newImg = itemView.findViewById(R.id.new_img);
            newName = itemView.findViewById(R.id.new_name);
            newPrice = itemView.findViewById(R.id.new_price);

        }
    }
}
