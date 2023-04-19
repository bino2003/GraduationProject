package com.example.graduationproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.graduationproject.databinding.ActivityRegisterBinding;
import com.example.graduationproject.model.users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RegisterActivity extends AppCompatActivity {
FirebaseFirestore firebaseFirestore;
ActivityRegisterBinding binding;
    String name;
    String password;

    String phone;
    String rePassword;
    String categorize;
    String location;
    FirebaseAuth firebaseAuth;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        sharedPreferences=getSharedPreferences("sp",MODE_PRIVATE);
        editor= sharedPreferences.edit();
        getIntent().getStringExtra("latlong");
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.radioButton){
                    categorize="Productive family";
                }else if (i==R.id.radioButton_users){
                    categorize="Users";
                }
            }
        });
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        binding.etLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(RegisterActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

       
        //double location=

        binding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=binding.etname.getText().toString();
                password=binding.etPassword.getText().toString();
                phone=binding.etPhone.getText().toString();
                rePassword=binding.etRePassword.getText().toString();
                location= getIntent().getStringExtra("latlong");
                if(name.isEmpty()||password.isEmpty()||rePassword.isEmpty()||phone.isEmpty()||categorize==null||location==null){

                    Toast.makeText(RegisterActivity.this, "All fields must be filled in", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(rePassword)){
                    Toast.makeText(RegisterActivity.this, "No password match", Toast.LENGTH_SHORT).show();

                }

                else {
                    register();
                    editor.putString("name",binding.etname.getText().toString());
                    editor.putInt("phone", Integer.parseInt(binding.etPhone.getText().toString()));
//                   editor.putString("latlong", binding.maps.getla().toString());
                    editor.apply();



                }

            }
        });


    }
    private void register(){


        firebaseAuth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(),binding.etPassword.getText().toString()).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Create User Success", Toast.LENGTH_SHORT).show();
                    Log.d("Register",task.getResult().getUser().toString());
                    setUsers();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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
            users.setPassword(password);
            users.setPhone(phone);
            users.setRePassword(rePassword);
            if (categorize!=null){
                users.setCategorize(categorize);
            }
         if (!location.isEmpty()){
               users.setLocation(location);
             Toast.makeText(this, location, Toast.LENGTH_SHORT).show();
            }
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
           
        }

    }
