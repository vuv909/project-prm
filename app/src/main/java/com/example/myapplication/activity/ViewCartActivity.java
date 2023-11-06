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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;

public class ViewCartActivity extends AppCompatActivity {

    // Init UI variable
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
        btn_pay.setOnClickListener(this::onClickButtonPay);
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

                                total_pay += cart.getPrice() * cart.getQuantity();
                                cartAdapter.notifyDataSetChanged();
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


    private void onClickButtonPay(View view) {

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