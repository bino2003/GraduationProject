package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.graduationproject.databinding.ActivityChangePasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class ChangePassword extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseUser user ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityChangePasswordBinding binding=ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();
      binding.btnChangepassword.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AuthCredential credential = EmailAuthProvider.getCredential(firebaseAuth.getCurrentUser().getEmail(), binding.etOldPass.getText().toString());
              if (!binding.etNewPass.getText().toString().equals(binding.etConfiPass.getText().toString())){
                  Toast.makeText(getApplication(), "The password is not the same", Toast.LENGTH_SHORT).show();
              }
              firebaseUser.reauthenticate(credential)
                      .addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              if (task.isSuccessful()) {
                                  firebaseUser.updatePassword(binding.etNewPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                      @Override
                                      public void onComplete(@NonNull Task<Void> task) {
                                          if (task.isSuccessful()) {
                                              Toast.makeText(getApplication(), "Successfully", Toast.LENGTH_SHORT).show();
                                              finish();
                                          }
                                          else {
                                              Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                          }
                                      }
                                  });
                              } else {
                                  Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                              }
                          }
                      });
          }
      });

    }
}