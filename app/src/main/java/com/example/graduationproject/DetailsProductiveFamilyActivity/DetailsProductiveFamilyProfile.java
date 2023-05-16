package com.example.graduationproject.DetailsProductiveFamilyActivity;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.graduationproject.databinding.FragmentDetailsProductiveFamilyProfileBinding;
import com.example.graduationproject.model.ProductiveFamily;

import com.google.android.gms.common.moduleinstall.internal.ApiFeatureRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class DetailsProductiveFamilyProfile extends Fragment {

    final Map<String, Object> ratting = new HashMap<>();

    private static final String ARG_db_name = "dbName3";
    private static final String ARG_ID_ProductiveFamily = "id2";
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
    // TODO: Rename and change types of parameters
    private String dbname;
    private String id;

    public DetailsProductiveFamilyProfile() {
        // Required empty public constructor
    }


    public static DetailsProductiveFamilyProfile newInstance(String dbname, String id) {
        DetailsProductiveFamilyProfile fragment = new DetailsProductiveFamilyProfile();
        Bundle args = new Bundle();
        args.putString(ARG_db_name, dbname);
        args.putString(ARG_ID_ProductiveFamily, id);
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
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();

        firebaseFirestore.collection(dbname).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
              float r=  binding.ratingBar2.getRating();
                String.valueOf(r);
                String[] dataArray = {String.valueOf(r)}; // Replace with your desired data

                DocumentReference docRef = firebaseFirestore.collection("Productive Family").document(id);

// Update the document with the array data
                Task<DocumentSnapshot> future = docRef.get();
                if (future.isSuccessful()){
                    DocumentSnapshot document = future.getResult();

                    if (document.exists()) {
                        // Get the existing array field
                        List<String> rating = (List<String>) document.get("evaluation");

                        // Add the new data to the existing array
                        List<String> newArray = new ArrayList<>();
                        newArray.addAll(Arrays.asList(dataArray));

                        // Update the document with the new array
                        docRef.update("evaluation", newArray);
                        System.out.println("Array data added successfully!");
                    } else {
                        System.out.println("Document does not exist.");
                    }


                }

                binding.tvDesception.setText(documentSnapshot.getString("details"));
                binding.tvSet.setText(documentSnapshot.getString("location"));
                binding.tvPhone.setText(""+documentSnapshot.getLong("phone").intValue());


            }
        });
      return   binding.getRoot();
    }
}