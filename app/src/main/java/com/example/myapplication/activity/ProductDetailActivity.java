package com.example.myapplication.activity;
import com.example.myapplication.R;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.Product;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.NumberFormat;
import java.util.Locale;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView dtImg;
    TextView name,desc,price;
    Button addtocart,buy;
    ImageView plus , minus;

    Product newProduct = null;
    Product productModel = null;

    Product showAllModel  = null;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        firestore = FirebaseFirestore.getInstance();

        //get data product
        Object object = getIntent().getSerializableExtra("detailpro");
        Object object2 = getIntent().getSerializableExtra("detailInList");
        Object object3 = getIntent().getSerializableExtra("detailInShowall");
        newProduct = (Product) object;
        productModel = (Product) object2;
        showAllModel = (Product) object3;
        dtImg = findViewById(R.id.detailed_img);
        name = findViewById(R.id.detailed_name);
        desc = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addtocart = findViewById(R.id.add_to_cart);
        buy = findViewById(R.id.buy);
        plus = findViewById(R.id.add_item);
        minus = findViewById(R.id.minus_item);

        //product detail
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        if (newProduct != null) {
            Glide.with(getApplicationContext()).load(newProduct.getImg()).into(dtImg);
            name.setText(newProduct.getName());
            desc.setText(newProduct.getDescription());
            price.setText(vndFormat.format(newProduct.getPrice()));
        }

        if (productModel != null) {
            Glide.with(getApplicationContext()).load(productModel.getImg()).into(dtImg);
            name.setText(productModel.getName());
            desc.setText(productModel.getDescription());
            price.setText(vndFormat.format(productModel.getPrice()));
        }

        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg()).into(dtImg);
            name.setText(showAllModel.getName());
            desc.setText(showAllModel.getDescription());
            price.setText(vndFormat.format(showAllModel.getPrice()));
        }
    }
}