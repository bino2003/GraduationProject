package com.example.graduationproject.DetailsProductiveFamilyActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;


import com.example.graduationproject.databinding.ActivityViewDetailsProductsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewDetailsProducts extends AppCompatActivity {
ActivityViewDetailsProductsBinding binding;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewDetailsProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();


        Intent intent= getIntent();

        String id=intent.getStringExtra("detailsproductid");
        firebaseFirestore.collection("Products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getString("image")!=null){
//                            Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.imageView);


                        }
                        binding.etCategryViewdetailsproduct.setText(document.getString("category"));
                        binding.etDescriptionViewdetailsproduct.setText(document.getString("description"));
                        binding.etNameViewdetailsproduct.setText(document.getString("name"));
                        binding.etPriceViewdetailsproduct.setText(document.getString("price"));

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




    }
}