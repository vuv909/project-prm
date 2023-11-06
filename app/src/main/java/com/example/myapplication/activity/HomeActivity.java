package com.example.myapplication.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.R;
import com.example.myapplication.fragment.HomeFragment;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sharePreferences;
    Fragment homeFragment;
    FirebaseFirestore firestore;
    private void onBindingAction(){
        loadFragment(homeFragment);
    }


    private void loadFragment(Fragment homeFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_container,homeFragment);
        transaction.commit();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.home);
            homeFragment = new HomeFragment();
            onBindingAction();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item);
       firestore = FirebaseFirestore.getInstance();
        menuItem.setActionView(R.layout.menu_action_view);

        View actionView = menuItem.getActionView();
        ImageView imageView = actionView.findViewById(R.id.image_view_in_menu);

        Gson gson = new Gson();

        // Retrieve user data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userData = sharedPreferences.getString("user", null);

        if (userData != null) {
            // Deserialize the user data
            User user = gson.fromJson(userData, User.class);

            firestore.collection("users").whereEqualTo("Email", user.getEmail().trim())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    // Update UI or perform actions with the user data here
                                    if (user != null) {
                                        // Display the user
                                        ImageView imageView = actionView.findViewById(R.id.image_view_in_menu);
                                        Glide.with(HomeActivity.this)
                                                .load(user.getAvatar())
                                                .placeholder(R.drawable.iconavatar)
                                                .into(imageView);
                                    }
                                }
                            } else {

                            }
                        }
                    });

        } else {
            // Handle the case where user data is not available in SharedPreferences
            // You can show a default image or handle the situation as needed
        }

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.menu_show_profile) {
            Intent intent = new Intent(HomeActivity.this,Profile.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.menu_watch_order) {
            Intent intent = new Intent(this,OrderActivity.class);
            startActivity(intent);
            // Toast.makeText(this, "Watch Order selected", Toast.LENGTH_SHORT).show();
            //return true;
        } else if (id == R.id.menu_log_out) {
            sharePreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharePreferences.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(this, "Log out successfully !!!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);

            return true;
        }
        // Luc code them item cart
        else if (id == R.id.menu_cart){
            Intent intent = new Intent(this,ViewCartActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
