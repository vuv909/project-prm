package com.example.myapplication.adapter;

import android.content.Context;
import android.icu.text.NumberFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.ProOrder;
import com.example.myapplication.models.Product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private ArrayList<ProOrder> orderItems;

    public OrderAdapter(Context context, ArrayList<ProOrder> orderItems) {
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
        ProOrder proOrder = orderItems.get(position);
        holder.order_date.setText(proOrder.getOrderDate());
        holder.order_quantity.setText(String.valueOf(proOrder.getTotalProduct()));
        double totalPrice = proOrder.getTotalPrice();


        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String formattedPrice = vndFormat.format(totalPrice);
        holder.order_price.setText(formattedPrice);
    }

    @Override
    public int getItemCount() {
        if (orderItems != null) {
            return orderItems.size();
        }
        return 0;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView order_date;
        TextView order_quantity;
        TextView order_price;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            bindingView();
        }

        void bindingView() {
            order_date = itemView.findViewById(R.id.order_date);
            order_quantity = itemView.findViewById(R.id.order_quantity);
            order_price = itemView.findViewById(R.id.order_price);
        }
    }
}