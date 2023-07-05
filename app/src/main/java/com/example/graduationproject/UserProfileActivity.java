package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.graduationproject.Fragments.ProfileFragment;
import com.example.graduationproject.Model.users;
import com.example.graduationproject.databinding.ActivityUserProfileBinding;
import com.example.graduationproject.databinding.ActivityUsersProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import org.checkerframework.checker.nullness.qual.NonNull;

public class UserProfileActivity extends AppCompatActivity {
    ActivityUserProfileBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("firebaseUser", firebaseAuth.getCurrentUser().getEmail());
        Log.d("uid2", firebaseAuth.getUid());

        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UserProfileActivity.this, "succesfully", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UserProfileActivity.this, "Not Successfuly", Toast.LENGTH_SHORT).show();
                }
                DocumentSnapshot documentSnapshot = task.getResult();


//                  firebaseFirestore.collection("Productive Family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                      @Override
//                      public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
//                          DocumentSnapshot documentSnapshot1=task.getResult();
//                          documentSnapshot1.
////                   ProductiveFamily productiveFamily=       task.getResult().toObject(ProductiveFamily.class);
////                   rating.add(String.valueOf(r));
////                productiveFamily.setEvaluation(rating);
//
//                      }
//                  });


                binding.etUserName.setText(documentSnapshot.getString("name"));
                binding.etUserName.setText("documentSnapshot.getStri");

                binding.etLocation.setText(documentSnapshot.getString("location"));
                binding.etPhoneNumber.setText("" + documentSnapshot.getLong("phone").intValue());

                final String email = firebaseAuth.getCurrentUser().getEmail();
                binding.btnCreate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AuthCredential credential = EmailAuthProvider.getCredential(binding.etEmailAddress.getText().toString(), binding.etPassword.getText().toString());
                        firebaseUser.reauthenticate(credential)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            firebaseUser.updatePassword(binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Toast.makeText(UserProfileActivity.this, "Password Successfully", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        Toast.makeText(UserProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });
                                        } else {
                                            Toast.makeText(UserProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        users user = new users();
                        user.setLocation(binding.etLocation.getText().toString());
                        user.setPhone(binding.etPhoneNumber.getText().toString());
                        user.setName(binding.etUserName.getText().toString());
                        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                Toast.makeText(UserProfileActivity.this, "Successfuly", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                binding.btnChangepassword.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(), ChangePassword.class));

                    }
                });


            }
        });

    }
}