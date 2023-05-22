package com.example.graduationproject.DetailsProductiveFamilyActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;



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
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String id=getIntent().getStringExtra("idproductivefamily");
        firebaseFirestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();











        ArrayList<String> tabs =new ArrayList<>();
        tabs.add("Products");
        tabs.add(" Profile");

        ArrayList<Fragment> detailsproductivefamilylist=new ArrayList<>();
        detailsproductivefamilylist.add(ItemDetailsProduct.newInstance("Products",id));
        detailsproductivefamilylist.add(DetailsProductiveFamilyProfile.newInstance("Productive family",id));
firebaseFirestore.collection("Productive family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
   DocumentSnapshot documentSnapshot=task.getResult();
   if (task.isSuccessful()){
       name=documentSnapshot.getString("name");

       binding.name.setText(documentSnapshot.getString("name"));
       Log.d("name prod", documentSnapshot.getString("name"));
       Glide.with(getApplicationContext()).load(Uri.parse(documentSnapshot.getString("image"))).circleCrop().into(binding.imageView3);


   }
    }
});

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