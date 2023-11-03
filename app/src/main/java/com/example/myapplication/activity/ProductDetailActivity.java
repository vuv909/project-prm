package com.example.myapplication.activity;
import com.example.myapplication.R;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.models.CartItem;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.User;
import com.google.firebase.firestore.CollectionReference;
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

    Product productInDetail;


    EditText editQuantity;
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
        editQuantity = findViewById(R.id.quantity);
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
        plus.setOnClickListener(this::handlePlus);
        minus.setOnClickListener(this::handleMinus);
        addtocart.setOnClickListener(this::handleAddToCart);

        if (newProduct != null) {
            productInDetail=newProduct;
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
            productInDetail=productModel;
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
            productInDetail=showAllModel;
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

    private void handleAddToCart(View view) {
        // Your existing code to retrieve product and user information

        String quantity = editQuantity.getText().toString(); // Make sure editQuantity is initialized correctly
        String product_id = String.valueOf(productInDetail.getId());
        String image_product = String.valueOf(productInDetail.getImg());
        String price = String.valueOf(productInDetail.getPrice());

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference cartRef = db.collection("Carts");

        CartItem cartItem = new CartItem(1, Integer.valueOf(product_id), image_product, Integer.valueOf(price), Integer.valueOf(quantity));

        // Add the cart item to the "Carts" collection
        cartRef.add(cartItem)
                .addOnSuccessListener(documentReference -> {
                    // Successfully added to the cart
                    Toast.makeText(this, "Added to cart successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Failed to add to the cart
                    Toast.makeText(this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                });
    }


    private void handlePlus(View view) {
        String add = String.valueOf(Integer.valueOf(editQuantity.getText().toString())+1);
        runOnUiThread(() -> editQuantity.setText(add));
    }

    private void handleMinus(View view) {
        int currentValue = Integer.parseInt(editQuantity.getText().toString());

        if (currentValue > 0) {
            final String subtract = String.valueOf(currentValue - 1);
            runOnUiThread(() -> editQuantity.setText(subtract));
        }
    }


}