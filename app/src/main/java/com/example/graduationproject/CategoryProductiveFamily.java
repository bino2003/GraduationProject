package com.example.graduationproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;
import android.location.Location;

import com.example.graduationproject.Adapters.CategoryProductFamilyAdapter;
import com.example.graduationproject.DetailsProductiveFamilyActivity.DetailsProductiveFamily;
import com.example.graduationproject.Interface.OnClickProductiveFamily;

import com.example.graduationproject.Interface.UnFavoritve;
import com.example.graduationproject.Model.Favorites;
import com.example.graduationproject.Model.users;
import com.example.graduationproject.databinding.ActivityCategoryProductiveFamilyBinding;

import com.example.graduationproject.Model.ProductiveFamily;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import com.google.firebase.firestore.QuerySnapshot;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CategoryProductiveFamily extends AppCompatActivity {

    ActivityCategoryProductiveFamilyBinding binding;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

String iduser=FirebaseAuth.getInstance().getUid();
    ArrayList<ProductiveFamily> productiveFamilyArrayListdistance = new ArrayList<>();
    ArrayList<ProductiveFamily> productiveFamilyArrayList=new ArrayList<>();
    String cat;
    double userlat;
    CategoryProductFamilyAdapter categoryProductFamilyAdapter;
    double  userlong;
   String   userlatString;
    String   userlongString;
    GeoPoint[] boundingCoordinates;
//    CategoryProductFamilyAdapter categoryProductFamilyAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        productiveFamilyArrayList.clear();
        refresh();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBacke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        cat = getIntent().getStringExtra("ctegoryname");
        firebaseAuth=FirebaseAuth.getInstance();





        // Step 3: Calculate the bounding coordinates

        // Step 4: Perform the quer
        binding.tvCategoryName.setText(cat);

        getproductivefamilydistance();
                binding.simpleSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
//                filterList(s);
                ArrayList<ProductiveFamily> productiveFamilies = new ArrayList<>();
                if (productiveFamilyArrayListdistance!=null){
                    for (ProductiveFamily productiveFamily: productiveFamilyArrayListdistance ){
                        if (productiveFamily.getName().toLowerCase().contains(s.toLowerCase())){
                            productiveFamilies.add(productiveFamily);
                        }
                        if (productiveFamilies.isEmpty()){
//                            Toast.makeText(getBaseContext(), "No Result", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getBaseContext(), HandleEmpityActivity.class));
                        }else {
                            categoryProductFamilyAdapter.setFilterList(productiveFamilies);
                        }
                }

                }
                else {
                    for (ProductiveFamily productiveFamily: productiveFamilyArrayList ){
                        if (productiveFamily.getName().toLowerCase().contains(s.toLowerCase())){
                            productiveFamilies.add(productiveFamily);
                        }
                        if (productiveFamilies.isEmpty()){
//                            Toast.makeText(getBaseContext(), "No Result", Toast.LENGTH_SHORT).show();
//                            startActivity(new Intent(getBaseContext(), HandleEmpityActivity.class));
                        }else {
                            categoryProductFamilyAdapter.setFilterList(productiveFamilies);
                        }
                    }

                }

//                CollectionReference itemsRef = firebaseFirestore.collection("Productive family");
//                Query query = itemsRef.whereEqualTo("name", s);
                return false;
            }
        });

    }

    void getproductivefamilydistance() {
        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<users> usersList = task.getResult().toObjects(users.class);
                    for (int i = 0; i < usersList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();

                        if (id.equals(firebaseAuth.getUid())) {
                            if (firebaseAuth.getUid() != null) {
                                firebaseFirestore.collection("users").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot documentSnapshot=task.getResult();
                                            userlatString=       task.getResult().getString("latitude");
                                            userlat=Double.parseDouble(userlatString);
                                            userlongString=       task.getResult().getString("longitude");

                                            userlong=Double.parseDouble(userlongString);
                                            firebaseFirestore.collection("Productive family").whereEqualTo("productCategory", cat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {

                                                        productiveFamilyArrayListdistance= (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);
                                                        // ...
                                                        binding.progressBar2.setVisibility(View.GONE);






                                                            // Filter families within a certain distance threshold (e.g., 10 km)




                                                        // Extract the family's location from the document



                                                        if (productiveFamilyArrayListdistance.isEmpty()) {
                                                            startActivity(new Intent(getApplicationContext(), HandleEmpityActivity.class));
                                                            finish();
                                                            binding.progressBar2.setVisibility(View.GONE);
                                                        }
                                                         categoryProductFamilyAdapter = new CategoryProductFamilyAdapter(CategoryProductiveFamily.this, new OnClickProductiveFamily() {
                                                            @Override
                                                            public void onclickproductiveFamily(ProductiveFamily productiveFamily) {
                                                                if (productiveFamily.getId() != null) {
                                                                    Intent intent = new Intent(getApplicationContext(), DetailsProductiveFamily.class);

                                                                    intent.putExtra("idproductivefamily", productiveFamily.getId());
                                                                    startActivity(intent);
                                                                }


                                                            }
                                                        }, productiveFamilyArrayList);

                                                        binding.rv.setAdapter(categoryProductFamilyAdapter);

                                                        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                                                        categoryProductFamilyAdapter.notifyDataSetChanged();
                                                    } else if (task.isCanceled()) {
                                                        Toast.makeText(CategoryProductiveFamily.this, "faild", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                        }
                                    }
                                });

                            }
                        }

                    }

                }
            }
        });
        firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                    for (int i = 0; i < productiveFamilyList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();

                        if (id.equals(firebaseAuth.getUid())) {
                            if (firebaseAuth.getUid() != null) {
                                firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot documentSnapshot=task.getResult();
                                            userlatString=       task.getResult().getString("latitude");
                                            userlat=Double.parseDouble(userlatString);
                                            userlongString=       task.getResult().getString("longitude");

                                            userlong=Double.parseDouble(userlongString);
                                            firebaseFirestore.collection("Productive family").whereEqualTo("productCategory", cat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {

                                                        productiveFamilyArrayListdistance= (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);
                                                        // ...
                                                        binding.progressBar2.setVisibility(View.GONE);





                                                            // Filter families within a certain distance threshold (e.g., 10 km)




                                                        // Extract the family's location from the document



                                                        if (productiveFamilyArrayListdistance.isEmpty()) {
                                                            startActivity(new Intent(getApplicationContext(), HandleEmpityActivity.class));
                                                            finish();
                                                            binding.progressBar2.setVisibility(View.GONE);
                                                        }
                                                        categoryProductFamilyAdapter = new CategoryProductFamilyAdapter(CategoryProductiveFamily.this, new OnClickProductiveFamily() {
                                                            @Override
                                                            public void onclickproductiveFamily(ProductiveFamily productiveFamily) {
                                                                if (productiveFamily.getId() != null) {
                                                                    Intent intent = new Intent(getApplicationContext(), DetailsProductiveFamily.class);

                                                                    intent.putExtra("idproductivefamily", productiveFamily.getId());
                                                                    startActivity(intent);
                                                                }


                                                            }
                                                        }, productiveFamilyArrayList);

                                                        binding.rv.setAdapter(categoryProductFamilyAdapter);

                                                        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                                                        categoryProductFamilyAdapter.notifyDataSetChanged();
                                                    } else if (task.isCanceled()) {
                                                        Toast.makeText(CategoryProductiveFamily.this, "faild", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                        }
                                    }
                                });

                            }
                        }

                    }

                }
            }
        });




    }
    void refresh(){
        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<users> usersList = task.getResult().toObjects(users.class);
                    for (int i = 0; i < usersList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();

                        if (id.equals(firebaseAuth.getUid())) {
                            if (firebaseAuth.getUid() != null) {
                                firebaseFirestore.collection("users").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot documentSnapshot=task.getResult();
                                            userlatString=       task.getResult().getString("latitude");
                                            userlat=Double.parseDouble(userlatString);
                                            userlongString=       task.getResult().getString("longitude");

                                            userlong=Double.parseDouble(userlongString);
                                            firebaseFirestore.collection("Productive family").whereEqualTo("productCategory", cat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {

                                                        productiveFamilyArrayListdistance= (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);
                                                        // ...
                                                        binding.progressBar2.setVisibility(View.GONE);




                                                        for (ProductiveFamily family : productiveFamilyArrayListdistance) {
                                                            float distance = calculateDistance(userlat, userlong, Double.parseDouble(family.getLatitude()), Double.parseDouble(family.getLongitude()));
                                                            if (distance<=10){
                                                                productiveFamilyArrayList.add(family);
                                                            }

                                                        }
                                                        for (ProductiveFamily family : productiveFamilyArrayListdistance) {
                                                            float distance = calculateDistance(userlat, userlong, Double.parseDouble(family.getLatitude()), Double.parseDouble(family.getLongitude()));
                                                            if (distance>10){
                                                                productiveFamilyArrayList.add(family);
                                                            }

                                                        }
                                                        // Filter families within a certain distance threshold (e.g., 10 km)




                                                        // Extract the family's location from the document



                                                        if (productiveFamilyArrayListdistance.isEmpty()) {
                                                            startActivity(new Intent(getApplicationContext(), HandleEmpityActivity.class));
                                                            finish();
                                                            binding.progressBar2.setVisibility(View.GONE);
                                                        }


                                                        categoryProductFamilyAdapter.notifyDataSetChanged();
                                                    } else if (task.isCanceled()) {
                                                        Toast.makeText(CategoryProductiveFamily.this, "faild", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                        }
                                    }
                                });

                            }
                        }

                    }

                }
            }
        });
        firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    firebaseAuth = FirebaseAuth.getInstance();
                    List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                    for (int i = 0; i < productiveFamilyList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();

                        if (id.equals(firebaseAuth.getUid())) {
                            if (firebaseAuth.getUid() != null) {
                                firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){
                                            DocumentSnapshot documentSnapshot=task.getResult();
                                            userlatString=       task.getResult().getString("latitude");
                                            userlat=Double.parseDouble(userlatString);
                                            userlongString=       task.getResult().getString("longitude");

                                            userlong=Double.parseDouble(userlongString);
                                            firebaseFirestore.collection("Productive family").whereEqualTo("productCategory", cat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {

                                                        productiveFamilyArrayListdistance= (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);
                                                        // ...
                                                        binding.progressBar2.setVisibility(View.GONE);



                                                        for (ProductiveFamily family : productiveFamilyArrayListdistance) {
                                                            float distance = calculateDistance(userlat, userlong, Double.parseDouble(family.getLatitude()), Double.parseDouble(family.getLongitude()));

                                                            if (distance<=10){
                                                                if (!productiveFamilyList.contains(family)){

                                                                    System.out.println(distance+family.getName()+family.getLatitude());
                                                                    productiveFamilyArrayList.add(family);
                                                                }

                                                            }

                                                        }

                                                        for (ProductiveFamily family : productiveFamilyArrayListdistance) {
                                                            float distance = calculateDistance(userlat, userlong, Double.parseDouble(family.getLatitude()), Double.parseDouble(family.getLongitude()));

                                                            if (distance>10){
                                                                if (!productiveFamilyList.contains(family)){
                                                                    System.out.println(distance+family.getName()+family.getLatitude());
                                                                    productiveFamilyArrayList.add(family);
                                                                }

                                                            }

                                                        }
                                                        // Filter families within a certain distance threshold (e.g., 10 km)




                                                        // Extract the family's location from the document



                                                        if (productiveFamilyArrayListdistance.isEmpty()) {
                                                            startActivity(new Intent(getApplicationContext(), HandleEmpityActivity.class));
                                                            finish();
                                                            binding.progressBar2.setVisibility(View.GONE);
                                                        }


                                                        categoryProductFamilyAdapter.notifyDataSetChanged();
                                                    } else if (task.isCanceled()) {
                                                        Toast.makeText(CategoryProductiveFamily.this, "faild", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });


                                        }
                                    }
                                });

                            }
                        }

                    }

                }
            }
        });


    }

    private float calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        int R = 6371; // Radius of the Earth in kilometers

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dlat = lat2Rad - lat1Rad;
        double dlon = lon2Rad - lon1Rad;

        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float distance = (float) (R * c);

        return distance;
    }

    // Function to get the user's location (example implementation)

}