package com.example.graduationproject.DetailsProductiveFamilyActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;


import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.graduationproject.databinding.FragmentDetailsProductiveFamilyProfileBinding;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailsProductiveFamilyProfile extends Fragment {

    final Map<String, Object> ratting = new HashMap<>();


    private static final String ARG_db_name = "dbName3";
    private static final String ARG_ID_ProductiveFamily = "id2";
    private static final String ARG_ID_Product_Id = "producr_id";
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    List<String> rating;
    // TODO: Rename and change types of parameters
    private String dbname;
    private String id;
    private String product_id;

    public DetailsProductiveFamilyProfile() {
        // Required empty public constructor
    }


    public static DetailsProductiveFamilyProfile newInstance(String dbname, String id,String product_id) {
        DetailsProductiveFamilyProfile fragment = new DetailsProductiveFamilyProfile();
        Bundle args = new Bundle();
        args.putString(ARG_db_name, dbname);
        args.putString(ARG_ID_ProductiveFamily, id);
        args.putString(ARG_ID_Product_Id, product_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbname = getArguments().getString(ARG_db_name);
            id = getArguments().getString(ARG_ID_ProductiveFamily);
            product_id = getArguments().getString(ARG_ID_Product_Id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (id!=null){
            Log.d("id_", id);
        }
        Log.d("d_", id);
        FragmentDetailsProductiveFamilyProfileBinding binding = FragmentDetailsProductiveFamilyProfileBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();

        float r = binding.ratingBar2.getRating();
        firebaseAuth = FirebaseAuth.getInstance();
        binding.watsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "was", Toast.LENGTH_SHORT).show();
                String phoneNumber = "0598681854";

                // Create a URI with the phone number
                Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber);

                // Create an Intent with the ACTION_VIEW action and the URI
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                // Set the package name of WhatsApp to ensure only WhatsApp handles this intent
                intent.setPackage("com.whatsapp");

                // Verify that there's a WhatsApp application available to handle this intent
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    // WhatsApp is not installed on the device or no activity can handle the intent
                    // You can show an error message or redirect the user to the Play Store to install WhatsApp
                }
            }
        });
        binding.ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b) {
                    if (id != null) {
                        firebaseFirestore.collection("Productive family").document(id).update("evaluation", FieldValue.arrayUnion(String.valueOf(v))).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "rating", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(), task.getException().getMessage() + "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                }
            }
        });
        if (id!=null){
            firebaseFirestore.collection(dbname).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();

//                  firebaseFirestore.collection("Productive Family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                      @Override
//                      public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
//                          DocumentSnapshot documentSnapshot1=task.getResult();
//                          documentSnapshot1.
////                   ProductiveFamily productiveFamily=       task.getResult().toObject(ProductiveFamily.class);
////                   rating.add(String.valueOf(r));
////                productiveFamily.setEvaluation(rating);
//
//                      }
//                  });


                    binding.tvDesception.setText(documentSnapshot.getString("details"));
                    binding.tvSet.setText(documentSnapshot.getString("location"));
                    binding.tvPhone.setText("" + documentSnapshot.getLong("phone").intValue());


                }
            });
        }



        return binding.getRoot();
    }
}