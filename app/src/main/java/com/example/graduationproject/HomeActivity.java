package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.graduationproject.databinding.ActivityHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),ProductiveFamilyProfile.class));
            }
        });
        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Category")
                .document("categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            List<String> names = (List<String>) document.get("categories_name");
                            List<String> images = (List<String>) document.get("categories_image");
                            CategoryAdapter adapter=new CategoryAdapter(names, images, getApplicationContext(), new ListenerOnClickItem() {
                                @Override
                                public void OnClickItem(String name) {
                                    Intent intent=new Intent(getApplicationContext(),CategoryProductiveFamily.class);
                                    intent.putExtra("ctegoryname",name);
                                    startActivity(intent);

                                }
                            });
                            binding.rv.setAdapter(adapter);
                            binding.rv.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                        } else {
                            task.getException().printStackTrace();
                        }
                    }
                });
    }
}