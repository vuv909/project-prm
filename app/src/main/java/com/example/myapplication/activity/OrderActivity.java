package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.adapter.OrderAdapter;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    // Init UI variable
    RecyclerView rcv_order;

    Gson gson;

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
        gson = new Gson();

        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString("user", null);

        if (userData != null) {
            // Deserialize the user data
            User user = gson.fromJson(userData, User.class);

            db.collection("orders").whereEqualTo("user_id", String.valueOf(user.getId()))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    String orderId = document.getId();  // Retrieve the document ID
                                    Order o = document.toObject(Order.class);
                                    o.setDocumentId(orderId);  // Set the document ID in your Order object

                                    // Retrieve the product data
                                    ArrayList<Map<String, Object>> productList = (ArrayList<Map<String, Object>>) document.get("productList");
                                    if (productList != null && !productList.isEmpty()) {
                                        Map<String, Object> productData = productList.get(0);
                                        String imageProduct = (String) productData.get("image_product");
                                        int price = ((Number) productData.get("price")).intValue();
                                        int productId = ((Number) productData.get("product_id")).intValue();
                                        String productName = (String) productData.get("product_name");
                                        int quantity = ((Number) productData.get("quantity")).intValue();

                                        // Create a Cart object with the retrieved data
//                                        Product product = new Product(productId,productName,imageProduct,price,quantity);
////                                        o.se(cart);
                                    }

                                    orderArrayList.add(o);
                                    orderAdapter.notifyDataSetChanged();
                                }
                                if (!orderArrayList.isEmpty()) {
                                    Toast.makeText(OrderActivity.this, "Order exist", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(OrderActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(this, "Not found user !!!", Toast.LENGTH_SHORT).show();
        }
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