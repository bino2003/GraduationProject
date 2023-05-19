package com.example.graduationproject.ProductiveFamilyProfileActivity;

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
import android.widget.AdapterView;
import android.widget.Toast;


import com.example.graduationproject.databinding.FragmentInformationProdectiveFamilyBinding;
import com.example.graduationproject.databinding.Fragmentinformationproductivefamily2Binding;
import com.example.graduationproject.model.ProductiveFamily;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;


public class InformationProdectiveFamilyFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private Uri imageuri;
    SharedPreferences sharedPreferences;
    String shareddetails;
    String sharedlocation;
    String sharedphone;
    String category;
    String latlong;
    String location;
SharedPreferences.Editor editor;
    private String mParam1;
    private String mParam2;
    String Productcategory;
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
            editor = sharedPreferences.edit();

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
firebaseAuth=FirebaseAuth.getInstance();

        FragmentInformationProdectiveFamilyBinding binding = FragmentInformationProdectiveFamilyBinding.inflate(inflater, container, false);
        Fragmentinformationproductivefamily2Binding binding2 = Fragmentinformationproductivefamily2Binding.inflate(inflater, container, false);
firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
    @Override
    public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
   if (task.isSuccessful()){
       DocumentSnapshot documentSnapshot=task.getResult();
 shareddetails=documentSnapshot.getString("details");
 sharedlocation=documentSnapshot.getString("location");
 sharedphone= String.valueOf(documentSnapshot.getLong("phone").intValue());
editor.putString("shareddetails",shareddetails);
editor.putString("sharedlocation",sharedlocation);
editor.putString("sharedphone",sharedphone);
editor.apply();


       binding2.tvDesception.setText(documentSnapshot.getString("details"));
       binding2.tvSet.setText(documentSnapshot.getString("location"));
       binding2.tvPhone.setText(""+documentSnapshot.getLong("phone").intValue());
   }
    }
});
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageuri = data.getData();
                            binding.imageView.setImageURI(imageuri);

                        } else {
                            Toast.makeText(getActivity(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
     binding2.update.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

             startActivity(new Intent(getActivity(),UpdateInformationProductiveFamilyActivity.class));
         }
     });

       binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                image = String.valueOf(binding.imageView.getDrawable());
                description = binding.etDescription.getText().toString();

                binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Productcategory=   adapterView.getItemAtPosition(i).toString();

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                firebaseAuth=FirebaseAuth.getInstance();
                location=binding.etLocation.getText().toString();
                ProductiveFamily productiveFamily = new ProductiveFamily();
                productiveFamily.setProductCategory(Productcategory);
                productiveFamily.setLocation(location);
                productiveFamily.setId(firebaseAuth.getUid());
                productiveFamily.setImage(String.valueOf(imageuri));
                Log.d("images gallary", "onClick: "+imageuri);
                String name = sharedPreferences.getString("name", null);
                int phone = sharedPreferences.getInt("phone", 0);
                latlong = sharedPreferences.getString("latlong", null);
                category =sharedPreferences.getString("category",null);

                productiveFamily.setCategory(category);
                productiveFamily.setPhone(phone);
                productiveFamily.setName(name);
                productiveFamily.setDetails(description);
                productiveFamily.setLatlong(latlong);
//        Log.d("category_name", category);
                if (image!=null&&description!=null&&location!=null&&Productcategory!=null){
                    firebaseFirestore.collection("Productive family").document(firebaseAuth.getCurrentUser().getUid()).set(productiveFamily).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("data ", productiveFamily.toString());

                                Toast.makeText(getActivity(), " successfully ", Toast.LENGTH_SHORT).show();
                                //    startActivity(new Intent(getActivity(), ViewInformationProtectiveFamilyActivity.class));
editor.putString("productcategory",Productcategory);
editor.apply();


                            } else {
                                Toast.makeText(getActivity(), "not successfully  ", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }else {
                    Toast.makeText(getActivity(), "All fields must be filled in", Toast.LENGTH_SHORT).show();
                }




            }
        });


String location=sharedPreferences.getString("sharedlocation","");
        String details=sharedPreferences.getString("shareddetails","");
        String phone=sharedPreferences.getString("sharedphone","");
        Toast.makeText(getActivity(), details, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), location, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), phone, Toast.LENGTH_SHORT).show();

return  location.isEmpty()||phone.isEmpty()||details.isEmpty() ? binding.getRoot() : binding2.getRoot();


    }
}