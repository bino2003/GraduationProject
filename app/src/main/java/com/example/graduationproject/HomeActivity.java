package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.graduationproject.databinding.ActivityHomeBinding;
import com.example.graduationproject.model.ProductiveFamily;
import com.example.graduationproject.model.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    users user;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user=auth.getCurrentUser();

                auth.signOut();
                user.reload();

                Toast.makeText(HomeActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
            //innnnn
                //rrr
            startActivity(intent);
//            finish();


            }
        });



        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             String id=   auth.getUid();
                firestore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        user= task.getResult().toObject(users.class);
                        String cat=user.getCategorize();
                        if (cat.equals("Productive family")){
                            startActivity(new Intent(getApplicationContext(),ProductiveFamilyProfile.class));

                        }else{
                            startActivity(new Intent(getApplicationContext(),UsersProfile.class));

                        }

                    }
                });


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