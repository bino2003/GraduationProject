package com.example.graduationproject.ProductiveFamilyProfileActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.graduationproject.HomeActivity;
import com.example.graduationproject.ProductiveFamily.InformationProdectiveFamilyFragment;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.FragmentCategoryBinding;
import com.example.graduationproject.databinding.FragmentProfile2Binding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    ArrayList<String> tabs =new ArrayList<>();


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       FragmentProfile2Binding binding=FragmentProfile2Binding.inflate(inflater,container,false);

        ArrayList<String> tabs =new ArrayList<>();
        tabs.add("Products");

        tabs.add("make Profile");

        ArrayList<Fragment> item_productArrayList=new ArrayList<>();
        item_productArrayList.add(ItemProductiveFamily.newInstance("Products"));
        item_productArrayList.add(InformationProdectiveFamilyFragment.newInstance());


        Log.d("productlist",item_productArrayList.toString());
        ItemProductAdapter itemProductAdapter=new ItemProductAdapter(getActivity(),item_productArrayList);
        binding.ViewPager.setAdapter(itemProductAdapter);
        new TabLayoutMediator(binding.tab, binding.ViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(tabs.get(position));
            }
        }).attach();
        return binding.getRoot();
    }
}