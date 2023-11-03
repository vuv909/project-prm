package com.example.myapplication.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.models.User;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {
    Button update;
    EditText imgUrl, username, phoneNumber;
    ImageView imageView;
    FirebaseFirestore db;
    DocumentReference userRef ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        db = FirebaseFirestore.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        imgUrl = findViewById(R.id.imgEditPro);
        imageView = findViewById(R.id.avatarEditProfile);
        username = findViewById(R.id.userEditPro);
        update = findViewById(R.id.updatePro);
        phoneNumber = findViewById(R.id.phoneEditPro);
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            User userModel = (User) intent.getSerializableExtra("user");

            Glide.with(EditProfile.this).load(userModel.getAvatar()).placeholder(R.drawable.iconavatar).into(imageView);

            // Set text from userModel as hints for EditText fields
            username.setHint("Username");
            if (userModel.getUsername() != null && !userModel.getUsername().isEmpty()) {
                username.setHint(userModel.getUsername());
            }

            phoneNumber.setHint("Phone Number");
            if (userModel.getMobileNumber() != null && !userModel.getMobileNumber().isEmpty()) {
                phoneNumber.setHint(userModel.getMobileNumber());
            }


        } else {
            Toast.makeText(this, "Error fetching data !!!", Toast.LENGTH_SHORT).show();
        }
        update.setOnClickListener(this::handleUpdate);
    }


    private void handleUpdate(View view) {
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            User userModel = (User) intent.getSerializableExtra("user");
            int id = userModel.getId();
            String imgLink = imgUrl.getText().toString().trim(); // Assuming imgUrl holds the Base64 image data
            String newUsername = username.getText().toString();
            String newPhoneNumber = phoneNumber.getText().toString();

            if (!newUsername.isEmpty() || !newPhoneNumber.isEmpty() || !imgLink.isEmpty()) {
                db.collection("users")
                        .whereEqualTo("id", id)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    DocumentReference userRef = document.getReference();
                                    Intent intentSuccess = new Intent(this, Profile.class);

                                    if (!imgLink.isEmpty()) {
                                        userRef.update("avatar", imgLink)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(this, "Image updated successfully!", Toast.LENGTH_SHORT).show();
                                                    // You might want to update the intent here before starting the activity
                                                    intentSuccess.putExtra("user", userModel);
                                                    startActivity(intentSuccess);
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(this, "Image update failed! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });
                                    }

                                    if (!newUsername.isEmpty()) {
                                        userRef.update("username", newUsername)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(this, "Username updated successfully!", Toast.LENGTH_SHORT).show();
                                                    intentSuccess.putExtra("user", userModel);
                                                    startActivity(intentSuccess);
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(this, "Username update failed! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });
                                    }

                                    if (!newPhoneNumber.isEmpty()) {
                                        userRef.update("MobileNumber", newPhoneNumber)
                                                .addOnSuccessListener(aVoid -> {
                                                    Toast.makeText(this, "Phone number updated successfully!", Toast.LENGTH_SHORT).show();
                                                    intentSuccess.putExtra("user", userModel);
                                                    startActivity(intentSuccess);
                                                })
                                                .addOnFailureListener(e -> {
                                                    Toast.makeText(this, "Phone number update failed! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                });
                                    }
                                }
                            } else {
                                // Handle the case when the task is not successful
                            }
                        });
            }
        } else {
            Toast.makeText(this, "Error fetching data !!!", Toast.LENGTH_SHORT).show();
        }
    }

}