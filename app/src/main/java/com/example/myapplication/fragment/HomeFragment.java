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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.example.myapplication.models.NewProducts;
import com.example.myapplication.models.ProductModel;
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
    List<NewProducts> productsList;
    CategoryAdapter categoryAdapter;
    NewProductAdapter productAdapter;
    RecyclerView catRecyclerView;
    RecyclerView newProductRecycleview;


    List<ProductModel> productModelList;
    RecyclerView listProductRecycleview;
    ListProductAdapter listProductAdapter;
    //
    FirebaseFirestore db ;

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

        //image slider
        ImageSlider imageSlider = root.findViewById(R.id.image_slider);
        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.advert1,"Discount on Pet Shop", ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.advert2,"Discount new people",ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.advert3,"50%",ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModels);

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


        //New products
        newProductRecycleview.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        productsList = new ArrayList<>();
        productAdapter = new NewProductAdapter(getContext(),productsList);
        newProductRecycleview.setAdapter(productAdapter);

        db.collection("NewProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                NewProducts productNew = document.toObject(NewProducts.class);
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

        db.collection("ListProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                ProductModel productNew = document.toObject(ProductModel.class);
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