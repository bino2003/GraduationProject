package com.example.graduationproject.DetailsProductiveFamilyActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.graduationproject.HomeActivity;
import com.example.graduationproject.ProductiveFamilyProfileActivity.ItemProductAdapter;
import com.example.graduationproject.ProductiveFamilyProfileActivity.ItemProductiveFamily;


import com.example.graduationproject.databinding.ActivityDetailsProductiveFamilyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class DetailsProductiveFamily extends AppCompatActivity {
ActivityDetailsProductiveFamilyBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsProductiveFamilyBinding.inflate(getLayoutInflater());
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
                        Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.imageView3);
                        Log.d("image",document.getString("image"));
//                        Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image")).into(binding.imageView2);
                        binding.name.setText(document.getString("name"));
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





        String id=getIntent().getStringExtra("idproductivefamily");

        ArrayList<String> tabs =new ArrayList<>();
        tabs.add("Products");

        tabs.add(" Profile");

        ArrayList<Fragment> detailsproductivefamilylist=new ArrayList<>();
        detailsproductivefamilylist.add(ItemDetailsProduct.newInstance("Products",id));
        detailsproductivefamilylist.add(DetailsProductiveFamilyProfile.newInstance("Productive family"));


        Log.d("productlist",detailsproductivefamilylist.toString());
        DetailsProductAdapter detailsProductAdapter=new DetailsProductAdapter(this,detailsproductivefamilylist);
        binding.ViewPager.setAdapter(detailsProductAdapter);
        new TabLayoutMediator(binding.tab, binding.ViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.@NonNull Tab tab, int position) {
                tab.setText(tabs.get(position));
            }
        }).attach();
    }
}