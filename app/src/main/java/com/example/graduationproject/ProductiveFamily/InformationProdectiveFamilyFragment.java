package com.example.graduationproject.ProductiveFamily;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graduationproject.R;
import com.example.graduationproject.ViewInformationProtectiveFamilyActivity;
import com.example.graduationproject.databinding.FragmentInformationProdectiveFamilyBinding;
import com.example.graduationproject.model.ProductiveFamily;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;


public class InformationProdectiveFamilyFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private Uri imageuri;
    SharedPreferences sharedPreferences;

    private String mParam1;
    private String mParam2;
    String category;
    String description;
    String image;
    public InformationProdectiveFamilyFragment() {
        // Required empty public constructor
    }


    public static InformationProdectiveFamilyFragment newInstance() {
        InformationProdectiveFamilyFragment fragment = new InformationProdectiveFamilyFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

            sharedPreferences = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        FragmentInformationProdectiveFamilyBinding binding = FragmentInformationProdectiveFamilyBinding.inflate(inflater, container, false);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageuri = data.getData();
//                            binding.image.setImageURI(imageuri);

                        } else {
                            Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
//        binding.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPicker = new Intent();
//                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });


//        binding.save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                image = String.valueOf(binding.image.getDrawable());
//
//                description = binding.etDescription.getText().toString();
//                category = binding.category.getText().toString();
//                ProductiveFamily productiveFamily = new ProductiveFamily();
//                productiveFamily.setCategory(category);
//                productiveFamily.setImage(String.valueOf(imageuri));
//                Log.d("images gallary", "onClick: "+imageuri);
//                String name = sharedPreferences.getString("name", null);
//                int phone = sharedPreferences.getInt("phone", 0);
////        String latlong = sharedPreferences.getString("latlong", null);
//                productiveFamily.setPhone(phone);
//                productiveFamily.setName(name);
//                productiveFamily.setDetails(description);
////        productiveFamily.setLatlong(latlong);
////        Log.d("category_name", category);
//                firebaseFirestore.collection("Productive family").document(firebaseAuth.getCurrentUser().getUid()).set(productiveFamily).addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful() && binding.category.getText() != null && binding.etDescription.getText() != null) {
//                            Log.d("data ", productiveFamily.toString());
//
//                            Toast.makeText(getActivity(), " successfully ", Toast.LENGTH_SHORT).show();
//                            //    startActivity(new Intent(getActivity(), ViewInformationProtectiveFamilyActivity.class));
//
//                        } else {
//                            Toast.makeText(getActivity(), "not successfully  ", Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//                if (binding.category.getText().toString()!=null && binding.etDescription.getText().toString()!=null ) {
////                    Log.d("data ", productiveFamily.toString());
//                    Toast.makeText(getActivity(), " not success ", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getActivity(), ViewInformationProtectiveFamilyActivity.class));
//                }
//
//
//
//            }
//        });
//        binding.btnViewData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getActivity(),ViewInformationProtectiveFamilyActivity.class);
//                startActivity(intent);
//
//            }
//        });


        return binding.getRoot();


    }
}