package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.graduationproject.databinding.ActivityCategoryProductiveFamilyBinding;
import com.example.graduationproject.model.ProductiveFamily;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryProductiveFamily extends AppCompatActivity {
ActivityCategoryProductiveFamilyBinding binding;
FirebaseFirestore firebaseFirestore;

    ArrayList<ProductiveFamily>     productiveFamilyArrayList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCategoryProductiveFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        firebaseFirestore=FirebaseFirestore.getInstance();


        //ArrayList<ProductiveFamily> productiveFamilyArrayListCat=new ArrayList<>();

getproductivefamily();

    }
    void getproductivefamily(){
        firebaseFirestore.collection("Productive family").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
           if (task.isSuccessful()){
            productiveFamilyArrayList= (ArrayList<ProductiveFamily>) task.getResult().toObjects(ProductiveFamily.class);


               Toast.makeText(CategoryProductiveFamily.this, productiveFamilyArrayList+"", Toast.LENGTH_SHORT).show();
               CategoryProductFamilyAdapter  categoryProductFamilyAdapter =new CategoryProductFamilyAdapter(CategoryProductiveFamily.this, new ListenerOnClickItem() {
                   @Override
                   public void OnClickItem(String name) {
                       Intent intent = new Intent(getApplicationContext(), DetailsProductiveFamily.class);
                       startActivity(intent);
                   }
               }, productiveFamilyArrayList);
               binding.rv.setAdapter(categoryProductFamilyAdapter);

               binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
categoryProductFamilyAdapter.notifyDataSetChanged();

           }
          else if (task.isCanceled()){
               Toast.makeText(CategoryProductiveFamily.this, "faild", Toast.LENGTH_SHORT).show();
           }

            }
        });
    }
}