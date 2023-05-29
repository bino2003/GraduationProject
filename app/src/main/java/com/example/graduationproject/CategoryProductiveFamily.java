package com.example.graduationproject;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import com.example.graduationproject.Adapters.CategoryProductFamilyAdapter;
import com.example.graduationproject.DetailsProductiveFamilyActivity.DetailsProductiveFamily;
import com.example.graduationproject.Interface.OnClickProductiveFamily;

import com.example.graduationproject.databinding.ActivityCategoryProductiveFamilyBinding;
import com.example.graduationproject.Model.ProductiveFamily;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class CategoryProductiveFamily extends AppCompatActivity {
    ActivityCategoryProductiveFamilyBinding binding;
    FirebaseFirestore firebaseFirestore;

    ArrayList<ProductiveFamily>     productiveFamilyArrayList=new ArrayList<>();
    ArrayList<ProductiveFamily> productiveFamilyArrayListCat=new ArrayList<>();
    String cat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCategoryProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();
        cat=getIntent().getStringExtra("ctegoryname");


        binding.tvCategoryName.setText(cat);

        getproductivefamily();

    }
    void getproductivefamily(){
        firebaseFirestore.collection("Productive family").whereEqualTo("productCategory",cat).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    productiveFamilyArrayList = (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);
                    binding.progressBar2.setVisibility(View.GONE);
                    if (productiveFamilyArrayList.isEmpty()){
                        startActivity(new Intent(getApplicationContext(),HandleEmpityActivity.class));
                        finish();
                        binding.progressBar2.setVisibility(View.GONE);
                    }
                    CategoryProductFamilyAdapter categoryProductFamilyAdapter =new CategoryProductFamilyAdapter(CategoryProductiveFamily.this, new OnClickProductiveFamily() {
                        @Override
                        public void onclickproductiveFamily(ProductiveFamily productiveFamily) {
                            if (productiveFamily.getId()!=null){
                                Intent intent=new Intent(getApplicationContext(), DetailsProductiveFamily.class);

                                intent.putExtra("idproductivefamily",productiveFamily.getId());
                                startActivity(intent);
                            }


                        }
                    },productiveFamilyArrayList);

                    binding.rv.setAdapter(categoryProductFamilyAdapter);

                    binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
                    categoryProductFamilyAdapter.notifyDataSetChanged();
                }     else if (task.isCanceled()){
                    Toast.makeText(CategoryProductiveFamily.this, "faild", Toast.LENGTH_SHORT).show();
                }
            }
        });







    }
}