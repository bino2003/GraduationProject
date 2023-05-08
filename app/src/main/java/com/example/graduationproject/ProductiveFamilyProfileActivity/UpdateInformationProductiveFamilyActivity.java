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

import com.bumptech.glide.Glide;
import com.example.graduationproject.databinding.ActivityUpdateInformationProductiveFamilyBinding;
import com.example.graduationproject.model.ProductiveFamily;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class UpdateInformationProductiveFamilyActivity extends AppCompatActivity {

    ActivityUpdateInformationProductiveFamilyBinding binding;
    FirebaseAuth firebaseAuth;
    private Uri imageuri;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateInformationProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            imageuri = data.getData();
                            binding.uplodeimgupdate.setImageURI(imageuri);

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
                        Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image"))).circleCrop().into(binding.uplodeimgupdate);
                        Log.d("image", document.getString("image"));
//                        Glide.with(getApplicationContext()).load(Uri.parse(document.getString("image")).into(binding.imageView2);
                        binding.etName.setText(document.getString("name"));
                        binding.etCategryupdate.setText(document.getString("category"));
                        binding.etDescriptionupdate.setText(document.getString("details"));
                        binding.etPhoneupdate.setText("" + document.getLong("phone").intValue());
//                        double phone=document.getDouble("phone");
//                        int phone
//                        binding.tvPhone.setText((Integer));
                    } else {
                        Log.d("TAG 2", "No such document");
                    }
                } else {
                    Log.d("TAG 3", "get failed with ", task.getException());
                }


            }

        });
        binding.updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.etName.getText().toString();
                int phone = Integer.parseInt(binding.etPhoneupdate.getText().toString());
                String category = binding.etName.getText().toString();
                String descrption = binding.etDescriptionupdate.getText().toString();
                String image = String.valueOf(imageuri);
                ProductiveFamily productiveFamily = new ProductiveFamily();
                productiveFamily.setName(name);
                productiveFamily.setPhone(phone);
                productiveFamily.setDetails(descrption);
                productiveFamily.setImage(image);
                productiveFamily.setCategory(category);

                firebaseFirestore.collection("Productive family").document(firebaseAuth.getCurrentUser().getUid()).set(productiveFamily).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("data ", productiveFamily.toString());

                            Toast.makeText(getBaseContext(), " successfully ", Toast.LENGTH_SHORT).show();
                            //    startActivity(new Intent(getActivity(), ViewInformationProtectiveFamilyActivity.class));

                        } else {
                            Toast.makeText(getBaseContext(), "not successfully  ", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

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