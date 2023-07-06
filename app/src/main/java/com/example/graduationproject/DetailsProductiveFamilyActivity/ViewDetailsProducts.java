package com.example.graduationproject.DetailsProductiveFamilyActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.example.graduationproject.Adapters.ViewPagerAdapter;
import com.example.graduationproject.databinding.ActivityViewDetailsProductsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ViewDetailsProducts extends AppCompatActivity {
ActivityViewDetailsProductsBinding binding;
    FirebaseFirestore firebaseFirestore;
    ArrayList<String> ChooseImageList;
    ArrayList<Uri> uriArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityViewDetailsProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        firebaseFirestore=FirebaseFirestore.getInstance();


        Intent intent= getIntent();

        String id=intent.getStringExtra("detailsproductid");
        firebaseFirestore.collection("Products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document1 = task.getResult();
                    ChooseImageList  = (ArrayList<String>) document1.get("imageUrls");
//                    if (task.getResult().getString("image"))
                    uriArrayList=new ArrayList<>();
//                    if (ChooseImageList.size()==0){
//                        Toast.makeText(ViewDetailsProducts.this, "ss", Toast.LENGTH_SHORT).show();
//                    }
                    for (int i = 0; i < ChooseImageList.size(); i++) {
                        uriArrayList.add(Uri.parse(ChooseImageList.get(i)));
                    }

//                        Log.d("uri", ChooseImageList.toString());
                    if (document1.get("imageUrls")!=null){
                        ViewPagerAdapter adapter=new ViewPagerAdapter(getBaseContext(), uriArrayList,true);
                        binding.viewPager.setAdapter(adapter);
                        binding.indicator.setViewPager(binding.viewPager);
                        binding.indicator.createIndicators(5,0);
                        binding.indicator.animatePageSelected(0);
                    }
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