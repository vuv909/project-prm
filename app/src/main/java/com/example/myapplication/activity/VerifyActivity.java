package com.example.myapplication.activity;
import com.example.myapplication.R;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerifyActivity extends AppCompatActivity {
    TextView loginBtnTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        loginBtnTextView = findViewById(R.id.login_text_view_btn);
        loginBtnTextView.setOnClickListener(this::exitLogin);
    }

    private void exitLogin(View view) {
        startActivity(new Intent(VerifyActivity.this, com.example.myapplication.activity.LoginActivity.class));
    }
}