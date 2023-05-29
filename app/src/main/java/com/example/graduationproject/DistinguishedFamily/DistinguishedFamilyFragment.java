package com.example.graduationproject.DistinguishedFamily;

import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graduationproject.Adapters.DistinguishedFamilyAdapter;
import com.example.graduationproject.DetailsProductiveFamilyActivity.DetailsProductiveFamily;
import com.example.graduationproject.Interface.OnClickProductiveFamily;
import com.example.graduationproject.databinding.FragmentDistinguishedFamilyBinding;
import com.example.graduationproject.Model.ProductiveFamily;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class DistinguishedFamilyFragment extends Fragment {

    DistinguishedFamilyAdapter distinguishedFamilyAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DistinguishedFamilyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DistinguishedFamilyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DistinguishedFamilyFragment newInstance(String param1, String param2) {
        DistinguishedFamilyFragment fragment = new DistinguishedFamilyFragment();
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
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        FragmentDistinguishedFamilyBinding binding=FragmentDistinguishedFamilyBinding.inflate(inflater,container,false);
firebaseFirestore.collection("Productive family").orderBy("rating", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
    @Override
    public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()){
            binding.progressBar2.setVisibility(View.GONE);
            ArrayList<ProductiveFamily>productiveFamilyList= (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);
            distinguishedFamilyAdapter=new DistinguishedFamilyAdapter(getActivity(), new OnClickProductiveFamily() {
                @Override
                public void onclickproductiveFamily(ProductiveFamily productiveFamily) {
                    Intent intent=new Intent(getActivity(), DetailsProductiveFamily.class);

                    intent.putExtra("idproductivefamily",productiveFamily.getId());
                    startActivity(intent);
                }
            },productiveFamilyList);
            binding.recyclerView.setAdapter(distinguishedFamilyAdapter);

            binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
            distinguishedFamilyAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(getActivity(), task.getException().getMessage()+"", Toast.LENGTH_SHORT).show();
        }

    }
});

        return binding.getRoot();
    }
}

