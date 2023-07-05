package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.graduationproject.databinding.ActivityEntertheProductFamilyInformationBinding;
import com.example.graduationproject.databinding.ActivityUsersProfileBinding;

public class EntertheProductFamilyInformation extends AppCompatActivity {
ActivityEntertheProductFamilyInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEntertheProductFamilyInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}