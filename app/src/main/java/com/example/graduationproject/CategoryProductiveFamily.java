package com.example.graduationproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
    double  userlong;
   String   userlatString;
    String   userlongString;
    GeoPoint[] boundingCoordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore = FirebaseFirestore.getInstance();
        cat = getIntent().getStringExtra("ctegoryname");
        firebaseAuth=FirebaseAuth.getInstance();





        // Step 3: Calculate the bounding coordinates

        // Step 4: Perform the quer
        binding.tvCategoryName.setText(cat);

        getproductivefamily();


    }
  void   getproductivefamily() {
      firebaseFirestore.collection("Productive family").whereEqualTo("productCategory", cat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
              if (task.isSuccessful()) {
                  productiveFamilyArrayList= (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);                                   // ...
                  binding.progressBar2.setVisibility(View.GONE);



                  // Extract the family's location from the document



                  if (productiveFamilyArrayList.isEmpty()) {
                      startActivity(new Intent(getApplicationContext(), HandleEmpityActivity.class));
                      finish();
                      binding.progressBar2.setVisibility(View.GONE);
                  }
                  CategoryProductFamilyAdapter categoryProductFamilyAdapter = new CategoryProductFamilyAdapter(CategoryProductiveFamily.this, new OnClickProductiveFamily() {
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
                                                        productiveFamilyArrayListdistance= (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);                                   // ...
                                                        binding.progressBar2.setVisibility(View.GONE);
                                                        for (int i = 0; i < task.getResult().size(); i++) {
                                                            String    familyLongitudestring   =task.getResult().getDocuments().get(i).getString("longitude");
                                                            String    familyLatitudestring   =task.getResult().getDocuments().get(i).getString("latitude");
                                                            double familyLatitude = Double.parseDouble(familyLatitudestring);
                                                            double familyLongitude = Double.parseDouble(familyLongitudestring);


                                                            // Calculate the distance between the family's location and the user's location
                                                            float distance =calculateDistance(userlat, userlong, familyLatitude, familyLongitude);
                                                            System.out.println("BIAN"+distance);

                                                            System.out.println("BIAN1"+userlat);
                                                            System.out.println("BIAN1"+userlong);

                                                            // Filter families within a certain distance threshold (e.g., 10 km)
                                                            if (distance <= 10) {
                                                                ProductiveFamily productiveFamily=task.getResult().getDocuments().get(i).toObject(ProductiveFamily.class);
productiveFamilyArrayListdistance.add(productiveFamily);                                    // ...
                                                            }

                                                        }

                                                        // Extract the family's location from the document



                                                        if (productiveFamilyArrayListdistance.isEmpty()) {
                                                            startActivity(new Intent(getApplicationContext(), HandleEmpityActivity.class));
                                                            finish();
                                                            binding.progressBar2.setVisibility(View.GONE);
                                                        }
                                                        CategoryProductFamilyAdapter categoryProductFamilyAdapter = new CategoryProductFamilyAdapter(CategoryProductiveFamily.this, new OnClickProductiveFamily() {
                                                            @Override
                                                            public void onclickproductiveFamily(ProductiveFamily productiveFamily) {
                                                                if (productiveFamily.getId() != null) {
                                                                    Intent intent = new Intent(getApplicationContext(), DetailsProductiveFamily.class);

                                                                    intent.putExtra("idproductivefamily", productiveFamily.getId());
                                                                    startActivity(intent);
                                                                }


                                                            }
                                                        }, productiveFamilyArrayListdistance);

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
                                            System.out.println("bnan"+userlat);
                                            firebaseFirestore.collection("Productive family").whereEqualTo("productCategory", cat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        binding.progressBar2.setVisibility(View.GONE);
                                                        for (int i = 0; i < task.getResult().size(); i++) {
                                                            String    familyLongitudestring   =task.getResult().getDocuments().get(i).getString("longitude");
                                                            String    familyLatitudestring   =task.getResult().getDocuments().get(i).getString("latitude");
                                                            double familyLatitude = Double.parseDouble(familyLatitudestring);
                                                            double familyLongitude = Double.parseDouble(familyLongitudestring);


                                                            // Calculate the distance between the family's location and the user's location
                                                            float distance =calculateDistance(userlat, userlong, familyLatitude, familyLongitude);
                                                            System.out.println("BIAN"+distance);

                                                            System.out.println("BIAN1"+userlat);
                                                            System.out.println("BIAN1"+userlong);

                                                            // Filter families within a certain distance threshold (e.g., 10 km)
                                                            if (distance <= 10) {
                                                                ProductiveFamily productiveFamily=task.getResult().getDocuments().get(i).toObject(ProductiveFamily.class);
productiveFamilyArrayListdistance.add(productiveFamily);                                    // ...
                                                            }

                                                        }

                                                        // Extract the family's location from the document



                                                        if (productiveFamilyArrayListdistance.isEmpty()) {
                                                            startActivity(new Intent(getApplicationContext(), HandleEmpityActivity.class));
                                                            finish();
                                                            binding.progressBar2.setVisibility(View.GONE);
                                                        }
                                                        CategoryProductFamilyAdapter categoryProductFamilyAdapter = new CategoryProductFamilyAdapter(CategoryProductiveFamily.this, new OnClickProductiveFamily() {
                                                            @Override
                                                            public void onclickproductiveFamily(ProductiveFamily productiveFamily) {
                                                                if (productiveFamily.getId() != null) {
                                                                    Intent intent = new Intent(getApplicationContext(), DetailsProductiveFamily.class);

                                                                    intent.putExtra("idproductivefamily", productiveFamily.getId());
                                                                    startActivity(intent);
                                                                }


                                                            }
                                                        }, productiveFamilyArrayListdistance);

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

    private GeoPoint[] calculateBoundingCoordinates(double centerLat, double centerLng, double maxDistanceInKm) {
        // Earth radius in kilometers
        double earthRadiusInKm = 6371.0;

        // Convert center latitude and longitude to radians
        double centerLatRad = Math.toRadians(centerLat);
        double centerLngRad = Math.toRadians(centerLng);

        // Calculate the angular distance
        double angularDistance = maxDistanceInKm / earthRadiusInKm;

        // Calculate the latitude difference
        double latDifference = Math.toDegrees(angularDistance);

        // Calculate the minimum and maximum latitude
        double minLat = centerLat - latDifference;
        double maxLat = centerLat + latDifference;

        // Calculate the longitude difference based on the center latitude
        double lngDifference = Math.toDegrees(angularDistance / Math.cos(centerLatRad));

        // Calculate the minimum and maximum longitude
        double minLng = centerLng - lngDifference;
        double maxLng = centerLng + lngDifference;

        // Create GeoPoint instances for the bounding coordinates
        GeoPoint minPoint = new GeoPoint(minLat, minLng);
        GeoPoint maxPoint = new GeoPoint(maxLat, maxLng);

        // Return the bounding coordinates
        return new GeoPoint[]{minPoint, maxPoint};
    }
    public static float calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        Location location1 = new Location("");
        location1.setLatitude(lat1);
        location1.setLongitude(lon1);

        Location location2 = new Location("");
        location2.setLatitude(lat2);
        location2.setLongitude(lon2);

        return location1.distanceTo(location2) / 1000; // Convert meters to kilometers
    }

}