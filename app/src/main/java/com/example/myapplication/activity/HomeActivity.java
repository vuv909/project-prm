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

public class HomeActivity extends AppCompatActivity {
    SharedPreferences sharePreferences;
    Fragment homeFragment;
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
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        System.out.println("email "+email);
        System.out.println("is login "+isLoggedIn);
        if (!isLoggedIn || email.isEmpty()) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home);
            homeFragment = new HomeFragment();
            onBindingAction();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.nav_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.menu_item);

        // Set the action view of the menu item
        menuItem.setActionView(R.layout.menu_action_view);

        // Retrieve the action view
        View actionView = menuItem.getActionView();

        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        final User[] userModel = new User[1];

        sharePreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String email = sharePreferences.getString("email", "");
        if (!email.isEmpty()) {
            //handle async
            firestore.collection("users").whereEqualTo("Email", email.trim())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    userModel[0] = user;

                                    // Update UI or perform actions with the user data here
                                    if (userModel[0] != null) {
                                        // Display the user
                                        ImageView imageView = actionView.findViewById(R.id.image_view_in_menu);
                                        Glide.with(HomeActivity.this)
                                                .load(userModel[0].getAvatar())
                                                .placeholder(R.drawable.iconavatar)
                                                .into(imageView);
                                    }
                                }
                            } else {
                                // Handle the case when the task is not successful
                            }
                        }
                    });

            // You might not need to set an icon since the actionLayout will handle the image display
            return true;
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
            Toast.makeText(this, "Watch Order selected", Toast.LENGTH_SHORT).show();
            return true;
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }





}
