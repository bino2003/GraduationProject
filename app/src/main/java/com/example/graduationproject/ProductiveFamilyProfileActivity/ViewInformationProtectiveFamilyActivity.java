package com.example.graduationproject.ProductiveFamilyProfileActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;


import com.example.graduationproject.databinding.ActivityViewInformationProtectiveFamilyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewInformationProtectiveFamilyActivity extends AppCompatActivity {

    ActivityViewInformationProtectiveFamilyBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewInformationProtectiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        firebaseFirestore.collection("Productive family").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG 1", "DocumentSnapshot data: " + document.getData());
                        Log.d("current user",  auth.getCurrentUser().getUid());
                        Log.d("name document", document.getString("name")+"category "+document.getString("category"));
//                        binding.imageView2.setImageURI(Uri.parse(document.getString("image")));
                        Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.imageView2);
                        Log.d("image",document.getString("image"));
//                        Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image")).into(binding.imageView2);
                        binding.tvNames.setText(document.getString("name"));
                        binding.tvCategory.setText(document.getString("category"));
                        binding.tvDescroption.setText(document.getString("details"));
                        binding.tvPhone.setText(""+document.getLong("phone").intValue());
//                        double phone=document.getDouble("phone");
//                        int phone
//                        binding.tvPhone.setText((Integer));
                    } else {
                        Log.d("TAG 2", "No such document");
                    }
                } else {
                    Log.d("TAG 3", "get failed with ", task.getException());
                }



            }

        });
        binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), UpdateInformationProductiveFamilyActivity.class));
                finish();
            }
        });


    }
}