package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    // Init UI variable
    RecyclerView rcv_order;

    // Init adapter & firebase
    OrderAdapter orderAdapter;
    ArrayList<Order> orderArrayList;
    FirebaseFirestore db;

    // Init temp variable
    private int total_pay = 0;

    private void bindingView() {
        rcv_order = findViewById(R.id.rcv_order);
    }
    private void process() {
        rcv_order.setLayoutManager(new GridLayoutManager(this, 1));
        orderArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        orderAdapter = new OrderAdapter(this, orderArrayList);
        rcv_order.setAdapter(orderAdapter);
        db.collection("orders").whereEqualTo("user_id", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Order o = document.toObject(Order.class);
                                orderArrayList.add(o);
                                orderAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(OrderActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void bindingAction() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        bindingView();
        process();
        bindingAction();
    }


}