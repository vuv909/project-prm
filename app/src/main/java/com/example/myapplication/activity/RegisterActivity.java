package com.example.myapplication.activity;
import com.example.myapplication.R;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEditText, passwordEditText, confirmPasswordEditText, btnpnb;
    Button createAccountBtn;
    ProgressBar progressBar;
    TextView loginBtnTextView;
    private FirebaseFirestore db;

    private void bindingView() {
        emailEditText = findViewById(R.id.email_edit_text);
        passwordEditText = findViewById(R.id.password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);
        createAccountBtn = findViewById(R.id.create_account_btn);
        progressBar = findViewById(R.id.progress_bar);
        loginBtnTextView = findViewById(R.id.login_text_view_btn);
        btnpnb = findViewById(R.id.btnpnb);
        db = FirebaseFirestore.getInstance();
    }

    private void bindingAction() {

        createAccountBtn.setOnClickListener(v -> createAccount());
//        createAccountBtn.setOnClickListener(v -> );
        loginBtnTextView.setOnClickListener(v -> Login());
    }

    private void Login() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void signUpFilestore(){
        final String userEmail = emailEditText.getText().toString();
        final String userMobileNumber = btnpnb.getText().toString();

        // Kiểm tra xem email và số điện thoại đã tồn tại trong Firestore chưa
        db.collection("users")
                .whereEqualTo("Email", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> emailTask) {
                        if (emailTask.isSuccessful()) {
                            if (emailTask.getResult().isEmpty()) {
                                // Nếu không có email trùng lặp, tiếp tục kiểm tra số điện thoại
                                db.collection("users")
                                        .whereEqualTo("MobileNumber", userMobileNumber)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> mobileTask) {
                                                if (mobileTask.isSuccessful()) {
                                                    if (mobileTask.getResult().isEmpty()) {
                                                        // Nếu không có số điện thoại trùng lặp, thêm vào Firestore
                                                        Map<String, Object> user = new HashMap<>();
                                                        user.put("Email", userEmail);
                                                        user.put("MobileNumber", userMobileNumber);

                                                        db.collection("users")
                                                                .add(user)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
//                                                                        Toast.makeText(getApplicationContext(), "Data stored Successfully", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(getApplicationContext(), "Error adding document: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });
                                                    } else {
                                                        // Nếu số điện thoại đã tồn tại, hiển thị thông báo
//                                                        Toast.makeText(getApplicationContext(), "Phone number already exists", Toast.LENGTH_SHORT).show();
//                                                        startActivity(new Intent(RegisterActivity.this, RegisterActivity.class));
                                                        btnpnb.setError("Phone Number should be same.");
                                                        Utility.showToast(RegisterActivity.this,"Phone Number should be same.");
                                                    }
                                                } else {
                                                    // Nếu có lỗi khi kiểm tra số điện thoại
                                                    Toast.makeText(getApplicationContext(), "Error checking phone number: " + mobileTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            } else {
                                // Nếu email đã tồn tại, hiển thị thông báo
//                                Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                                btnpnb.setError("Email should be same.");
                                Utility.showToast(RegisterActivity.this,"Email should be same.");
                            }
                        } else {
                            // Nếu có lỗi khi kiểm tra email
                            Toast.makeText(getApplicationContext(), "Error checking email: " + emailTask.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindingView();
        bindingAction();


    }

    void createAccount() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();
        String phoneNumber = btnpnb.getText().toString();
        boolean isValidated = validateData(email,phoneNumber, password, confirmPassword);
        if (!isValidated) {
            return;
        }

        createAccountInFirebase(email, password);


    }

    void createAccountInFirebase(String email, String password) {
        changeInProgress(true);

        // firebaseAuth gửi mail
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        // tạo email và mật khẩu
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegisterActivity.this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        changeInProgress(false);
                        // thành công
                        if (task.isSuccessful()) {
                            //creating acc is done
                            Utility.showToast(RegisterActivity.this, "Successfully create account,Check email to verify");
                            signUpFilestore();
                            startActivity(new Intent(RegisterActivity.this, VerifyActivity.class));
                            // gửi email cho người dung
                            firebaseAuth.getCurrentUser().sendEmailVerification();
                            firebaseAuth.signOut();

                            finish();
                        } else {
                            //failure
                            Utility.showToast(RegisterActivity.this, task.getException().getLocalizedMessage());
                        }
                    }
                }
        );


    }

    void changeInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            createAccountBtn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            createAccountBtn.setVisibility(View.VISIBLE);
        }
    }

    boolean validateData(String email,String phoneNumber, String password, String confirmPassword) {
        //validate the data that are input by user.

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Email is invalid");
            return false;
        }
        if(phoneNumber.length() > 11 && phoneNumber.length() < 10){
            btnpnb.setError("Phone Number length is invalid");
            return false;
        }
        if (password.length() < 6) {
            passwordEditText.setError("Password length is invalid");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordEditText.setError("Password not matched");
            return false;
        }
        return true;
    }

}