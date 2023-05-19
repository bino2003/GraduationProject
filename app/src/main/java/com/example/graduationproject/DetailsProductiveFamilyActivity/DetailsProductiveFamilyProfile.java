package com.example.graduationproject.DetailsProductiveFamilyActivity;

import android.os.Bundle;


import androidx.fragment.app.Fragment;

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
FirebaseFirestore firebaseFirestore;
FirebaseAuth firebaseAuth;
List<String> rating;
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
        float r=  binding.ratingBar2.getRating();
        firebaseAuth=FirebaseAuth.getInstance();
        binding.ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                if (b){
if (id!=null){
    firebaseFirestore.collection("Productive Family").document(id).update("evaluation", FieldValue.arrayUnion("hhjhjk")).addOnCompleteListener(new OnCompleteListener<Void>() {
        @Override
        public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
            if (task.isSuccessful()){
                Toast.makeText(getActivity(), "rating", Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(getActivity(), task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        }
    });

}
                }
            }
        });


        firebaseFirestore.collection(dbname).document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();

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
                binding.tvPhone.setText(""+documentSnapshot.getLong("phone").intValue());


            }
        });
      return   binding.getRoot();
    }
}