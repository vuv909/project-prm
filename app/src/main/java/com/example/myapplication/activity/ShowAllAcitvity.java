package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.ShowAllAdapter;
import com.example.myapplication.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;


public class ShowAllAcitvity extends AppCompatActivity {

    private Product showAllModel;

    private ShowAllAdapter showAllAdapter;

    private List<Product> showAllModels;

    FirebaseFirestore db;
    TextView textName ;

    TextView notFound ;

    private RecyclerView recyclerViewShowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_acitvity);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();

        recyclerViewShowAll = findViewById(R.id.showAll);
        textName = findViewById(R.id.titleInShowall);
        notFound = findViewById(R.id.not_found);
        String type = getIntent().getStringExtra("type");
        String name = getIntent().getStringExtra("name");
        String textSearch  =  getIntent().getStringExtra("textSearch");


        recyclerViewShowAll.setLayoutManager(new GridLayoutManager(this, 1));
        showAllModels = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModels);
        recyclerViewShowAll.setAdapter(showAllAdapter);

        if (textSearch != null) {

            textName.setText("Kết quả cho '" + textSearch + "'");
            final String searchTextLower = textSearch.toLowerCase(); // Convert search text to lowercase

            db.collection("Product")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                boolean foundMatch = false;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product productNew = document.toObject(Product.class);
                                    String productName = productNew.getName();

                                    // Check if the lowercase product name contains the lowercase search text
                                    if (productName != null && productName.toLowerCase().contains(searchTextLower)) {
                                        showAllModels.add(productNew);
                                        showAllAdapter.notifyDataSetChanged();
                                        foundMatch = true;
                                    }
                                }
                                if(!foundMatch){
                                    notFound.setVisibility(View.VISIBLE);
                                }
                            } else {
                                Toast.makeText(ShowAllAcitvity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }


        if (type != null) {
            textName.setText(name);
            int typeId = Integer.valueOf(type);
            db.collection("Product").whereEqualTo("category_id", typeId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product productNew = document.toObject(Product.class);
                                    showAllModels.add(productNew);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(ShowAllAcitvity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        if (type == null && textSearch == null) {
            db.collection("Product")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Product productNew = document.toObject(Product.class);
                                    showAllModels.add(productNew);
                                    showAllAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(ShowAllAcitvity.this, "" + task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }



}