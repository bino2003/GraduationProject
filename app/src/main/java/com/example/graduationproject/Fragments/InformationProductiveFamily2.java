package com.example.graduationproject.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.graduationproject.Model.ProductiveFamily;
import com.example.graduationproject.ProductiveFamilyProfileActivity.UpdateInformationProductiveFamilyActivity;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.FragmentInformationProdectiveFamilyBinding;
import com.example.graduationproject.databinding.FragmentInformationProductiveFamily2Binding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InformationProductiveFamily2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InformationProductiveFamily2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
    private static final String ARG_PARAM2 = "param2";
    String details;
    String location;
    String phone;

    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InformationProductiveFamily2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InformationProductiveFamily2.
     */
    // TODO: Rename and change types and number of parameters
    public static InformationProductiveFamily2 newInstance(String param1, String param2) {
        InformationProductiveFamily2 fragment = new InformationProductiveFamily2();
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
    public void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        firebaseAuth= FirebaseAuth.getInstance();

        FragmentInformationProductiveFamily2Binding binding2 = FragmentInformationProductiveFamily2Binding.inflate(inflater, container, false);
        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();



                }
            }
        });

        binding2.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), UpdateInformationProductiveFamilyActivity.class));
            }
        });



        return  binding2.getRoot();

    }
    public void refresh(){
        firebaseAuth= FirebaseAuth.getInstance();
        FragmentInformationProductiveFamily2Binding binding2 = FragmentInformationProductiveFamily2Binding.inflate(getLayoutInflater());

        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();

details=documentSnapshot.getString("details");
location=documentSnapshot.getString("location");
phone=""+documentSnapshot.getLong("phone").intValue();

                    binding2.tvDesception.setText(details);
                    binding2.tvSet.setText(location);
                    binding2.tvPhone.setText(phone);

                }
            }
        });
    }
}