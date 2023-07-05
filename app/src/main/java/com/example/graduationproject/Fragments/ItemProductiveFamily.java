package com.example.graduationproject.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.graduationproject.Adapters.ProductAdapter;
import com.example.graduationproject.HandleEmpityActivity;
import com.example.graduationproject.Interface.OndeleteProduct;
import com.example.graduationproject.Interface.ProductsAction;

import com.example.graduationproject.Model.Favorites;
import com.example.graduationproject.Model.ProductiveFamily;
import com.example.graduationproject.Model.users;
import com.example.graduationproject.ProductiveFamilyProfileActivity.AddProducts;
import com.example.graduationproject.ProductiveFamilyProfileActivity.UpdateProducts;
import com.example.graduationproject.ProductiveFamilyProfileActivity.ViewProduct;
import com.example.graduationproject.R;
import com.example.graduationproject.databinding.FragmentItemProductiveFamilyBinding;
import com.example.graduationproject.Model.Product;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;


public class ItemProductiveFamily extends Fragment implements OndeleteProduct {
    ArrayList<Product> productsallArrayList=new ArrayList<>();
    ArrayList<Product> productsArrayList=new ArrayList<>();
    ArrayList<Product> newproductsArrayList=new ArrayList<>();
    ProductAdapter productAdapter;
    static int allPrice = 0;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static int i=0;
    FirebaseFirestore db;
    Product products;


    private static final String ARG_db_name = "dbName";
    private static final String ARG_db_id = "idFamily";
    private static final String ARG_db_id_product = "idProduct";

    private String dbname;
    private String family_id;
    private String product_id;

    public ItemProductiveFamily() {
        // Required empty public constructor
    }


    public static ItemProductiveFamily newInstance(String dbname,String family_id,String product_id) {
        ItemProductiveFamily fragment = new ItemProductiveFamily();
        Bundle args = new Bundle();

        args.putString(ARG_db_name, dbname);
        args.putString(ARG_db_id, family_id);
        args.putString(ARG_db_id_product, product_id);
        fragment.setArguments(args);
        return fragment;




    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbname = getArguments().getString(ARG_db_name);
            family_id = getArguments().getString(ARG_db_id);
            product_id = getArguments().getString(ARG_db_id_product);

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        getprouct();
    }

