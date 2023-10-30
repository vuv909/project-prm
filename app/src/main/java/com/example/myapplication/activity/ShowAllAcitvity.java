package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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


    private RecyclerView recyclerViewShowAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_acitvity);

        db = FirebaseFirestore.getInstance();

        recyclerViewShowAll = findViewById(R.id.showAll);

        String type = getIntent().getStringExtra("type");


        recyclerViewShowAll.setLayoutManager(new GridLayoutManager(this, 2));
        showAllModels = new ArrayList<>();
        showAllAdapter = new ShowAllAdapter(this, showAllModels);
        recyclerViewShowAll.setAdapter(showAllAdapter);

        if (type != null) {
            db.collection("Category").whereEqualTo("type", type)
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
        if (type == null) {
            db.collection("ListProducts")
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