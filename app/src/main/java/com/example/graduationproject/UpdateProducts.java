package com.example.graduationproject;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class UpdateProducts extends AppCompatActivity {
ActivityUpdateProductsBinding binding;

FirebaseAuth firebaseAuth;
    private Uri imageuri;
    final private FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
    final  private StorageReference storageReference= FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityUpdateProductsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();
        String oldname=intent.getStringExtra("nameupdate");
        String oldcategory=intent.getStringExtra("categoryupdate");
        String olddescription=intent.getStringExtra("descriptionupdate");
        String oldprice=intent.getStringExtra("priceupdate");
        String id = intent.getStringExtra("id");
        String imageupdate=intent.getStringExtra("imageupdate");
        Glide.with(getApplicationContext()).load(imageupdate).circleCrop().into(binding.uplodeimgupdate);

        binding.etCategryupdate.setText(oldcategory);
        binding.etDescriptionupdate.setText(olddescription);
        binding.etNameupdate.setText(oldname);
        binding.etPriceupdate.setText(oldprice);
        binding.exitupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateProducts.this,ProductiveFamilyProfile.class));
            }
        });
binding.updatebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String name=   binding.etNameupdate.getText().toString();
        String description=binding.etDescriptionupdate.getText().toString();
        String price =binding.etPriceupdate.getText().toString();
        String category =binding.etCategryupdate.getText().toString();

        if (name.isEmpty()||category.isEmpty()||description.isEmpty()||price.isEmpty()){
            Toast.makeText(UpdateProducts.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();

        }else {
            updateproduct();
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
                            binding.uplodeimgupdate.setImageURI(imageuri);

                        }else {
                            Toast.makeText(UpdateProducts.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        binding.uplodeimgupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker=new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
    }
    void updateproduct(){
        Intent intent=getIntent();
        String id = intent.getStringExtra("id");


        String newname=binding.etNameupdate.getText().toString();
        String newdescription=binding.etDescriptionupdate.getText().toString();
        String newcategory=binding.etCategryupdate.getText().toString();
        String newprice=binding.etPriceupdate.getText().toString();

        firebaseAuth= FirebaseAuth.getInstance();
        Product product=new Product();

        product.setName(newname);
        product.setCategory(newcategory);
        product.setDescription(newdescription);
        product.setPrice(newprice);
        product.setImage(String.valueOf(imageuri));
        product.setUser(firebaseAuth.getUid());
        firebaseFirestore.collection("Products").document(id).set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){


                    Toast.makeText(UpdateProducts.this, "Product Update successfully ", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UpdateProducts.this, "Product Update failed  ", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}