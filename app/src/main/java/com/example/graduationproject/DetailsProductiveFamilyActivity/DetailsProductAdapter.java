package com.example.graduationproject.DetailsProductiveFamilyActivity;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;




public class DetailsProductAdapter extends FragmentStateAdapter {
    ArrayList<Fragment> fragmentArrayList;
    public DetailsProductAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<Fragment> fragmentArrayList) {
        super(fragmentActivity);
        this.fragmentArrayList=fragmentArrayList;
    }
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentArrayList.get(position);
    }
    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
}


