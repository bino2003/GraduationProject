package com.example.graduationproject.ProductiveFamilyProfileActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.graduationproject.Adapters.ViewPagerAdapter;

import com.example.graduationproject.ItemModel;
import com.example.graduationproject.Model.Product2;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.ActivityAddProductsBinding;
import com.example.graduationproject.Model.Product;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public class AddProducts extends AppCompatActivity {

    ActivityAddProductsBinding binding;

    AppCompatButton Uploadbutton;
    EditText ItemName, ItemDesc,price,category;
    RelativeLayout PickImagebutton;
    ViewPager viewPager;
    Uri ImageUri;
    ArrayList<Uri> ChooseImageList;
    ArrayList<String> UrlsList;
    FirebaseFirestore firestore;
    StorageReference storagereference;
    FirebaseAuth auth;
    FirebaseStorage mStorage;
    String productive_family;
    Product2 model;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        PickImagebutton = findViewById(R.id.ChooseImage);
        viewPager = findViewById(R.id.viewPager);
        ItemDesc = findViewById(R.id.et_descriptionadd);
        ItemName = findViewById(R.id.et_nameadd);
        price = findViewById(R.id.et_priceadd);
        auth=FirebaseAuth.getInstance();
        category = findViewById(R.id.et_categryadd);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Please Wait While Uploading Your data...");


        // firebase Instance
        firestore = FirebaseFirestore.getInstance();
        mStorage = FirebaseStorage.getInstance();
        storagereference = mStorage.getReference();
        Uploadbutton = findViewById(R.id.UploadBtn);

        ChooseImageList = new ArrayList<>();
        UrlsList = new ArrayList<>();

        PickImagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckPermission();

            }
        });
        Uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadIMages();
            }
        });
    }










//        binding.addbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = binding.etNameadd.getText().toString();
//                String description = binding.etDescriptionadd.getText().toString();
//                String price = binding.etPriceadd.getText().toString();
//                String category = binding.etCategryadd.getText().toString();
//
//                if (name.isEmpty() || category.isEmpty() || description.isEmpty() || price.isEmpty()) {
//                    Toast.makeText(AddProducts.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    addproduct();
//
//                }
//
//            }
//        });
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            ArrayList<Bitmap> bitmapList=new ArrayList<>();
//                            Intent data = result.getData();
//                            imageuri = data.getData();
////                            binding.uplodeimg.setImageURI(imageuri);
//                            if (data.getClipData() != null) {
//                                // Multiple images selected
//                                int count = data.getClipData().getItemCount();
//                                for (int i = 0; i < count; i++) {
//                                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
//                                    // Upload the image to Firebase Firestore
//                                    uploadImageToFirestore(imageUri);
//                                }
//                            }else if (data.getData() != null) {
//                                // Single image selected
//                                Uri imageUri = data.getData();
//                                // Upload the image to Firebase Firestore
//                                uploadImageToFirestore(imageUri);
//                            }
//                        }
//                    }
//                }
//        );
//        binding.btnImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPicker = new Intent();
//                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });





