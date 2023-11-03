package com.example.myapplication.adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.CartItem;
import com.example.myapplication.models.Product;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    private ArrayList<Cart> cartItems;
    public CartAdapter(Context context, ArrayList<Cart> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v  = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Cart p = cartItems.get(position);
        Glide.with(context)
                .load(cartItems.get(position).getImage_product()).placeholder(R.drawable.loading).into(holder.img_pro);
        holder.tv_name.setText(p.getProduct_name());
        holder.tv_price.setText(Integer.toString(p.getPrice()));
        holder.edt_quantity.setText(Integer.toString(p.getQuantity()));
    }

    @Override
    public int getItemCount() {
        if (cartItems != null) return cartItems.size();
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView img_pro;
        TextView tv_name;
        TextView tv_price;
        EditText edt_quantity;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pro = itemView.findViewById(R.id.pro_img);
            tv_name = itemView.findViewById(R.id.pro_name);
            tv_price = itemView.findViewById(R.id.pro_price);
            edt_quantity = itemView.findViewById(R.id.edt_pro_quantity);
        }
    }
}
