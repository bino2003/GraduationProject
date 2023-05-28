package com.example.graduationproject.ProductiveFamilyProfileActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.graduationproject.databinding.ActivityUpdateProductsBinding;
import com.example.graduationproject.model.Product;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateProducts extends AppCompatActivity {
    final private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ActivityUpdateProductsBinding binding;
    FirebaseAuth firebaseAuth;
    String imageupdate;
    private Uri imageuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();

        String id = intent.getStringExtra("id");
        firebaseFirestore.collection("Products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        if (document.getString("image") != null) {
//                            try {
//                                Uri imageUri = Uri.parse(document.getString("image"));
//
//
//                               InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(imageUri);
//                                Drawable image=   Drawable.createFromStream(inputStream, imageUri.toString());
//                                Glide.with(getApplicationContext()).load().circleCrop().into(binding.uplodeimgupdate);
//
//                            } catch (FileNotFoundException e) {
//                                throw new RuntimeException(e);
//                            }
                            Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.uplodeimgupdate);
                            imageupdate = document.getString("image");

                        }
                        binding.etCategryupdate.setText(document.getString("category"));
                        binding.etDescriptionupdate.setText(document.getString("description"));
                        binding.etNameupdate.setText(document.getString("name"));
                        binding.etPriceupdate.setText(document.getString("price"));

//
                    }
                }

            }

        });

//        Glide.with(getApplicationContext()).load(imageupdate).circleCrop().into(binding.uplodeimgupdate);
//
//        binding.etCategryupdate.setText(oldcategory);
//        binding.etDescriptionupdate.setText(olddescription);
//        binding.etNameupdate.setText(oldname);
//        binding.etPriceupdate.setText(oldprice);

        binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etNameupdate.getText().toString();
                String description = binding.etDescriptionupdate.getText().toString();
                String price = binding.etPriceupdate.getText().toString();
                String category = binding.etCategryupdate.getText().toString();

                if (name.isEmpty() || category.isEmpty() || description.isEmpty() || price.isEmpty()) {
                    Toast.makeText(UpdateProducts.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();

                } else {
                    updateproduct();
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
                            binding.uplodeimgupdate.setImageURI(imageuri);

                        } else {
                            Toast.makeText(UpdateProducts.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
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

    void updateproduct() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        String newname = binding.etNameupdate.getText().toString();
        String newdescription = binding.etDescriptionupdate.getText().toString();
        String newcategory = binding.etCategryupdate.getText().toString();
        String newprice = binding.etPriceupdate.getText().toString();

        firebaseAuth = FirebaseAuth.getInstance();
        Product product = new Product();

        product.setName(newname);
        product.setCategory(newcategory);
        product.setDescription(newdescription);
        product.setPrice(newprice);
        if (imageuri != null) {
            product.setImage(String.valueOf(imageuri));
        } else {
            product.setImage(imageupdate);

        }

        product.setUser(firebaseAuth.getUid());
        firebaseFirestore.collection("Products").document(id).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {


                    Toast.makeText(UpdateProducts.this, "Product Update successfully ", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UpdateProducts.this, "Product Update failed  ", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}