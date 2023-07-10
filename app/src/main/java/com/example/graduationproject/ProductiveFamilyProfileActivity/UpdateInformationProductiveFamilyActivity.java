package com.example.graduationproject.ProductiveFamilyProfileActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.graduationproject.Model.users;
import com.example.graduationproject.databinding.ActivityUpdateInformationProductiveFamilyBinding;

import com.example.graduationproject.Model.ProductiveFamily;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class UpdateInformationProductiveFamilyActivity extends AppCompatActivity {

    ActivityUpdateInformationProductiveFamilyBinding binding;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FirebaseAuth firebaseAuth;
    private Uri imageuri;

    FirebaseFirestore firebaseFirestore;
    StorageReference storageRef= FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateInformationProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.backe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sharedPreferences = getApplicationContext().getSharedPreferences("sp", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageuri = data.getData();
                            binding.imageView.setImageURI(imageuri);

                        } else {
                            Toast.makeText(getBaseContext(), "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        firebaseFirestore.collection("Productive family").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG 1", "DocumentSnapshot data: " + document.getData());
                        Log.d("name document", document.getString("name") + "category " + document.getString("category"));
//                        binding.imageView2.setImageURI(Uri.parse(document.getString("image")));
                        Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.imageView);
                        Log.d("image", document.getString("image"));
//                        Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image")).into(binding.imageView2);
                        binding.etName.setText(document.getString("name"));
                        binding.etLocationupdate.setText(document.getString("location"));
                        binding.etDescriptionupdate.setText(document.getString("details"));
                        binding.etPhoneupdate.setText(document.getString("phone"));
//                        double phone=document.getDouble("phone");
//                        int phone
//                        binding.tvPhone.setText((Integer));
                        String oldimage=document.getString("image");
                        editor.putString("image",oldimage);
                        editor.apply();
                    } else {
                        Log.d("TAG 2", "No such document");
                    }
                } else {
                    Log.d("TAG 3", "get failed with ", task.getException());
                }


            }

        });
        firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                    for (int i = 0; i < productiveFamilyList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();
                        if (id.equals(firebaseAuth.getUid())) {

                        }


                    }
                }
            }
        });
        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<users> usersList = task.getResult().toObjects(users.class);
                    for (int i = 0; i < usersList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();
                        if (id.equals(firebaseAuth.getUid())) {

                        }


                    }
                }
            }
        });
        binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth=FirebaseAuth.getInstance();


                String name = binding.etName.getText().toString();

                String phone = binding.etPhoneupdate.getText().toString();
                String location = binding.etLocationupdate.getText().toString();
                String descrption = binding.etDescriptionupdate.getText().toString();
                String productCategory=sharedPreferences.getString("productcategory","");
                Toast.makeText(UpdateInformationProductiveFamilyActivity.this, productCategory, Toast.LENGTH_SHORT).show();
               String longitude = sharedPreferences.getString("longitude", null);
                String latitude = sharedPreferences.getString("latitude", null);

                String  category =sharedPreferences.getString("category",null);


                String image = String.valueOf(imageuri);
                ProductiveFamily productiveFamily = new ProductiveFamily();
                productiveFamily.setName(name);
                productiveFamily.setProductCategory(productCategory);
                productiveFamily.setId(firebaseAuth.getUid());
                productiveFamily.setLongitude(longitude);
                productiveFamily.setLatitude(latitude);
                productiveFamily.setCategory(category);
                productiveFamily.setLocation(location);


                productiveFamily.setDetails(descrption);
                if (imageuri==null){
                    String oldimage=sharedPreferences.getString("image","");
                    productiveFamily.setImage(oldimage);
                    firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                       DocumentSnapshot documentSnapshot=task.getResult();
                       documentSnapshot.getReference().update("image",oldimage);
                       documentSnapshot.getReference().update("name",name);
                       documentSnapshot.getReference().update("productCategory",productCategory);
                       documentSnapshot.getReference().update("id",firebaseAuth.getUid());
                       documentSnapshot.getReference().update("longitude",longitude);
                       documentSnapshot.getReference().update("latitude",latitude);
                       documentSnapshot.getReference().update("category",category);
                       documentSnapshot.getReference().update("location",location);
                       documentSnapshot.getReference().update("phone",phone);
                       documentSnapshot.getReference().update("details",descrption);
                            Toast.makeText(UpdateInformationProductiveFamilyActivity.this, "update successfully", Toast.LENGTH_SHORT).show();

                        }
                    });
//                    firebaseFirestore.collection("Productive family").document(firebaseAuth.getCurrentUser().getUid()).set(productiveFamily).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if (task.isSuccessful()) {
//                                Log.d("data ", productiveFamily.toString());
//
//                                Toast.makeText(getBaseContext(), " successfully ", Toast.LENGTH_SHORT).show();
//finish();                            } else {
//                                Toast.makeText(getBaseContext(), "not successfully  ", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });

                }else {
                    StorageReference riversRef = storageRef.child("images/"+imageuri.getLastPathSegment());
                    UploadTask uploadTask = riversRef.putFile(imageuri);


                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UpdateInformationProductiveFamilyActivity.this, "upload failed", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(UpdateInformationProductiveFamilyActivity.this, "upload success", Toast.LENGTH_SHORT).show();
                            riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {
                                    if (task.isSuccessful()){
                                        productiveFamily.setImage(task.getResult().toString());
                                        String image1=task.getResult().toString();
                                        firebaseFirestore.collection("Productive family").document(firebaseAuth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@androidx.annotation.NonNull Task<DocumentSnapshot> task) {
                                                DocumentSnapshot documentSnapshot=task.getResult();
                                                documentSnapshot.getReference().update("image",image1);
                                                documentSnapshot.getReference().update("name",name);
                                                documentSnapshot.getReference().update("productCategory",productCategory);
                                                documentSnapshot.getReference().update("id",firebaseAuth.getUid());
                                                documentSnapshot.getReference().update("longitude",longitude);
                                                documentSnapshot.getReference().update("latitude",latitude);
                                                documentSnapshot.getReference().update("category",category);
                                                documentSnapshot.getReference().update("location",location);
                                                documentSnapshot.getReference().update("phone",Integer.parseInt(phone));
                                                documentSnapshot.getReference().update("details",descrption);
                                                Toast.makeText(UpdateInformationProductiveFamilyActivity.this, "update successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        });


                                    }
                                }
                            });

                        }
                    });
                }





            }
        });
        binding.uplodeimgupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
    }
}