    @Override
    public void onResume() {
        super.onResume();
        getprouct();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentItemProductiveFamilyBinding binding = FragmentItemProductiveFamilyBinding.inflate(inflater, container, false);
        firebaseFirestore=FirebaseFirestore.getInstance();
        if (family_id!=null){
            Log.d("id!null", family_id);
            //     binding.add.setVisibility(View.GONE);
            // firebaseFirestore.collection("Products").document(product_id).get().addOnCompleteListener(m)
        }else{

        }
        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AddProducts.class);
                startActivity(intent);
            }
        });
        firebaseFirestore.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    firebaseAuth=FirebaseAuth.getInstance();
                    binding.progressBar.setVisibility(View.GONE);
                    productsallArrayList= (ArrayList<Product>) task.getResult().toObjects(Product.class);
                    if (productsallArrayList.isEmpty()){
                        startActivity(new Intent(getActivity(), HandleEmpityActivity.class));

                        binding.progressBar.setVisibility(View.GONE);
                    }
                    for (int i=0 ;i<productsallArrayList.size();i++){
                        String id= task.getResult().getDocuments().get(i).getId();
                        Product product=productsallArrayList.get(i);
                        product.setId(id);

                        String user= product.getUser();
                        String userid= firebaseAuth.getUid();
//                        if (user.equals(userid)){
//                            productsArrayList.add(product);
//                        }else {
//
//                        }
                    }

                    //       Toast.makeText(getContext(), productsArrayList+"", Toast.LENGTH_SHORT).show();
                    productAdapter=new ProductAdapter(productsArrayList, getActivity(), new ProductsAction() {
                        @Override
                        public void OnDelete(String name, int pos) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("DELETE MESSAGE");
                            builder.setMessage("Are You Sure You Want To Delete Tis Product");
                            builder.setIcon(R.drawable.ic_baseline_delete_outline_24);
                            builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Product product=productsArrayList.get(pos);
                                    firebaseFirestore.collection("Products").document(product.getId()).delete();
                                    Toast.makeText(getActivity(), "Product deleted", Toast.LENGTH_SHORT).show();

                                    productsArrayList.remove(product);

                                    productAdapter.notifyDataSetChanged();
                                    productAdapter.notifyItemChanged(pos);
                                    firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                firebaseAuth = FirebaseAuth.getInstance();
                                                List<ProductiveFamily> productiveFamilyList = task.getResult().toObjects(ProductiveFamily.class);
                                                for (int i = 0; i < productiveFamilyList.size(); i++) {
                                                    String id = task.getResult().getDocuments().get(i).getId();
                                                    if (id.equals(firebaseAuth.getUid())) {
                                                        firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()){

                                                                List<Favorites> favoritesList=    task.getResult().toObjects(Favorites.class);
                                                                    for (int j = 0; j < favoritesList.size(); j++) {
                                                                     String favid=   task.getResult().getDocuments().get(j).getString("id");
                                                                        if (favid.equals(product.getId())){
                                                                            firebaseFirestore.collection("Productive family").document(firebaseAuth.getUid()).collection("Favorites").document(product.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                                                                    Toast.makeText(getActivity(), "delete from fav", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }


                                                                    }



                                                                }
                                                            }
                                                        });

                                                    }


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
                                                    if (id.equals(firebaseAuth.getUid())) {
                                                        firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                            @Override
                                                            public void onComplete(@androidx.annotation.NonNull Task<QuerySnapshot> task) {
                                                                if (task.isSuccessful()){

                                                                    List<Favorites> favoritesList=    task.getResult().toObjects(Favorites.class);
                                                                    for (int j = 0; j < favoritesList.size(); j++) {
                                                                        String favid=   task.getResult().getDocuments().get(j).getString("id");
                                                                        if (favid.equals(product.getId())){
                                                                            firebaseFirestore.collection("users").document(firebaseAuth.getUid()).collection("Favorites").document(product.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                                                                    Toast.makeText(getActivity(), "delete from fav", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                        }


                                                                    }



                                                                }
                                                            }
                                                        });

                                                    }


                                                }
                                            }
                                        }
                                    });

                                }
                            });
                            builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.show();



                        }

                        @Override
                        public void OnUpdate(Product product) {

                            Intent intent = new Intent(getActivity(), UpdateProducts.class);
                            intent.putExtra("nameupdate", product.getName());
                            intent.putExtra("id", product.getId());
                            intent.putExtra("imageupdate", product.getImage());

                            intent.putExtra("categoryupdate", product.getCategory());
                            intent.putExtra("descriptionupdate", product.getDescription());
                            intent.putExtra("priceupdate", product.getPrice());
                            startActivity(intent);

                        }


                        @Override
                        public void OnClickItem(Product product) {
                            Intent intent = new Intent(getActivity(), ViewProduct.class);
                            intent.putExtra("name", product.getName());
                            intent.putExtra("category", product.getCategory());
                            intent.putExtra("description", product.getDescription());
                            intent.putExtra("image", product.getImage());
                            intent.putExtra("price", product.getPrice());
                            intent.putExtra("idview", product.getId());
                            //            Toast.makeText(getActivity(), product.getId(), Toast.LENGTH_SHORT).show();
                            startActivity(intent);


                        }
                    });
                    binding.rv.setAdapter(productAdapter);

                    binding.rv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    productAdapter.notifyDataSetChanged();


                }
                else if (task.isCanceled()){
                    Toast.makeText(getContext(), "faild", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Log.d("id_productive",  intent.getStringExtra("id"));



        return binding.getRoot();
    }


    @Override
    public void OnDelete(int pos) {
        System.out.println("DELETEBIAN2");
        Product product=productsArrayList.get(pos);
        firebaseFirestore.collection("Products").document(product.getId()).delete();
        Toast.makeText(getActivity(), "Product deleted", Toast.LENGTH_SHORT).show();

        productsArrayList.remove(product);

        productAdapter.notifyDataSetChanged();
        productAdapter.notifyItemChanged(pos);

    }
    public  View getprouct(){
        FragmentItemProductiveFamilyBinding binding = FragmentItemProductiveFamilyBinding.inflate(LayoutInflater.from(getActivity()));

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Products").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    firebaseAuth=FirebaseAuth.getInstance();
                    binding.progressBar.setVisibility(View.GONE);
                    productsallArrayList= (ArrayList<Product>) task.getResult().toObjects(Product.class);
                    if (productsallArrayList.isEmpty()){
                        startActivity(new Intent(getActivity(), HandleEmpityActivity.class));

                        binding.progressBar.setVisibility(View.GONE);
                    }
                    productsArrayList.clear();
                    for (int i=0 ;i<productsallArrayList.size();i++){
                        String id= task.getResult().getDocuments().get(i).getId();
                        Product product=productsallArrayList.get(i);
                        product.setId(id);

                        String user= product.getUser();
                        String userid= firebaseAuth.getUid();
                        if (user.equals(userid)){

                            productsArrayList.add(product);
                        }else {

                        }
                    }
                    System.out.println("list="+productsArrayList);
                    //       Toast.makeText(getContext(), productsArrayList+"", Toast.LENGTH_SHORT).show();
                    productAdapter.notifyDataSetChanged();



                }
                else if (task.isCanceled()){
                    Toast.makeText(getContext(), "faild", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return binding.getRoot();
    }
}
