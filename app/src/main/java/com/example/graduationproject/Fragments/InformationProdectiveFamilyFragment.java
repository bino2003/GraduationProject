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
    FragmentInformationProdectiveFamilyBinding binding;
    String description;
    String image;
    String sharedproductCategory;
    String productCategory;
    FragmentInformationProductiveFamily2Binding binding2;

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
    public void onResume() {
        super.onResume();
        refresh();

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

        binding = FragmentInformationProdectiveFamilyBinding.inflate(inflater, container, false);
        binding2 = FragmentInformationProductiveFamily2Binding.inflate(inflater, container, false);
check();
//        binding.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPicker = new Intent();
//                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });

        location=sharedPreferences.getString("sharedlocation","");
        details=sharedPreferences.getString("shareddetails","");
        phone=sharedPreferences.getString("sharedphone","");
        System.out.println(location);
        System.out.println(details);
        System.out.println(phone);
if (location.isEmpty()||phone.isEmpty()||details.isEmpty()){
    createprofile();
}else {
    viewprofile();
}







        return  location.isEmpty()||phone.isEmpty()||details.isEmpty() ? binding.getRoot() : binding2.getRoot();


    }
    public void createprofile(){
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Productcategory=   adapterView.getItemAtPosition(i).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
        binding.btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = binding.etDescription.getText().toString();

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
                int phone1 = sharedPreferences.getInt("phone", 0);
                phone=String.valueOf(phone1);
                lat = sharedPreferences.getString("latitude", null);
                longitude = sharedPreferences.getString("longitude", null);
                category =sharedPreferences.getString("category",null);

                productiveFamily.setCategory(category);
                productiveFamily.setPhone(Integer.parseInt(phone));
                productiveFamily.setName(name);
                productiveFamily.setDetails(description);
                productiveFamily.setLatitude(lat);
                productiveFamily.setLongitude(longitude);
//        Log.d("category_name", category);
                StorageReference riversRef = storageRef.child("images/"+imageuri.getLastPathSegment());
                UploadTask uploadTask = riversRef.putFile(imageuri);


                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "upload failed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "upload success", Toast.LENGTH_SHORT).show();
                        riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()){
                                    image=task.getResult().toString();
                                    productiveFamily.setImage(image);
                                    if (image!=null&&description!=null&&location!=null&&Productcategory!=null){
                                        firebaseFirestore.collection("Productive family").document(firebaseAuth.getCurrentUser().getUid()).set(productiveFamily).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("data ", productiveFamily.toString());

                                                    Toast.makeText(getActivity(), " successfully ", Toast.LENGTH_SHORT).show();
                                                    editor.putString("sharedlocation",location);
                                                    editor.putString("shareddetails",description);
                                                    editor.putString("sharedphone",String.valueOf(phone));
                                                    editor.apply();
                                                    refresh();


                                                    Toast.makeText(getActivity(), " successfully ", Toast.LENGTH_SHORT).show();

                                                    //    startActivity(new Intent(getActivity(), ViewInformationProtectiveFamilyActivity.class));



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


    }
    public void viewprofile(){
        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();

                   String detailsproducivefamile=documentSnapshot.getString("details");
                  String  locationproducivefamile=documentSnapshot.getString("location");
                 String   phoneproducivefamile=""+documentSnapshot.getLong("phone").intValue();

                    binding2.tvDesception.setText(detailsproducivefamile);
                    binding2.tvSet.setText(locationproducivefamile);
                    binding2.tvPhone.setText(phoneproducivefamile);


                }
            }
        });
        binding2.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), UpdateInformationProductiveFamilyActivity.class));
            }
        });

    }
    public void refresh(){
        firebaseAuth= FirebaseAuth.getInstance();

        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();

                    details=documentSnapshot.getString("details");
                    Location=documentSnapshot.getString("location");
                    phone=""+documentSnapshot.getLong("phone").intValue();

                    productCategory =sharedPreferences.getString("sharedproductCategory","");
                    binding2.tvDesception.setText(details);
                    binding2.tvSet.setText(Location);
                    binding2.tvPhone.setText(phone);


                }
            }
        });
    }
    public void check(){
        firebaseAuth= FirebaseAuth.getInstance();

        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot documentSnapshot=task.getResult();

                    details=documentSnapshot.getString("details");
                    Location=documentSnapshot.getString("location");
                    phone=""+documentSnapshot.getLong("phone").intValue();

                 if (details==null&&Location==null){
                     editor.remove("sharedlocation");
                     editor.remove("shareddetails");
                     editor.remove("sharedphone");
                     editor.apply();
                 }



                }
            }
        });
    }


}