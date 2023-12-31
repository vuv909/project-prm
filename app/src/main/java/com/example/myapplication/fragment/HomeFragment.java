package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.myapplication.R;
import com.example.myapplication.activity.ShowAllAcitvity;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.ListProductAdapter;
import com.example.myapplication.adapter.NewProductAdapter;
import com.example.myapplication.models.Category;

import com.example.myapplication.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    TextView newProShowall, listProductShowall;

    ProgressDialog progressDialog;

    LinearLayout linearLayout;

    //Category recycleview
    List<Category> categoryList;
    List<Product> productsList;
    CategoryAdapter categoryAdapter;
    NewProductAdapter productAdapter;
    RecyclerView catRecyclerView;
    RecyclerView newProductRecycleview;


    List<Product> productModelList;
    RecyclerView listProductRecycleview;
    ListProductAdapter listProductAdapter;
    //
    FirebaseFirestore db ;

    EditText textSearch;
    Button searchAction;

    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root =  inflater.inflate(R.layout.fragment_home, container, false);
        db = FirebaseFirestore.getInstance();
        //add progress bar
        progressDialog = new ProgressDialog(getActivity());

        catRecyclerView = root.findViewById(R.id.rec_category);
        newProductRecycleview =root.findViewById(R.id.new_product_rec);
        listProductRecycleview = root.findViewById(R.id.popular_rec);

        newProShowall = root.findViewById(R.id.newProducts_see_all);
        listProductShowall = root.findViewById(R.id.popular_see_all);
        textSearch = root.findViewById(R.id.input_search);
        searchAction = root.findViewById(R.id.search_action);

        searchAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textSearch.getText().toString().trim().equals("")) {
                    Toast.makeText(getContext(), "Vui lòng không bỏ trống trường tìm kiếm !!!", Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(getContext(),ShowAllAcitvity.class);
                    intent.putExtra("textSearch", textSearch.getText().toString().trim());
                    startActivity(intent);
                }
                }
        });


        newProShowall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllAcitvity.class);
                startActivity(intent);
            }
        });

        listProductShowall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ShowAllAcitvity.class);
                startActivity(intent);
            }
        });







        linearLayout = root.findViewById(R.id.home_layout);
        linearLayout.setVisibility(View.GONE);



        progressDialog.setTitle("Chao mung ban tro lai !!!");
        progressDialog.setMessage("please wait ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();


        //Category
        catRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(getContext(),categoryList);
        catRecyclerView.setAdapter(categoryAdapter);


        db.collection("Category")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              Category category = document.toObject(Category.class);
                              categoryList.add(category);
                              categoryAdapter.notifyDataSetChanged();
                              linearLayout.setVisibility(View.VISIBLE);
                              progressDialog.dismiss();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        //Top sellert product
        newProductRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        productsList = new ArrayList<>();
        productAdapter = new NewProductAdapter(getContext(),productsList);
        newProductRecycleview.setAdapter(productAdapter);

        db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product productNew = document.toObject(Product.class);
                                productsList.add(productNew);
                                productAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        //List product
        listProductRecycleview.setLayoutManager(new GridLayoutManager(getActivity(),3));
        productModelList = new ArrayList<>();
        listProductAdapter = new ListProductAdapter(getContext(),productModelList);
        listProductRecycleview.setAdapter(listProductAdapter);

        db.collection("Product")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Product productNew = document.toObject(Product.class);
                                productModelList.add(productNew);
                                listProductAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(), ""+task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        return  root;
    }




}