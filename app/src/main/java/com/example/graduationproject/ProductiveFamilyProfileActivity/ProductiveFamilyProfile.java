package com.example.graduationproject.ProductiveFamilyProfileActivity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.graduationproject.Adapters.ItemProductAdapter;
import com.example.graduationproject.Fragments.InformationProdectiveFamilyFragment;
import com.example.graduationproject.Fragments.ItemProductiveFamily;
import com.example.graduationproject.HomeActivity;


import com.example.graduationproject.databinding.ActivityProductiveFamilyProfileBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class ProductiveFamilyProfile extends AppCompatActivity {
    ActivityProductiveFamilyProfileBinding binding;
    public String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProductiveFamilyProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        id=getIntent().getStringExtra("id");
        Log.d("id_productive_family", id);

        binding.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ArrayList<String> tabs =new ArrayList<>();
        tabs.add("Products");
        tabs.add(" Profile");

        ArrayList<Fragment> item_productArrayList=new ArrayList<>();
        item_productArrayList.add(ItemProductiveFamily.newInstance("Products",id,getIntent().getStringExtra("id_product")));
        item_productArrayList.add(InformationProdectiveFamilyFragment.newInstance(id,getIntent().getStringExtra("id_product")));


        Log.d("productlist",item_productArrayList.toString());
        ItemProductAdapter itemProductAdapter=new ItemProductAdapter(this,item_productArrayList);
        binding.ViewPager.setAdapter(itemProductAdapter);
        new TabLayoutMediator(binding.tab, binding.ViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.@NonNull Tab tab, int position) {
                tab.setText(tabs.get(position));
            }
        }).attach();
    }


}