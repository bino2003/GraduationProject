package com.example.graduationproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.graduationproject.Category.CategoryFragment;
import com.example.graduationproject.DistinguishedFamily.DistinguishedFamilyFragment;
import com.example.graduationproject.ProductiveFamilyProfileActivity.ProfileFragment;

import com.example.graduationproject.databinding.ActivityHomeBinding;
import com.example.graduationproject.model.users;

import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;
    users user;
    String nam;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    FirebaseFirestore firestore=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        binding.logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FirebaseUser user=auth.getCurrentUser();
//
//                auth.signOut();
//                user.reload();
//
//                Toast.makeText(HomeActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
////            Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
////            //innnnn
////                //rrr
////            startActivity(intent);
////            finish();
//
//
//            }
//        });


//binding.inflate(R.)
        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.categories:
                        replacefragmint(new CategoryFragment());
                        break;

                    case R.id.profile:
                        replacefragmint(new ProfileFragment());
                        break;

                    case R.id.favorite:
                        replacefragmint(new FavouriteFragment());
                        break;

                    case R.id.newss:
                        replacefragmint(new DistinguishedFamilyFragment());
                        break;

                }
                return true;
            }
        });
        /*
        NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@androidx.annotation.NonNull MenuItem item) {

            }
        });

        */

//        firestore = FirebaseFirestore.getInstance();
//        firestore.collection("Category")
//                .document("categories")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            List<String> names = (List<String>) document.get("categories_name");
//                            List<String> images = (List<String>) document.get("categories_image");
//                            CategoryAdapter adapter=new CategoryAdapter(names, images, getApplicationContext(), new ListenerOnClickItem() {
//                                @Override
//                                public void OnClickItem(String name) {
//                                    Intent intent=new Intent(getApplicationContext(),CategoryProductiveFamily.class);
//                                    intent.putExtra("ctegoryname",name);
//                                    startActivity(intent);
//
//
//                                }
//                            });
//
//                        }
//                        else {
//                            task.getException().printStackTrace();
//                        }
//                    }
//                });

    }

    private void replacefragmint(Fragment fragment) {
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.Framin_tLayout,fragment);
        fragmentTransaction.commit();

    }

    //             String id=   auth.getUid();
//                firestore.collection("users").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        user= task.getResult().toObject(users.class);
//                        String cat=user.getCategorize();
//                        if (cat.equals("Productive family")){
//                            startActivity(new Intent(getApplicationContext(),ProductiveFamilyProfile.class));
//
//                        }else{
//                            startActivity(new Intent(getApplicationContext(),UsersProfile.class));
//
//                        }
//
//                    }
//                });
//
//
//            }


}