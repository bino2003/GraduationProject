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
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.graduationproject.databinding.ActivityAddProductsBinding;
import com.example.graduationproject.model.Product;

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
    StorageReference firebaseStorage = FirebaseStorage.getInstance().getReference();
    private Uri imageuri;

    final private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    final private StorageReference storageRef = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etNameadd.getText().toString();
                String description = binding.etDescriptionadd.getText().toString();
                String price = binding.etPriceadd.getText().toString();
                String category = binding.etCategryadd.getText().toString();

                if (name.isEmpty() || category.isEmpty() || description.isEmpty() || price.isEmpty()) {
                    Toast.makeText(AddProducts.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();

                } else {
                    addproduct();

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
                            binding.uplodeimg.setImageURI(imageuri);

                        } else {
                            Toast.makeText(AddProducts.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        binding.uplodeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

    }

    void addproduct() {

        String name = binding.etNameadd.getText().toString();
        String description = binding.etDescriptionadd.getText().toString();
        String category = binding.etCategryadd.getText().toString();
        String price = binding.etPriceadd.getText().toString();

//        firebaseAuth = FirebaseAuth.getInstance();
        Product product = new Product();

        product.setName(name);
        product.setCategory(category);
        product.setDescription(description);
        product.setPrice(price);

//        product.setUser(firebaseUser.getUid());
        StorageReference riversRef = storageRef.child("images/"+imageuri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(imageuri);

// Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(e ->{
                    Log.d("muath", "addproduct: fail"+e.getLocalizedMessage());
                })
                .addOnSuccessListener(taskSnapshot -> {
                    Toast.makeText(AddProducts.this, "upload success", Toast.LENGTH_SHORT).show();
                    Log.d("muath", "addproduct: success"+taskSnapshot.getStorage().getName());

                } );

// Initialize FirebaseApp
//        FirebaseOptions options = null;
//        try {
//            options = new FirebaseOptions.Builder()
//                    .setCredentials(GoogleCredentials.fromStream(new FileInputStream("path/to/serviceAccountKey.json")))
//                    .setStorageBucket("gs://graduationproject-ce496.appspot.com")
//                    .build();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        FirebaseApp.initializeApp(options);
//
//
//
//
//// Create a Storage instance
//        Storage storage = StorageOptions.getDefaultInstance().getService();
//
//// Specify the image URI
//        String imageUri = imageuri+"";
//
//// Specify the bucket name and desired file name
//        String bucketName = "gs://graduationproject-ce496.appspot.com";
//        String fileName = "image.jpg";
//
//// Create a BlobId using the bucket name and desired file name
//        BlobId blobId = BlobId.of(bucketName, fileName);
//
//// Create a BlobInfo object with the image URI as the content
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("text/plain").build();
//        Blob blob = storage.create(blobInfo, imageUri.getBytes());
//
//        System.out.println("Image URI stored successfully in Firebase Storage.");


//        firebaseFirestore.collection("Products").document().set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//
//
//                    Toast.makeText(AddProducts.this, "Product added successfully ", Toast.LENGTH_SHORT).show();
//                    finish();
//                } else {
//                    Toast.makeText(AddProducts.this, "Product addition failed  ", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

    }
}