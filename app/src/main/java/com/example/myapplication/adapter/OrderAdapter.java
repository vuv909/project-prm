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
import com.example.myapplication.models.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private ArrayList<Order> orderItems;

    public OrderAdapter(Context context, ArrayList<Order> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order p = orderItems.get(position);
        String temp = "";
        Glide.with(context)
                .load(p.getProduct_image())
                .placeholder(R.drawable.loading)
                .into(holder.imv_pro);
        holder.tv_pro_name.setText(p.getProduct_name());

        temp = "x"+ p.getProduct_quantity();
        holder.tv_quantity.setText(temp);

        temp = "Thành Tiền:" + p.getTotal_price();
        holder.tv_order_price.setText(temp);
        holder.tv_order_status.setText(p.getStatus());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String date = "Ngày đặt hàng: " + dateFormat.format(p.getOrder_date());
        holder.tv_date.setText(date);
    }

    @Override
    public int getItemCount() {
        if (orderItems != null) return orderItems.size();
        return 0;
    }


    public class OrderViewHolder extends RecyclerView.ViewHolder {

        ImageView imv_pro;
        TextView tv_pro_name;
        TextView tv_quantity;
        TextView tv_order_price;
        TextView tv_order_status;
        TextView tv_date;

        void bindingView() {
            imv_pro = itemView.findViewById(R.id.imv_pro);
            tv_pro_name = itemView.findViewById(R.id.tv_pro_name);
            tv_quantity = itemView.findViewById(R.id.tv_pro_quantity);
            tv_order_price = itemView.findViewById(R.id.tv_order_price);
            tv_order_status = itemView.findViewById(R.id.tv_order_status);
            tv_date = itemView.findViewById(R.id.tv_date);
        }


        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
        }
    }
}