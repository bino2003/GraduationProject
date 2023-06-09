package com.example.graduationproject.DetailsProductiveFamilyActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.graduationproject.Adapters.CategoryProductFamilyAdapter;
import com.example.graduationproject.Adapters.DetailsProductAdapter;
import com.example.graduationproject.CategoryProductiveFamily;
import com.example.graduationproject.HandleEmpityActivity;
import com.example.graduationproject.Interface.OnClickProductiveFamily;
import com.example.graduationproject.Model.ProductiveFamily;
import com.example.graduationproject.Model.users;
import com.example.graduationproject.databinding.ActivityDetailsProductiveFamilyBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class DetailsProductiveFamily extends AppCompatActivity {
    private static final String WHATSAPP_PACKAGE = "com.whatsapp";

    ActivityDetailsProductiveFamilyBinding binding;
    FirebaseFirestore firebaseFirestore;
    String phoneNumber;


    FirebaseAuth auth=FirebaseAuth.getInstance();
    public static String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

// Inside your onCreate or onViewCreated method

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
        tabs.add("products");
        tabs.add(" profile");

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
                        Glide.with(getApplicationContext()).load(documentSnapshot.getString("image")).circleCrop().into(binding.imageView3);

                        binding.name.setText(documentSnapshot.getString("name"));
//                        Log.d("name prod", documentSnapshot.getString("name"));
                        // Glide.with(getApplicationContext()).load(Uri.parse(documentSnapshot.getString("image"))).circleCrop().into(binding.imageView3);


                    }
                }
            });
        }
        else if (getIntent().getStringExtra("id")!=null){
            detailsproductivefamilylist.add(ItemDetailsProduct.newInstance("Products",getIntent().getStringExtra("id"),getIntent().getStringExtra("id_product")));
            detailsproductivefamilylist.add(DetailsProductiveFamilyProfile.newInstance("Productive family",getIntent().getStringExtra("id"),getIntent().getStringExtra("id_product")));
            firebaseFirestore.collection("Productive family").document(getIntent().getStringExtra("id")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if (task.isSuccessful()){
                        name=documentSnapshot.getString("name");
                        Glide.with(getApplicationContext()).load(documentSnapshot.getString("image")).circleCrop().into(binding.imageView3);

                        binding.name.setText(documentSnapshot.getString("name"));
//                        Log.d("name prod", documentSnapshot.getString("name"));
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
    private void openWhatsAppChat(String phoneNumber, String message) {
        try {
            // Format the phone number to include the country code and remove any special characters
            phoneNumber = phoneNumber.replaceAll("[^0-9+]", "");

            // Open WhatsApp with the predefined message
            Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + URLEncoder.encode(message, "UTF-8"));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to open WhatsApp.", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isWhatsAppInstalled(String url) {
        PackageManager packageManager = getPackageManager();
        boolean app_installed;
        try {
            packageManager.getPackageInfo(url, PackageManager.GET_ACTIVITIES);
            app_installed= true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed= false;
        }
        return app_installed;
    }

    // Open WhatsApp with the specified phone number and message

}