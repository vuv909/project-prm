package com.example.myapplication.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.adapter.CartAdapter;
import com.example.myapplication.models.Cart;
import com.example.myapplication.models.Order;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewCartActivity extends AppCompatActivity {

    // Init UI variable
    RecyclerView rcv_carts;
    Button btn_pay;
    static TextView tv_total_price;

    // Init adapter & firebase
    CartAdapter cartAdapter;
    ArrayList<Cart> cartArrayList;
    FirebaseFirestore db;

    // Init temp variable
    public static int total_pay = 0;
    public static List<Order> listOrder = new ArrayList<>();

    private void bindingView() {
        rcv_carts = findViewById(R.id.rcv_cart);
        btn_pay = findViewById(R.id.btn_datHang);
        tv_total_price = findViewById(R.id.tv_total_price);
    }

    private void bindingAction() {
        btn_pay.setOnClickListener(this::onClickButtonPay);
    }
    public  static  void setTotal_pay(int total){
        tv_total_price.setText(Integer.toString(total_pay));
    }
    private void process() {
        rcv_carts.setLayoutManager(new GridLayoutManager(this, 1));
        cartArrayList = new ArrayList<>();
        db = FirebaseFirestore.getInstance();
        cartAdapter = new CartAdapter(this, cartArrayList);
        rcv_carts.setAdapter(cartAdapter);
        db.collection("Carts").whereEqualTo("user_id", 1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Cart cart = document.toObject(Cart.class);
                                cartArrayList.add(cart);
                                cartAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(ViewCartActivity.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
//        for (Cart c : cartArrayList) {
//            total_pay += c.getPrice() * c.getQuantity();
//        }
//        tv_total_price.setText(Integer.toString(total_pay));
    }

    private void onClickButtonPay(View view) {
        // Add list order to firebase
        String x = listOrder.get(1).getProduct_name();
        Toast.makeText(this, x, Toast.LENGTH_SHORT).show();
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