//
//    void addproduct() {
//
//        String name = binding.etNameadd.getText().toString();
//        String description = binding.etDescriptionadd.getText().toString();
//        String category = binding.etCategryadd.getText().toString();
//        String price = binding.etPriceadd.getText().toString();
//
//        Product product = new Product();
//        product.setName(name);
//        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                DocumentSnapshot documentSnapshot = task.getResult();
//                if (documentSnapshot.get("name") != null) {
//                    product.setProductive_family(documentSnapshot.getString("name"));
//
//                }
//            }
//        });
//        product.setCategory(category);
//        product.setDescription(description);
//        product.setPrice(price);
//
//        product.setUser(firebaseAuth.getUid());
//        StorageReference riversRef = storageRef.child("images/" + imageuri.getLastPathSegment());
//        UploadTask uploadTask = riversRef.putFile(imageuri);
//
//
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(AddProducts.this, "upload failed", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Toast.makeText(AddProducts.this, "upload success", Toast.LENGTH_SHORT).show();
//                riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Uri> task) {
//                        if (task.isSuccessful()) {
//                            product.setImage(task.getResult().toString());
//                            firebaseFirestore.collection("Product2").document().set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    if (task.isSuccessful()) {
//                                        firebaseFirestore.collection("Product2").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                                if (task.isSuccessful()) {
//                                                    ArrayList<Product> productsallArrayList = (ArrayList<Product>) task.getResult().toObjects(Product.class);
//                                                    for (int i = 0; i < productsallArrayList.size(); i++) {
//                                                        String id = task.getResult().getDocuments().get(i).getId();
//                                                        Product product = productsallArrayList.get(i);
//                                                        product.setId(id);
//
//
//                                                        firebaseFirestore.collection("Product2").document(id).update("id", id);
//
//
//                                                    }
//                                                }
//
//                                            }
//                                        });
//
//                                        Toast.makeText(AddProducts.this, "Product added successfully ", Toast.LENGTH_SHORT).show();
//                                        finish();
//                                    } else {
//                                        Toast.makeText(AddProducts.this, "Product addition failed  ", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//                            });
//
//                        }
//                    }
//                });
//
//            }
//        });
//
//
//    }
//    private void uploadImageToFirestore(Uri imageUri) {
//        // Create a unique filename for the image
//        String filename = UUID.randomUUID().toString();
//
//        // Get a reference to the Firebase Storage location
//        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images").child(filename);
//
//        // Upload the image to Firebase Storage
//        storageRef.putFile(imageUri)
//                .addOnSuccessListener(taskSnapshot -> {
//                    // Image uploaded successfully, get the download URL
//                    storageRef.getDownloadUrl()
//                            .addOnSuccessListener(uri -> {
//                                // Save the download URL to Firestore
//                                saveImageUrlToFirestore(uri.toString());
//                            })
//                            .addOnFailureListener(e -> {
//                                // Handle any errors that occur during URL retrieval
//                            });
//                })
//                .addOnFailureListener(e -> {
//                    // Handle any errors that occur during image upload
//                });
//    }
//    private void saveImageUrlToFirestore(String imageUrl) {
//        // Get a reference to the Firestore collection where you want to store the image URLs
//        CollectionReference imagesRef = FirebaseFirestore.getInstance().collection("images");
//
//        // Create a new document and set the image URL as a field
//        DocumentReference newImageRef = imagesRef.document();
//        newImageRef.set(new YourDataModel(imageUrl))
//                .addOnSuccessListener(aVoid -> {
//                    // Image URL saved successfully to Firestore
//                })
//                .addOnFailureListener(e -> {
//                    // Handle any errors that occur during URL saving
//                });
//    }


    private void UploadIMages() {

        // we need list that images urls
        for (int i = 0; i < ChooseImageList.size(); i++) {
            Uri IndividualImage = ChooseImageList.get(i);
            if (IndividualImage != null) {
                progressDialog.show();
                StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ItemImages");
                final StorageReference ImageName = ImageFolder.child("Image" + i + ": " + IndividualImage.getLastPathSegment());
                ImageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ImageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                UrlsList.add(String.valueOf(uri));
                                if (UrlsList.size() == ChooseImageList.size()) {
                                    StoreLinks(UrlsList);
                                }
                            }
                        });

                    }
                });
            } else {
                Toast.makeText(this, "Please fill All Field", Toast.LENGTH_SHORT).show();
            }
        }


    }


    private void StoreLinks(ArrayList<String> urlsList) {
        // now we need get text from EditText
        String Name = ItemName.getText().toString();
        String Description = ItemDesc.getText().toString();
        String category1 = category.getText().toString();
        String price1 = price.getText().toString();
        if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Description) && ImageUri != null) {
            // now we need a model class
            firestore.collection("Productive family").document(auth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.get("name") != null) {
                      productive_family=  documentSnapshot.getString("name");
                      model.setProductive_family(documentSnapshot.getString("name"));
                        
                    }
                }
            });
            model = new Product2(Name,Description,price1,category1,auth.getUid(),UrlsList);
            firestore.collection("Products").add(model).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                @Override
                public void onSuccess(DocumentReference documentReference) {
                    // now here we need Item id and set into model
                    model.setId(documentReference.getId());
                    firestore.collection("Products").document(model.getId())
                            .set(model, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            // if data uploaded successfully then show ntoast
                            Toast.makeText(AddProducts.this, "Your data Uploaded Successfully", Toast.LENGTH_SHORT).show();

                        }
                    });


                }
            });

        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Please Fill All field", Toast.LENGTH_SHORT).show();
        }
        // if you want to clear viewpager after uploading Images
        ChooseImageList.clear();


    }

    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(AddProducts.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddProducts.this, new
                        String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                PickImageFromgallry();
            } else {
                PickImageFromgallry();
            }

        } else {
            PickImageFromgallry();
        }
    }
    private void PickImageFromgallry() {
        // here we go to gallery and select Image
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getClipData() != null) {
            int count = data.getClipData().getItemCount();
            for (int i = 0; i < count; i++) {
                ImageUri = data.getClipData().getItemAt(i).getUri();
                ChooseImageList.add(ImageUri);
// now we need Adapter to show Images in viewpager

            }
            setAdapter();

        }
    }

    private void setAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, ChooseImageList);
        viewPager.setAdapter(adapter);
    }










}
