package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.Order;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ViewCartActivity extends AppCompatActivity {

    // Init UI variable
    Gson gson;
    RecyclerView rcv_carts;
    Button btn_pay;
    TextView tv_total_price;

    // Init adapter & firebase
    CartAdapter cartAdapter;
    ArrayList<Cart> cartArrayList;
    FirebaseFirestore db;

    // Init temp variable
    private int total_pay = 0;

    private void bindingView() {
        rcv_carts = findViewById(R.id.rcv_cart);
        btn_pay = findViewById(R.id.btn_pay);
        tv_total_price = findViewById(R.id.tv_total_price);

    }



    private void bindingAction() {
        btn_pay.setOnClickListener(this::handleSaveOrder);
    }

    private void process() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextView notExist = findViewById(R.id.cart_notfound);
        rcv_carts.setLayoutManager(new GridLayoutManager(this, 1));
        cartArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        cartAdapter = new CartAdapter(this, cartArrayList);
        rcv_carts.setAdapter(cartAdapter);
        Gson gson = new Gson();

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString("user", null);

        if (userData != null) {
            User user = gson.fromJson(userData, User.class);

            db.collection("Carts").whereEqualTo("user_id", user.getId())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Cart cart = document.toObject(Cart.class);
                                    cartArrayList.add(cart);

                                    total_pay += cart.getPrice() * cart.getQuantity();
                                    cartAdapter.notifyDataSetChanged();
                                }
                                if (cartArrayList.isEmpty()) {
                                    notExist.setVisibility(View.VISIBLE);
                                }
                                NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
                                vndFormat.setCurrency(Currency.getInstance("VND"));
                                String total_pay_vnd = vndFormat.format(total_pay);
                                tv_total_price.setText(total_pay_vnd);
                            } else {
                                Toast.makeText(ViewCartActivity.this, "error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private void handleSaveOrder(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Gson gson = new Gson();
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(currentDate);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString("user", null);
        if (userData != null) {
            User user = gson.fromJson(userData, User.class);
            String userId = String.valueOf(user.getId());
            if(total_pay > 0){
            // Create a map to store the order data
            Map<String, Object> orderData = new HashMap<>();
            orderData.put("userId", userId);
            orderData.put("orderDate", formattedDateTime);
            orderData.put("totalPrice", total_pay);
            orderData.put("totalProduct", cartArrayList.size());

            // Add the order data to the Firestore collection
            db.collection("orders")
                    .add(orderData)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // Delete the user's cart
                            db.collection("Carts")
                                    .whereEqualTo("user_id", Integer.parseInt(userId))
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                        @Override
                                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                document.getReference().delete();
                                            }

                                            Toast.makeText(ViewCartActivity.this, "Order saved successfully", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ViewCartActivity.this,OrderActivity.class));
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ViewCartActivity.this, "Failed to delete cart", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(ViewCartActivity.this, "Failed to save order", Toast.LENGTH_SHORT).show();
                        }
                    });

        }}else{
            Toast.makeText(this, "Có lỗi khi tiến hành đặt hàng !!!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        bindingView();
        process();
        bindingAction();
    }
}