package com.example.graduationproject.ProductiveFamilyProfileActivity;

import android.content.Intent;
import android.os.Bundle;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graduationproject.Interface.OnDelete;
import com.example.graduationproject.Interface.ProductsAction;

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


public class ItemProductiveFamily extends Fragment implements OnDelete {
    ArrayList<Product> productsallArrayList=new ArrayList<>();
    ArrayList<Product> productsArrayList=new ArrayList<>();
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

    private String dbname;

    public ItemProductiveFamily() {
        // Required empty public constructor
    }


    public static ItemProductiveFamily newInstance(String dbname) {
        ItemProductiveFamily fragment = new ItemProductiveFamily();
        Bundle args = new Bundle();

        args.putString(ARG_db_name, dbname);
        fragment.setArguments(args);
        return fragment;




    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbname = getArguments().getString(ARG_db_name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentItemProductiveFamilyBinding binding = FragmentItemProductiveFamilyBinding.inflate(inflater, container, false);
        firebaseFirestore=FirebaseFirestore.getInstance();

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

                    Toast.makeText(getContext(), productsArrayList+"", Toast.LENGTH_SHORT).show();
             productAdapter=new ProductAdapter(productsArrayList, getActivity(), new ProductsAction() {
                 @Override
                 public void OnDelete(String name, int pos) {

//                     DeleteDialogFragment deleteDialogFragment = DeleteDialogFragment.newInstance(productsArrayList.get(pos).getName(), pos);
//
//                     deleteDialogFragment.show(getParentFragmentManager(), "alertdialog");
                     Product product=productsArrayList.get(pos);
                     firebaseFirestore.collection("Products").document(product.getId()).delete();
                     Toast.makeText(getActivity(), "Product deleted", Toast.LENGTH_SHORT).show();

                     productsArrayList.remove(product);

                     productAdapter.notifyDataSetChanged();
                     productAdapter.notifyItemChanged(pos);
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
                     Toast.makeText(getActivity(), product.getId(), Toast.LENGTH_SHORT).show();
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

        return binding.getRoot();
    }


    @Override
    public void OnDelete(int pos) {
        Product product=productsArrayList.get(pos);
        firebaseFirestore.collection("Products").document(product.getId()).delete();
        Toast.makeText(getActivity(), "Product deleted", Toast.LENGTH_SHORT).show();

        productsArrayList.remove(product);

        productAdapter.notifyDataSetChanged();
        productAdapter.notifyItemChanged(pos);

    }
}
