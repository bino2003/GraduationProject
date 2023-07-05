package com.example.graduationproject.ProductiveFamilyProfileActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;


import com.example.graduationproject.Adapters.ViewPagerAdapter;
import com.example.graduationproject.DetailsProductiveFamilyActivity.DetailsProductiveFamily;
import com.example.graduationproject.Fragments.ProfileFragment;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.ActivityViewProductBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ViewProduct extends AppCompatActivity {
ActivityViewProductBinding binding;
FirebaseFirestore firebaseFirestore;
    ArrayList<String> ChooseImageList;
    ArrayList<Uri> uriArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewProductBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();


      Intent intent= getIntent();

        String id=intent.getStringExtra("idview");
        if (intent.getStringExtra("idview")!=null){
            firebaseFirestore.collection("Products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document1 = task.getResult();
                        ChooseImageList  = (ArrayList<String>) document1.get("imageUrls");
                        uriArrayList=new ArrayList<>();
                        for (int i = 0; i < ChooseImageList.size(); i++) {
                            uriArrayList.add(Uri.parse(ChooseImageList.get(i)));
                        }
                        if (document1.get("imageUrls")!=null){
                            ViewPagerAdapter adapter=new ViewPagerAdapter(getBaseContext(), uriArrayList,true);
                            binding.viewPager.setAdapter(adapter);
                        }
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            if (document.getString("image")!=null){
//                                Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.imageView);


                            }
                            binding.etCategryViewproduct.setText(document.getString("category"));
                            binding.etDescriptionViewproduct.setText(document.getString("description"));
                            binding.etNameViewproduct.setText(document.getString("name"));
                            binding.etPriceViewproduct.setText(document.getString("price"));
                            binding.etNameProductiveFamily.setText(document.getString("productive_family"));

//
                        }
                    }

                }

            });
        }
        else if (getIntent().getStringExtra("id_product")!=null){
            firebaseFirestore.collection("Products").document(getIntent().getStringExtra("id_product")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                       ChooseImageList  = (ArrayList<String>) document.get("imageUrls");
                        uriArrayList=new ArrayList<>();
                        for (int i = 0; i < ChooseImageList.size(); i++) {
                            uriArrayList.add(Uri.parse(ChooseImageList.get(i)));
                        }

//                        Log.d("uri", ChooseImageList.toString());
                        if (document.get("imageUrls")!=null){
                            ViewPagerAdapter adapter=new ViewPagerAdapter(getBaseContext(), uriArrayList,true);
                            binding.viewPager.setAdapter(adapter);
                        }
                        if (document.exists()) {
                            if (document.getString("image")!=null){
//                                Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.imageView);


                            }
                            binding.etCategryViewproduct.setText(document.getString("category"));
                            binding.etDescriptionViewproduct.setText(document.getString("description"));
                            binding.etNameViewproduct.setText(document.getString("name"));
                            binding.etPriceViewproduct.setText(document.getString("price"));
                            binding.etNameProductiveFamily.setText(document.getString("productive_family"));

//
                        }
                    }

                }

            });
            binding.etNameProductiveFamily.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
           Intent intent=new Intent(getBaseContext(), DetailsProductiveFamily.class);
           intent.putExtra("id",getIntent().getStringExtra("id"));
           intent.putExtra("id_product",getIntent().getStringExtra("id_product"));
           startActivity(intent);
                }
            });
        }



//        Glide.with(getApplicationContext()).load(image).circleCrop().into(binding.uplodeimgview);
//
//        binding.tvCategoryview.setText(category);
//        binding.tvDescriptionview.setText(description);
//        binding.tvPriceview.setText(price);
//        binding.tvNameview.setText(name);




    }
//    private void setAdapter() {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(this, uriArrayList);
//        binding.viewPager.setAdapter(adapter);
//    }
}