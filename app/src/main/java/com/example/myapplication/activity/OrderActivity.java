package com.example.myapplication.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.models.ProOrder;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {

    // UI variables
    private RecyclerView rcvOrder;

    // Adapter and Firestore
    private OrderAdapter orderAdapter;
    private ArrayList<ProOrder> orderArrayList;
    private FirebaseFirestore db;

    // Gson for JSON handling
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Initialize UI elements
        rcvOrder = findViewById(R.id.rcv_order);
        rcvOrder.setLayoutManager(new GridLayoutManager(this, 1));

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        // Initialize Gson
        gson = new Gson();

        // Initialize the orderArrayList and orderAdapter
        orderArrayList = new ArrayList<>();
        orderAdapter = new OrderAdapter(this, orderArrayList);
        rcvOrder.setAdapter(orderAdapter);

        // Load user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString("user", null);

        if (userData != null) {
            // Deserialize the user data
            User user = gson.fromJson(userData, User.class);

            // Query Firestore for orders
            db.collection("orders").whereEqualTo("userId", String.valueOf(user.getId()))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    ProOrder proOrder = document.toObject(ProOrder.class);
                                    orderArrayList.add(proOrder);
                                }
                                orderAdapter.notifyDataSetChanged(); // Notify the adapter of data changes

                                if (!orderArrayList.isEmpty()) {
                                    Toast.makeText(OrderActivity.this, "Order exists", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "User not found!", Toast.LENGTH_SHORT).show();
        }
    }
}
