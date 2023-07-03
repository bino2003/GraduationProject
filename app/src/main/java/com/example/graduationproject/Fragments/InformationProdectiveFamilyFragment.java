package com.example.graduationproject.Fragments;

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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;


import com.example.graduationproject.ProductiveFamilyProfileActivity.UpdateInformationProductiveFamilyActivity;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.FragmentInformationProdectiveFamilyBinding;
import com.example.graduationproject.databinding.FragmentInformationProductiveFamily2Binding;
import com.example.graduationproject.Model.ProductiveFamily;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;


public class InformationProdectiveFamilyFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    FirebaseFirestore firebaseFirestore;
    StorageReference storageRef= FirebaseStorage.getInstance().getReference();

    FirebaseAuth firebaseAuth;
    private Uri imageuri;
    SharedPreferences sharedPreferences;
    String shareddetails;
    String sharedlocation;
    String sharedphone;
    String category;
    String lat;
    String longitude;
    String Location;
    String location;
    String details;
    String phone;
    String instgram;
    String Twitter;
    SharedPreferences.Editor editor;
    private String mParam1;
    private String mParam2;
    String Productcategory;
    String description;
    String image;
    String sharedproductCategory;

    private static final String ARG_db_id = "idFamily";
    private static final String ARG_db_id_product = "idProduct";

    private String family_id;
    private String product_id;
    public InformationProdectiveFamilyFragment() {
        // Required empty public constructor
    }


    public static InformationProdectiveFamilyFragment newInstance(String family_id,String product_id) {
        InformationProdectiveFamilyFragment fragment = new InformationProdectiveFamilyFragment();
        Bundle args = new Bundle();
        args.putString(ARG_db_id, family_id);
        args.putString(ARG_db_id_product, product_id);
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
            family_id = getArguments().getString(ARG_db_id);
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            product_id = getArguments().getString(ARG_db_id_product);

            sharedPreferences = getActivity().getSharedPreferences("sp", MODE_PRIVATE);
            editor = sharedPreferences.edit();

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
        firebaseAuth=FirebaseAuth.getInstance();

        FragmentInformationProdectiveFamilyBinding binding = FragmentInformationProdectiveFamilyBinding.inflate(inflater, container, false);
        FragmentInformationProductiveFamily2Binding binding2 = FragmentInformationProductiveFamily2Binding.inflate(inflater, container, false);
        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();
                    shareddetails=documentSnapshot.getString("details");
                    sharedlocation=documentSnapshot.getString("location");
 sharedphone= String.valueOf(documentSnapshot.getLong("phone"));
                    sharedproductCategory=documentSnapshot.getString("productCategory");
                    editor.putString("sharedproductCategory",sharedproductCategory);

                    editor.putString("shareddetails",shareddetails);
                    editor.putString("sharedlocation",sharedlocation);
                    editor.putString("sharedphone",sharedphone);
                    editor.apply();
                    details=documentSnapshot.getString("details");
                    Location=documentSnapshot.getString("location");
                    phone=""+documentSnapshot.getLong("phone").intValue();

                    binding2.tvDesception.setText(details);
                    binding2.tvSet.setText(Location);
                    binding2.tvPhone.setText(phone);

                    binding2.tvDesception.setText(details);
                    binding2.tvSet.setText(Location);
    binding2.tvPhone.setText(phone);
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

                startActivity(new Intent(getActivity(), UpdateInformationProductiveFamilyActivity.class));
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
                Twitter=binding.etAccount.getText().toString();
                instgram=binding.etInstigram.getText().toString();

                ProductiveFamily productiveFamily = new ProductiveFamily();
                productiveFamily.setProductCategory(Productcategory);
                productiveFamily.setLocation(location);
                productiveFamily.setInstgram(instgram);
                productiveFamily.setTwitter(Twitter);

                productiveFamily.setId(firebaseAuth.getUid());
                Log.d("images gallary", "onClick: "+imageuri);
                String name = sharedPreferences.getString("name", null);
                int phone = sharedPreferences.getInt("phone", 0);
                lat = sharedPreferences.getString("latitude", null);
                longitude = sharedPreferences.getString("longitude", null);
                category =sharedPreferences.getString("category",null);

                productiveFamily.setCategory(category);
                productiveFamily.setPhone(phone);
                productiveFamily.setName(name);
                productiveFamily.setDetails(description);
                productiveFamily.setLatitude(lat);
                productiveFamily.setLongitude(longitude);
//        Log.d("category_name", category);
                StorageReference riversRef = storageRef.child("images/"+imageuri.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(imageuri);


                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@androidx.annotation.NonNull Exception e) {
                        Toast.makeText(getActivity(), "upload failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "upload success", Toast.LENGTH_SHORT).show();
                        riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@androidx.annotation.NonNull Task<Uri> task) {
                                if (task.isSuccessful()){
                                    productiveFamily.setImage(task.getResult().toString());
                                    if (image!=null&&description!=null&&location!=null&&Productcategory!=null&&instgram!=null&&Twitter!=null){
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
                            }
                        });



                    }
                });




            }
        });


        String location=sharedPreferences.getString("sharedlocation","");
        String details=sharedPreferences.getString("shareddetails","");
        String phone=sharedPreferences.getString("sharedphone","");
        String productCategory =sharedPreferences.getString("sharedproductCategory","");

        Toast.makeText(getActivity(), details, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), location, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), phone, Toast.LENGTH_SHORT).show();

        return  location.isEmpty()||productCategory.isEmpty()||phone.isEmpty()||details.isEmpty() ? binding.getRoot() : binding2.getRoot();


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
                    Location=documentSnapshot.getString("location");
                    phone=""+documentSnapshot.getLong("phone").intValue();

                    binding2.tvDesception.setText(details);
                    binding2.tvSet.setText(Location);
                    binding2.tvPhone.setText(phone);

                }
            }
        });
    }

}