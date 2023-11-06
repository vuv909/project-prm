package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Cart;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Context context;
    Gson gson;
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
        String productName = p.getProduct_name();
        if (productName.length() > 20) {
            productName = productName.substring(0, 30) + "...";
        }
        holder.tv_name.setText(productName);

        int priceInVND = p.getPrice();
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.tv_price.setText(vndFormat.format(priceInVND));
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
        TextView edt_quantity;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pro = itemView.findViewById(R.id.pro_img);
            tv_name = itemView.findViewById(R.id.pro_name);
            tv_price = itemView.findViewById(R.id.pro_price);
            edt_quantity = itemView.findViewById(R.id.edt_pro_quantity);
        }
    }
}
