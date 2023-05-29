package com.example.graduationproject.ProductiveFamilyProfileActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.graduationproject.databinding.ActivityAddProductsBinding;
import com.example.graduationproject.model.Product;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

public class AddProducts extends AppCompatActivity {
    ActivityAddProductsBinding binding;
FirebaseOptions firebaseOptions;
FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    StorageReference storageRef=FirebaseStorage.getInstance().getReference();
    private Uri imageuri;

    final private FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
    final  private StorageReference storageReference= FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


firebaseAuth=FirebaseAuth.getInstance();


        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String name=   binding.etNameadd.getText().toString();
             String description=binding.etDescriptionadd.getText().toString();
             String price =binding.etPriceadd.getText().toString();
             String category =binding.etCategryadd.getText().toString();

                if (name.isEmpty()||category.isEmpty()||description.isEmpty()||price.isEmpty()){
                    Toast.makeText(AddProducts.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();

                }else {
                   addproduct();

                }

            }
        });
        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode()== Activity.RESULT_OK){
                            Intent data=result.getData();
                            imageuri=data.getData();
                            binding.uplodeimg.setImageURI(imageuri);

                        }else {
                            Toast.makeText(AddProducts.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        binding.uplodeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker=new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

    }
    void addproduct(){

String name=binding.etNameadd.getText().toString();
        String description=binding.etDescriptionadd.getText().toString();
        String category=binding.etCategryadd.getText().toString();
        String price=binding.etPriceadd.getText().toString();

        Product product=new Product();

        product.setName(name);
        product.setCategory(category);
        product.setDescription(description);
        product.setPrice(price);

        product.setUser(firebaseAuth.getUid());
        StorageReference riversRef = storageRef.child("images/"+imageuri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(imageuri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@androidx.annotation.NonNull Exception e) {
                Toast.makeText(AddProducts.this, "upload failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AddProducts.this, "upload success", Toast.LENGTH_SHORT).show();
                riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@androidx.annotation.NonNull Task<Uri> task) {
                        if (task.isSuccessful()){
                            product.setImage(task.getResult().toString());
                            firebaseFirestore.collection("Products").document().set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){


                                        Toast.makeText(AddProducts.this, "Product added successfully ", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(AddProducts.this, "Product addition failed  ", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }
                    }
                });

            }
        });





    }
}