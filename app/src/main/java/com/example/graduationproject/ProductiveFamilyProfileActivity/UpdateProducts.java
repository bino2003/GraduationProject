package com.example.graduationproject.ProductiveFamilyProfileActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
//import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.graduationproject.Adapters.ViewPagerAdapter;
import com.example.graduationproject.Model.Product2;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.ActivityUpdateProductsBinding;
import com.example.graduationproject.Model.Product;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class UpdateProducts extends AppCompatActivity {
    final private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    ActivityUpdateProductsBinding binding;
    FirebaseAuth firebaseAuth;
    String imageupdate;
    EditText ItemName, ItemDesc,price,category;
    ArrayList<String> ChooseImageList;
    ArrayList<Uri> ChooseImageList2;
    ArrayList<Uri> uriArrayList;
    ArrayList<String> UrlsList;
    Uri ImageUri;
    String productive_family;
    private Uri imageuri;
    Product2 model;
    ProgressDialog progressDialog;
    StorageReference storageRef=FirebaseStorage.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
//        ItemDesc = findViewById(R.id.et_descriptionupdate);
//        ItemName = findViewById(R.id.et_nameupdate);
//        price = findViewById(R.id.et_priceadd);
//        category = findViewById(R.id.et_categryupdate);
        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Data");
        progressDialog.setMessage("Please Wait While Uploading Your data...");
        ChooseImageList2 = new ArrayList<>();
        UrlsList = new ArrayList<>();

        String id = intent.getStringExtra("id");
        binding.ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckPermission();

            }
        });
        firebaseFirestore.collection("Products").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document1 = task.getResult();
                    ChooseImageList  = (ArrayList<String>) document1.get("imageUrls");
                    uriArrayList=new ArrayList<>();
                    for (int i = 0; i < ChooseImageList.size(); i++) {
                        uriArrayList.add(Uri.parse(ChooseImageList.get(i)));
                    }
                    if (document1.get("imageUrls")!=null){
                        ViewPagerAdapter adapter=new ViewPagerAdapter(getBaseContext(), uriArrayList,true);
                        binding.viewPager.setAdapter(adapter);
                    }
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
//                            Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.uplodeimgupdate);
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
                UploadIMages();

            }
        });
//        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
//                new ActivityResultCallback<ActivityResult>() {
//                    @Override
//                    public void onActivityResult(ActivityResult result) {
//                        if (result.getResultCode() == Activity.RESULT_OK) {
//                            Intent data = result.getData();
//                            imageuri = data.getData();
////                            binding.uplodeimgupdate.setImageURI(imageuri);
//
//                        } else {
//                            Toast.makeText(UpdateProducts.this, "No Image Selected", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//        );
//        binding.ChooseImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent photoPicker = new Intent();
//                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
//                photoPicker.setType("image/*");
//                activityResultLauncher.launch(photoPicker);
//            }
//        });
    }

