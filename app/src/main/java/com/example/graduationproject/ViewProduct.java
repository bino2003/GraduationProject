package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.graduationproject.databinding.ActivityViewProductBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewProduct extends AppCompatActivity {
ActivityViewProductBinding binding;
FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();


      Intent intent= getIntent();

        String id=intent.getStringExtra("idview");
        firebaseFirestore.collection("Products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getString("image")!=null){
                            Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.uplodeimgview);


                        }
                        binding.tvCategoryview.setText(document.getString("category"));
                        binding.tvDescriptionview.setText(document.getString("description"));
                        binding.tvNameview.setText(document.getString("name"));
                        binding.tvPriceview.setText(document.getString("price"));

//
                    }
                }

            }

        });

//        Glide.with(getApplicationContext()).load(image).circleCrop().into(binding.uplodeimgview);
//
//        binding.tvCategoryview.setText(category);
//        binding.tvDescriptionview.setText(description);
//        binding.tvPriceview.setText(price);
//        binding.tvNameview.setText(name);
        binding.exitview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewProduct.this,ProductiveFamilyProfile.class));
                finish();
            }
        });



    }
}