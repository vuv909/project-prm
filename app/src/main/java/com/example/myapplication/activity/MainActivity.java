package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.started);

        btn.setOnClickListener(this::handleStart);
    }

    private void handleStart(View view) {
        Intent intent = new Intent(MainActivity.this , SilderActivity.class);
        startActivity(intent);
    }
}