package com.example.graduationproject.DetailsProductiveFamilyActivity;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.graduationproject.databinding.FragmentDetailsProductiveFamilyProfileBinding;
import com.example.graduationproject.model.ProductiveFamily;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailsProductiveFamilyProfile extends Fragment {

    private static final String ARG_db_name = "dbName3";
    private static final String ARG_ID_ProductiveFamily = "id2";
    final Map<String, Object> ratting = new HashMap<>();
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    // TODO: Rename and change types of parameters
    private String dbname;
    private String id;

    public DetailsProductiveFamilyProfile() {
        // Required empty public constructor
    }


    public static DetailsProductiveFamilyProfile newInstance(String dbname) {
        DetailsProductiveFamilyProfile fragment = new DetailsProductiveFamilyProfile();
        Bundle args = new Bundle();
        args.putString(ARG_db_name, dbname);
        args.putString(ARG_ID_ProductiveFamily, fragment.id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbname = getArguments().getString(ARG_db_name);
            id = getArguments().getString(ARG_ID_ProductiveFamily);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentDetailsProductiveFamilyProfileBinding binding = FragmentDetailsProductiveFamilyProfileBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore.collection(dbname).document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot = task.getResult();
                Log.d("uid ", firebaseAuth.getCurrentUser().getUid());
                binding.tvDesception.setText(documentSnapshot.getString("details"));
                binding.tvSet.setText(documentSnapshot.getString("location"));
                Log.d("phone", documentSnapshot.getLong("phone")+"");
                binding.tvPhone.setText("" + documentSnapshot.getLong("phone"));


            }
        });
        return binding.getRoot();
    }
}
