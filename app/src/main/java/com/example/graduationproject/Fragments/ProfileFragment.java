package com.example.graduationproject.Fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;


import com.example.graduationproject.Adapters.ItemProductAdapter;
import com.example.graduationproject.databinding.ActivityUserProfileBinding;
import com.example.graduationproject.databinding.ActivityUsersProfileBinding;
import com.example.graduationproject.databinding.FragmentProfile2Binding;
import com.example.graduationproject.Model.ProductiveFamily;
import com.example.graduationproject.Model.users;
import com.example.graduationproject.databinding.FragmentUserProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<String> tabs = new ArrayList<>();
    SharedPreferences sharedPreferences;
    boolean isuser;
    SharedPreferences.Editor editor;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public  static  String user_id;

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

            sharedPreferences = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
            editor = sharedPreferences.edit();


        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ActivityUserProfileBinding bindinguser = ActivityUserProfileBinding.inflate(inflater, container, false);

        FragmentProfile2Binding binding = FragmentProfile2Binding.inflate(inflater, container, false);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user_id=firebaseAuth.getUid();
        Log.d("user", user_id);
        firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                    boolean isFound=false;
                    for (int i = 0; i < productiveFamilyList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();
//                        if(isFound)
//                            continue;
                        if (id.equals(firebaseAuth.getUid())) {
                            isuser = false;
                           // isFound=true;
                            break;
                        }
                    }
                }
            }
        });
        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<users> usersList = task.getResult().toObjects(users.class);
                    for (int i = 0; i < usersList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();
                        if (id.equals(firebaseAuth.getUid())) {
                            isuser = true;
                            Toast.makeText(getActivity(), "user", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            }
        });

        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                if (documentSnapshot.get("name") != null) {
                    binding.name.setText(documentSnapshot.getString("name"));

                }
                if (documentSnapshot.get("image") != null) {
                    Glide.with(getActivity()).load(Uri.parse(documentSnapshot.getString("image"))).circleCrop().into(binding.imageView3);

                }


            }
        });

        ArrayList<String> tabs = new ArrayList<>();
        tabs.add("Products");

        tabs.add("make Profile");

        ArrayList<Fragment> item_productArrayList = new ArrayList<>();
        item_productArrayList.add(ItemProductiveFamily.newInstance("Products","",""));
        item_productArrayList.add(InformationProdectiveFamilyFragment.newInstance("",""));


        Log.d("productlist", item_productArrayList.toString());
        ItemProductAdapter itemProductAdapter = new ItemProductAdapter(getActivity(), item_productArrayList);
        binding.ViewPager.setAdapter(itemProductAdapter);
        binding.ViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        new TabLayoutMediator(binding.tab, binding.ViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(TabLayout.@NonNull Tab tab, int position) {
                tab.setText(tabs.get(position));
            }
        }).attach();
        return binding.getRoot();
    }

}