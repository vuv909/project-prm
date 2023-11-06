package com.example.myapplication.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.Category;
import com.example.myapplication.models.Product;
import com.example.myapplication.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    ImageView avatar;
    TextView username,mobilephone,emailInDisplay,userpro;
    FirebaseFirestore db ;
    String email;
    Button edit;

    User userModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = FirebaseFirestore.getInstance();
        avatar = findViewById(R.id.avatarProfile);
        userpro=findViewById(R.id.userPro);
        edit = findViewById(R.id.editPro);
        username=findViewById(R.id.usernamePro);
        mobilephone=findViewById(R.id.mobilePro);
        emailInDisplay=findViewById(R.id.emailPro);
        edit.setOnClickListener(this::handleClick);

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        if(!email.isEmpty()){
            //handle async
            db.collection("users").whereEqualTo("Email", email.trim())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    User user = document.toObject(User.class);
                                    userModel = user;

                                    // Update UI or perform actions with the user data here
                                    if (userModel != null) {
                                        // Display the user
                                        Glide.with(Profile.this).load(userModel.getAvatar()).placeholder(R.drawable.iconavatar).into(avatar);
                                        userpro.setText(userModel.getUsername());
                                        username.setText(userModel.getUsername());
                                        mobilephone.setText(userModel.getMobileNumber());
                                        emailInDisplay.setText(userModel.getEmail());

                                        // Show Toast with user's username
                                        Toast.makeText(Profile.this, "Hello " + userModel.getUsername(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                // Handle the case when the task is not successful
                            }
                        }
                    });


        }



    }

    private void handleClick(View view) {
        Intent intent = new Intent(Profile.this,EditProfile.class);
        intent.putExtra("user",userModel);
        startActivity(intent);
    }
}