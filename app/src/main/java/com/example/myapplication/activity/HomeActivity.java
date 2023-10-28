package com.example.myapplication.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.R;
import com.example.myapplication.R;
import com.example.myapplication.fragment.HomeFragment;

public class HomeActivity extends AppCompatActivity {
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        homeFragment = new HomeFragment();
        onBindingAction();
    }
}
