package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;


import com.example.graduationproject.databinding.ActivityResetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.checkerframework.checker.nullness.qual.NonNull;


public class Reset_password extends AppCompatActivity {
ActivityResetPasswordBinding binding;
FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityResetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       String email=binding.etEmailResetpassword.getText().toString();
       if (TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches()){
           Toast.makeText(Reset_password.this, "Enter your registered email id", Toast.LENGTH_SHORT).show();
           return;
       }
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Reset_password.this, "Check your email", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Reset_password.this, "Unable to send, failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
       
            }
        });

    }
}