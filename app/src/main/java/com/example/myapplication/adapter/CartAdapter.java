package com.example.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.activity.ViewCartActivity;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.Order;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
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

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        Order o = new Order(0,p.getProduct_id(),
                        1, p.getPrice() * p.getQuantity(), p.getQuantity(), p.getProduct_name(),p.getImage_product(),
                        "Chờ Xác nhận", "Ha Noi", currentDate);

        holder.chk_confirm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ViewCartActivity.total_pay += p.getPrice() * p.getQuantity();
                    ViewCartActivity.setTotal_pay(ViewCartActivity.total_pay);
                    ViewCartActivity.listOrder.add(o);
                    Log.d("luctd", "check_" + ViewCartActivity.listOrder.size());
                } else {
                    ViewCartActivity.total_pay -= p.getPrice() * p.getQuantity();
                    ViewCartActivity.setTotal_pay(ViewCartActivity.total_pay);
                    ViewCartActivity.listOrder.remove(o);
                    Log.d("luctd", "uncheck_" + ViewCartActivity.listOrder.size());
                }
            }
        });
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
        CheckBox chk_confirm;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_pro = itemView.findViewById(R.id.pro_img);
            tv_name = itemView.findViewById(R.id.pro_name);
            tv_price = itemView.findViewById(R.id.pro_price);
            edt_quantity = itemView.findViewById(R.id.edt_pro_quantity);
            chk_confirm = itemView.findViewById(R.id.chk_confirm);
        }
    }
}
