package com.example.myapplication.activity;
import com.example.myapplication.R;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    TextView Avai,Het,numberQuantity;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        Avai = findViewById(R.id.available);
        Het = findViewById(R.id.hethang);
        numberQuantity = findViewById(R.id.numberQuantity);

        //product detail
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        if (newProduct != null) {
            Glide.with(getApplicationContext()).load(newProduct.getImg()).into(dtImg);
            name.setText(newProduct.getName());
            desc.setText(newProduct.getDescription());
            price.setText(vndFormat.format(newProduct.getPrice()));
            if(newProduct.getQuantity() > 0){
                Avai.setVisibility(View.VISIBLE);
            }else if(newProduct.getQuantity() == 0){
                Het.setVisibility(View.VISIBLE);
            }
            numberQuantity.setText(String.valueOf("Còn "+newProduct.getQuantity()+" sản phẩm trong cửa hàng"));
        }

        if (productModel != null) {
            Glide.with(getApplicationContext()).load(productModel.getImg()).into(dtImg);
            name.setText(productModel.getName());
            desc.setText(productModel.getDescription());
            price.setText(vndFormat.format(productModel.getPrice()));
            if(productModel.getQuantity() > 0){
                Avai.setVisibility(View.VISIBLE);
            }else if(productModel.getQuantity() == 0){
                Het.setVisibility(View.VISIBLE);
            }
            numberQuantity.setText(String.valueOf("Còn "+productModel.getQuantity()+" sản phẩm trong cửa hàng"));
        }

        if (showAllModel != null) {
            Glide.with(getApplicationContext()).load(showAllModel.getImg()).into(dtImg);
            name.setText(showAllModel.getName());
            desc.setText(showAllModel.getDescription());
            price.setText(vndFormat.format(showAllModel.getPrice()));
            if(showAllModel.getQuantity() > 0){
                Avai.setVisibility(View.VISIBLE);
            }else if(showAllModel.getQuantity() == 0){
                Het.setVisibility(View.VISIBLE);
            }
            numberQuantity.setText(String.valueOf("Còn "+showAllModel.getQuantity()+" sản phẩm trong cửa hàng"));
        }
    }
}