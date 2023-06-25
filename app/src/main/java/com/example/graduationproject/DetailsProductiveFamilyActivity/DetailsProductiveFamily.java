package com.example.graduationproject.DetailsProductiveFamilyActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;


import com.bumptech.glide.Glide;
import com.example.graduationproject.Adapters.DetailsProductAdapter;
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
import java.util.List;

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
        String id2=getIntent().getStringExtra("id");
        String id_product=getIntent().getStringExtra("id_product");
        firebaseFirestore=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();


        PackageManager packageManager = getPackageManager();
        String packageName = "com.example.graduationproject";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            // Package found
        } catch (PackageManager.NameNotFoundException e) {
            // Package not found
        }
        String packageName2 = "com.example.graduationproject";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(packageName2, 0);
            // Access packageInfo to get details like version, permissions, etc.
        } catch (PackageManager.NameNotFoundException e) {
            // Package not found
        }
        List<PackageInfo> installedPackages = packageManager.getInstalledPackages(0);
// Iterate through installedPackages to access information about each package










        ArrayList<String> tabs =new ArrayList<>();
        tabs.add("Products");
        tabs.add(" Profile");

        ArrayList<Fragment> detailsproductivefamilylist1=new ArrayList<>();
        ArrayList<Fragment> detailsproductivefamilylist=new ArrayList<>();

        if (id!=null){
            detailsproductivefamilylist.add(ItemDetailsProduct.newInstance("Products",id,id_product));
            detailsproductivefamilylist.add(DetailsProductiveFamilyProfile.newInstance("Productive family",id,id_product));
            firebaseFirestore.collection("Productive family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if (task.isSuccessful()){
                        name=documentSnapshot.getString("name");
                        Glide.with(getApplicationContext()).load(documentSnapshot.getString("image")).circleCrop().into(binding.imageView3);

                        binding.name.setText(documentSnapshot.getString("name"));
                        Log.d("name prod", documentSnapshot.getString("name"));
                        // Glide.with(getApplicationContext()).load(Uri.parse(documentSnapshot.getString("image"))).circleCrop().into(binding.imageView3);


                    }
                }
            });
        }else if (id2!=null){
            detailsproductivefamilylist.add(ItemDetailsProduct.newInstance("Products",id2,id_product));
            detailsproductivefamilylist.add(DetailsProductiveFamilyProfile.newInstance("Productive family",id2,id_product));
            firebaseFirestore.collection("Productive family").document(id2).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if (task.isSuccessful()){
                        name=documentSnapshot.getString("name");

                        binding.name.setText(documentSnapshot.getString("name"));
                        Log.d("name prod", documentSnapshot.getString("name"));
                        // Glide.with(getApplicationContext()).load(Uri.parse(documentSnapshot.getString("image"))).circleCrop().into(binding.imageView3);


                    }
                }
            });
        }


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