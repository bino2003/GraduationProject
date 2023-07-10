package com.example.graduationproject.DetailsProductiveFamilyActivity;

import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graduationproject.Adapters.ItemDetailsProductAdapter;
import com.example.graduationproject.Interface.DetailsProductAction;


import com.example.graduationproject.Model.Favorite2;
import com.example.graduationproject.Model.Product2;
import com.example.graduationproject.databinding.FragmentItemDetailsProductBinding;
import com.example.graduationproject.Model.Favorites;
import com.example.graduationproject.Model.Product;

import com.example.graduationproject.Model.ProductiveFamily;
import com.example.graduationproject.Model.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailsProduct extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_db_name2 = "dbName2";
    private static final String ARG_ID_ProductiveFamily = "id";
    private static final String ARG_ID_Product_Id = "product_id";
    static int allPrice = 0;
    static int i = 0;
    boolean isfav;
    boolean isexists;
    ArrayList<Product2> productsArrayList = new ArrayList<>();
    ItemDetailsProductAdapter itemDetailsProductAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    Favorite2 favorites_ = new Favorite2();

    FirebaseFirestore db;
    Product products;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String dbname;
    private String product_id;
    boolean isexistsuser;
    private String id;
    boolean notexistsuser;
    boolean notexistproductivefamily;

    public ItemDetailsProduct() {
        // Required empty public constructor
    }


    public static ItemDetailsProduct newInstance(String dbname, String id, String product_id) {
        ItemDetailsProduct fragment = new ItemDetailsProduct();
        Bundle args = new Bundle();

        args.putString(ARG_db_name2, dbname);
        args.putString(ARG_ID_ProductiveFamily, id);
        args.putString(ARG_ID_Product_Id, product_id);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbname = getArguments().getString(ARG_db_name2);
            id = getArguments().getString(ARG_ID_ProductiveFamily);
            product_id = getArguments().getString(ARG_ID_Product_Id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentItemDetailsProductBinding binding = FragmentItemDetailsProductBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();


        firebaseFirestore.collection(dbname).whereEqualTo("user", id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    firebaseFirestore = FirebaseFirestore.getInstance();
                    binding.progressBar.setVisibility(View.GONE);
                    productsArrayList = (ArrayList<Product2>) task.getResult().toObjects(Product2.class);
                    for (int i = 0; i < productsArrayList.size(); i++) {
                        String id = task.getResult().getDocuments().get(i).getId();
                        Product2 product = productsArrayList.get(i);
                        product.setId(id);
                    }


                    itemDetailsProductAdapter = new ItemDetailsProductAdapter(productsArrayList, getActivity(), new DetailsProductAction() {
                        @Override
                        public void onfav(Product2 product) {
                            firebaseAuth = FirebaseAuth.getInstance();

                            DocumentReference documentReference = firebaseFirestore.collection("Productive family").document(id);
                            firebaseFirestore.collection("Productive family").document(id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        favorites_.setCategory(product.getCategory());
                                        favorites_.setDescription(product.getDescription());
                                        favorites_.setName(product.getName());
                                        favorites_.setImage(product.getImage());
                                        favorites_.setImageUrls(product.getImageUrls());
                                        favorites_.setPrice(product.getPrice());
                                        favorites_.setUser(firebaseAuth.getUid());
                                        favorites_.setId(product.getId());
                                        favorites_.setProductiveFamilyName(task.getResult().getString("name"));
                                        favorites_.setProductiveFamilyId(task.getResult().getString("id"));
                                        Log.d("idProductivefamily", task.getResult().getString("id"));


                                        firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    firebaseAuth = FirebaseAuth.getInstance();
                                                    List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                                                    for (int i = 0; i < productiveFamilyList.size(); i++) {
                                                        String id = task.getResult().getDocuments().get(i).getId();
                                                        firebaseFirestore.collection("Productive family")
                                                                .document(firebaseAuth.getUid()).collection("Favorites")
                                                                .document(favorites_.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                notexistproductivefamily = task.getResult().exists();
                                                                System.out.println("notbian" + notexistproductivefamily);
                                                                if (id.equals(firebaseAuth.getUid())) {
                                                                    if (firebaseAuth.getUid() != null && notexistproductivefamily == false) {
                                                                        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).collection("Favorites").document(favorites_.getId()).set(favorites_).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    Toast.makeText(getActivity(), "Product added to favorites", Toast.LENGTH_SHORT).show();

                                                                                } else {
                                                                                    Toast.makeText(getActivity(), "Adding product to favorites failed", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });
                                                                    } else {
                                                                        Toast.makeText(getActivity(), "This product is already in my favourites", Toast.LENGTH_SHORT).show();
                                                                        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).collection("Favorites").document(favorites_.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        });
                                                        System.out.println("notbian" + notexistproductivefamily);


                                                    }
                                                }
                                            }
                                        });
                                        firebaseFirestore.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    firebaseAuth = FirebaseAuth.getInstance();
                                                    List<users> usersList = task.getResult().toObjects(users.class);
                                                    for (int i = 0; i < usersList.size(); i++) {
                                                        String id = task.getResult().getDocuments().get(i).getId();
                                                        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").document(favorites_.getId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                notexistsuser = task.getResult().exists();
                                                                if (id.equals(firebaseAuth.getUid())) {
                                                                    if (firebaseAuth.getUid() != null && notexistsuser == false) {
                                                                        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").document(favorites_.getId()).set(favorites_).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful()) {
                                                                                    Toast.makeText(getActivity(), "Product added to favoritesusers", Toast.LENGTH_SHORT).show();

                                                                                } else {
                                                                                    Toast.makeText(getActivity(), "Adding product to favorites failed", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            }
                                                                        });
                                                                    } else {
                                                                        Toast.makeText(getActivity(), "This product is already in my favourites", Toast.LENGTH_SHORT).show();
                                                                        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").document(favorites_.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                Toast.makeText(getActivity(), "delete", Toast.LENGTH_SHORT).show();

                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            }
                                                        });
                                                        System.out.println("notbian" + notexistsuser);


                                                    }
                                                }
                                            }
                                        });


                                    }

                                }
                            });


                            Toast.makeText(getActivity(), documentReference.getId() + "", Toast.LENGTH_SHORT).show();


                        }




                        @Override
                        public void onClickItem(Product2 product) {
                            Intent intent = new Intent(getActivity(), ViewDetailsProducts.class);

                            intent.putExtra("detailsproductid", product.getId());
                            startActivity(intent);
                        }
                    }, isfav);
                    binding.rv.setAdapter(itemDetailsProductAdapter);

                    binding.rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    itemDetailsProductAdapter.notifyDataSetChanged();

                }

            }
        });


        return binding.getRoot();
    }
}