//    void updateproduct() {
//        Intent intent = getIntent();
//        String id = intent.getStringExtra("id");
//
//
//        String newname = binding.etNameupdate.getText().toString();
//        String newdescription = binding.etDescriptionupdate.getText().toString();
//        String newcategory = binding.etCategryupdate.getText().toString();
//        String newprice = binding.etPriceupdate.getText().toString();
//
//        firebaseAuth = FirebaseAuth.getInstance();
//        Product2 product = new Product2();
//        UploadIMages();
//
//        product.setName(newname);
//        product.setCategory(newcategory);
//        product.setDescription(newdescription);
//        product.setPrice(newprice);
//        product.setUser(firebaseAuth.getUid());
//        product.setImageUrls(UrlsList);
//
//        if (imageuri != null) {
//            StorageReference riversRef = storageRef.child("images/"+imageuri.getLastPathSegment());
//            UploadTask uploadTask = riversRef.putFile(imageuri);
//
//
//            uploadTask.addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    Toast.makeText(UpdateProducts.this, "upload failed", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(UpdateProducts.this, "upload success", Toast.LENGTH_SHORT).show();
//                    riversRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Uri> task) {
//                            if (task.isSuccessful()){
//                                product.setImage(task.getResult().toString());
//                                firebaseFirestore.collection("Products").document(id).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//
//
//                                            Toast.makeText(UpdateProducts.this, "Product Update successfully ", Toast.LENGTH_SHORT).show();
//                                            finish();
//                                        } else {
//                                            Toast.makeText(UpdateProducts.this, "Product Update failed  ", Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    }
//                                });
//
//                            }
//                        }
//                    });
//
//                }
//            });
//
//        } else {
//            product.setImageUrls(UrlsList);
//            firebaseFirestore.collection("Products").document(id).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//                    if (task.isSuccessful()) {
//
//
//                        Toast.makeText(UpdateProducts.this, "Product Update successfully ", Toast.LENGTH_SHORT).show();
//                        finish();
//                    } else {
//                        Toast.makeText(UpdateProducts.this, "Product Update failed  ", Toast.LENGTH_SHORT).show();
//
//                    }
//                }
//            });
//
//        }
//
//
//
//    }
    private void CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(UpdateProducts.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(UpdateProducts.this, new
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
            if (count==1){
                model.setImage(String.valueOf(data.getClipData().getItemAt(1)));
                Log.d("imageee", String.valueOf(data.getClipData().getItemAt(1)));
                ImageUri = data.getClipData().getItemAt(1).getUri();
                ChooseImageList2.add(ImageUri);
                Log.d("countss", count+"");
            }
            Log.d("countss", count+"");

                for (int i = 0; i < count; i++) {
                    ImageUri = data.getClipData().getItemAt(i).getUri();
                    ChooseImageList2.add(ImageUri);
                }

                setAdapter();

        }
    }
    private void setAdapter() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, ChooseImageList2,true);
        binding.viewPager.setAdapter(adapter);
    }


    private void UploadIMages() {

        // we need list that images urls
        for (int i = 0; i < ChooseImageList2.size(); i++) {
            Uri IndividualImage = ChooseImageList2.get(i);
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
                                if (UrlsList.size() == ChooseImageList2.size()) {
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
        String Name = binding.etNameupdate.getText().toString();
        Log.d("namerer", Name);
        String Description = binding.etDescriptionupdate.getText().toString();
        String category1 = binding.etCategryupdate.getText().toString();
        String price1 = binding.etPriceupdate.getText().toString();

        if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Description) && ImageUri != null && !TextUtils.isEmpty(category1) && !TextUtils.isEmpty(price1)) {
            // now we need a model class
            model = new Product2(Name,Description,price1,category1,firebaseAuth.getUid(),UrlsList);
            firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.get("name") != null) {
                        productive_family=  documentSnapshot.getString("name");
                        model.setProductive_family(documentSnapshot.getString("name"));
//                        model.setImageUrls();

                    }
                }
            });

            Log.d("urlsList", urlsList.toString());
            Intent intent = getIntent();
            String id = intent.getStringExtra("id");
            Log.d("model", model.toString());
            firebaseFirestore.collection("Products").document(id).set(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(Task<Void> task) {
//                    model.setId(documentReference.getId());
                    firebaseFirestore.collection("Products").document(id)
                            .set(model, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            // if data uploaded successfully then show ntoast
                            Toast.makeText(UpdateProducts.this, "Your data Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });
                }
            });
/*
     model.setId(documentReference.getId());
                    firestore.collection("Products").document(model.getId())
                            .set(model, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            // if data uploaded successfully then show ntoast
                            Toast.makeText(AddProducts.this, "Your data Uploaded Successfully", Toast.LENGTH_SHORT).show();
                            finish();

                        }
                    });
 */
        } else {
            progressDialog.dismiss();
            Toast.makeText(this, "Please Fill All field", Toast.LENGTH_SHORT).show();
        }
        // if you want to clear viewpager after uploading Images
//        ChooseImageList.clear();


    }
}