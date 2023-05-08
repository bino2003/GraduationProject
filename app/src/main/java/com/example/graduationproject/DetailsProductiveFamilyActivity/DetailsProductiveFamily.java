package com.example.graduationproject.DetailsProductiveFamilyActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.graduationproject.HomeActivity;
import com.example.graduationproject.ProductiveFamilyProfileActivity.ItemProductAdapter;
import com.example.graduationproject.ProductiveFamilyProfileActivity.ItemProductiveFamily;
import com.example.graduationproject.databinding.ActivityDetailsProductiveFamilyBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class DetailsProductiveFamily extends AppCompatActivity {
ActivityDetailsProductiveFamilyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailsProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String id=getIntent().getStringExtra("idproductivefamily");

        ArrayList<String> tabs =new ArrayList<>();
        tabs.add("Products");

        tabs.add(" Profile");

        ArrayList<Fragment> detailsproductivefamilylist=new ArrayList<>();
        detailsproductivefamilylist.add(ItemDetailsProduct.newInstance("Products",id));
        detailsproductivefamilylist.add(DetailsProductiveFamilyProfile.newInstance("Productive family",id));


        Log.d("productlist",detailsproductivefamilylist.toString());
        DetailsProductAdapter detailsProductAdapter=new DetailsProductAdapter(this,detailsproductivefamilylist);
        binding.ViewPager.setAdapter(detailsProductAdapter);
        new TabLayoutMediator(binding.tab, binding.ViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs.get(position));
            }
        }).attach();
    }
}