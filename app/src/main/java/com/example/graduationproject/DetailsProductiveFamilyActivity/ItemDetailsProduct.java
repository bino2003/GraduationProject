package com.example.graduationproject.DetailsProductiveFamilyActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.graduationproject.Interface.DetailsProductAction;


import com.example.graduationproject.databinding.FragmentItemDetailsProductBinding;

import com.example.graduationproject.model.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ItemDetailsProduct extends Fragment {
boolean isfav=true;

    ArrayList<Product> productsArrayList = new ArrayList<>();
    ItemDetailsProductAdapter itemDetailsProductAdapter;
    static int allPrice = 0;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    static int i = 0;
    FirebaseFirestore db;
    Product products;


    private static final String ARG_db_name2 = "dbName2";
    private static final String ARG_ID_ProductiveFamily = "id";


    private String dbname;
    private String id;

    public ItemDetailsProduct() {
        // Required empty public constructor
    }


    public static ItemDetailsProduct newInstance(String dbname, String id) {
        ItemDetailsProduct fragment = new ItemDetailsProduct();
        Bundle args = new Bundle();

        args.putString(ARG_db_name2, dbname);
        args.putString(ARG_ID_ProductiveFamily, id);
        fragment.setArguments(args);
        return fragment;


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dbname = getArguments().getString(ARG_db_name2);
            id = getArguments().getString(ARG_ID_ProductiveFamily);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentItemDetailsProductBinding binding = FragmentItemDetailsProductBinding.inflate(inflater, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(dbname).whereEqualTo("user",id).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
if (task.isSuccessful()){



    firebaseFirestore=FirebaseFirestore.getInstance();
    binding.progressBar.setVisibility(View.GONE);
    productsArrayList= (ArrayList<Product>) task.getResult().toObjects(Product.class);
    for (int i=0 ;i<productsArrayList.size();i++){
        String id= task.getResult().getDocuments().get(i).getId();
        Product product=productsArrayList.get(i);
        product.setId(id);
    }
 itemDetailsProductAdapter=new ItemDetailsProductAdapter(productsArrayList, getActivity(), new DetailsProductAction() {
     @Override
     public void onfav(Product product) {
         if (isfav){
             isfav=false;
             Toast.makeText(getActivity(), isfav+"", Toast.LENGTH_SHORT).show();
             firebaseFirestore.collection("Favorites").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                 @Override
                 public void onComplete(@NonNull Task<QuerySnapshot> task) {
                     if (task.isSuccessful()){
                         List<Product> favorite=     task.getResult().toObjects(Product.class);
                         for (int i=0;i<favorite.size();i++){
                             String favid=  task.getResult().getDocuments().get(i).getId();
                             product.setFavid(favid);
                         }
                     }


                 }
             });

             firebaseFirestore.collection("Favorites").document().set(product).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {

                     if (task.isSuccessful()){

                         Toast.makeText(getActivity(), "Product added to favorites", Toast.LENGTH_SHORT).show();
                     }
                 }
             });
         }else if (isfav==false){
             isfav=true;
             if (product.getFavid()!=null){
                 firebaseFirestore.collection("Favorites").document(product.getFavid()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         if (task.isSuccessful()){

                             Toast.makeText(getActivity(), product.getFavid(), Toast.LENGTH_SHORT).show();
                             Toast.makeText(getActivity(), "The product has been removed from favorites successfully", Toast.LENGTH_SHORT).show();

                         }else {
                             Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                         }
                     }
                 });
             }


         }
     }

     @Override
     public void onClickItem(Product product) {
Intent intent=new Intent(getActivity(),ViewDetailsProducts.class);

intent.putExtra("detailsproductid",product.getId());
startActivity(intent);
     }
 });
         binding.rv.setAdapter(itemDetailsProductAdapter);

    binding.rv.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
    itemDetailsProductAdapter.notifyDataSetChanged();

}

            }
        });


        return binding.getRoot();
    }
}