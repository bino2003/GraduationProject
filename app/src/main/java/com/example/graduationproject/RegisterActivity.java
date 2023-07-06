package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.graduationproject.databinding.ActivityRegisterBinding;
import com.example.graduationproject.Model.ProductiveFamily;
import com.example.graduationproject.Model.users;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class RegisterActivity extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore;
    ActivityRegisterBinding binding;
    String name;
    String password;

    String phone;
    String rePassword;
    String categorize;
    String latitude;
    String longitude;
    FirebaseAuth firebaseAuth;
    boolean isuser;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    int  catid;
    String    cat;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBacke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


//        binding.gotosignin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        });
        sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        editor= sharedPreferences.edit();
        String sharedphone=sharedPreferences.getString("phoneaftermap","");
        String sharedpassword=sharedPreferences.getString("passwordaftermap","");
        String sharedrepassword=sharedPreferences.getString("repasswordaftermap","");
        String sharedname=sharedPreferences.getString("nameaftermap","");
        String sharedemail=sharedPreferences.getString("emailaftermap","");
        String sharedcat=sharedPreferences.getString("cataftermap","");
        if (sharedcat!=null){
            binding.radioGroup.check(catid);
        }
        if (sharedemail!=null){
            binding.EmailAddress.setText(sharedemail);
        }
        if (sharedname!=null){
            binding.fullname.setText(sharedname);
        }
        if (sharedpassword!=null){
            binding.Password.setText(sharedpassword);
        }
        if (sharedrepassword!=null){
            binding.ConfirmPassword.setText(sharedrepassword);
        }
        if (sharedphone!=null){
            binding.PhoneNumber.setText(sharedphone);
        }
        getIntent().getStringExtra("lat");
        getIntent().getStringExtra("long");
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.radioButton){
                    categorize="Productive family";
                    isuser=false;
                }else if (i==R.id.radioButton_users){
                    categorize="Users";
                    isuser=true;
                }
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        binding.location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,MapsActivity.class);
                String passwordmaps= binding.Password.getText().toString();
                String repasswordmaps=binding.ConfirmPassword.getText().toString();
                String phonemaps=binding.PhoneNumber.getText().toString();
                String emailmaps=binding.EmailAddress.getText().toString();
                String namemaps=binding.fullname.getText().toString();
                binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        if (i== R.id.radioButton){
                            cat="Productive family";
                            catid=i;
                            isuser=false;
                        }else if (i==R.id.radioButton_users){
                            cat="Users";
                            catid=i;
                            isuser=true;
                        }
                    }
                });
                if (namemaps!=null){
                    editor.putString("nameaftermap",namemaps);

                    editor.apply();


                }
                if (phonemaps!=null){

                    editor.putString("phoneaftermap",phonemaps);
                    editor.apply();


                }
                if (cat!=null){

                    editor.putString("cataftermap",cat);
                    editor.apply();

                }
                if (repasswordmaps!=null){


                    editor.putString("repasswordaftermap",repasswordmaps);
                    editor.apply();

                }
                if (emailmaps!=null){

                    editor.putString("emailaftermap",emailmaps);
                    editor.apply();

                }
                if (passwordmaps!=null){

                    editor.putString("passwordaftermap",passwordmaps);
                    editor.apply();
                }


                startActivity(intent);
                finish();
            }
        });


        //double location=

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=binding.fullname.getText().toString();
                password=binding.Password.getText().toString();
                phone=binding.PhoneNumber.getText().toString();
                rePassword=binding.ConfirmPassword.getText().toString();
                email=binding.EmailAddress.getText().toString();
                latitude= getIntent().getStringExtra("lat");
                longitude= getIntent().getStringExtra("long");
               if(name.isEmpty()||password.isEmpty()||rePassword.isEmpty()||phone.isEmpty()||categorize==null||longitude==null||email==null||latitude==null){

                    Toast.makeText(RegisterActivity.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
               }
               else if (!password.equals(rePassword)){
                    Toast.makeText(RegisterActivity.this, "No password match", Toast.LENGTH_SHORT).show();

              }

            else {
                    register();
                    editor.putString("name",binding.fullname.getText().toString());
                    editor.putInt("phone", Integer.parseInt(binding.PhoneNumber.getText().toString()));
                    editor.putString("latitude",latitude);
                   editor.putString("longitude",longitude);

                   editor.putString("category",categorize);
                    editor.apply();



             }

            }
        });


    }
    private void register(){


        firebaseAuth.createUserWithEmailAndPassword(binding.EmailAddress.getText().toString(),binding.Password.getText().toString()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    editor.remove("phoneaftermap");
                    editor.remove("passwordaftermap");
                    editor.remove("repasswordaftermap");
                    editor.remove("nameaftermap");
                    editor.remove("emailaftermap");
                    editor.remove("cataftermap");

editor.apply();
                    Toast.makeText(RegisterActivity.this, "Create User Success", Toast.LENGTH_SHORT).show();
                    Log.d("Register",task.getResult().getUser().toString());
                    setUsers();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                }else {
                    Log.d("Register",task.getException().getMessage());

                    Toast.makeText(RegisterActivity.this, "error"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        })
        ;
    }
    void setUsers(){
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();



        users users=new users();
        users.setName(name);

        users.setPhone(phone);

//        if (categorize!=null){
            users.setCategorize(categorize);
//        }
//        if (!location.isEmpty()){
            users.setLatitude(latitude);
            users.setLongitude(longitude);
            Toast.makeText(this, latitude+longitude, Toast.LENGTH_SHORT).show();
//        }
        ProductiveFamily productiveFamily=new ProductiveFamily();
        productiveFamily.setId(user.getUid());
      //  if (categorize!=null){
            productiveFamily.setCategory(categorize);
       // }
    //    if (!location.isEmpty()){
            productiveFamily.setLongitude(longitude);
            productiveFamily.setLatitude(latitude);
     //   }
        productiveFamily.setName(name);
        productiveFamily.setPhone(Integer.parseInt(phone));
        if (isuser){
            firebaseFirestore.collection("users").document(user.getUid()).set(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Account creation process successful ", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Account creation failed  ", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }else if (isuser==false){
            firebaseFirestore.collection("Productive family").document(user.getUid()).set(productiveFamily).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "Account creation process successful ", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegisterActivity.this, "Account creation failed  ", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }





    }


}