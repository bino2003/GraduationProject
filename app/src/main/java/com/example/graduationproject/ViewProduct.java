package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.graduationproject.databinding.ActivityViewProductBinding;
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
    String price=    intent.getStringExtra("price");
        String category=    intent.getStringExtra("category");
        String description=    intent.getStringExtra("description");
        String name=    intent.getStringExtra("name");
        String image =intent.getStringExtra("image");
        Glide.with(getApplicationContext()).load(image).circleCrop().into(binding.uplodeimgview);

        binding.tvCategoryview.setText(category);
        binding.tvDescriptionview.setText(description);
        binding.tvPriceview.setText(price);
        binding.tvNameview.setText(name);
        binding.exitview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ViewProduct.this,ProductiveFamilyProfile.class));
                finish();
            }
        });



    }
}