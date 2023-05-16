package com.example.graduationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.graduationproject.databinding.ActivityEntertheProductFamilyInformationBinding;

public class EntertheProductFamilyInformation extends AppCompatActivity {
ActivityEntertheProductFamilyInformationBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEntertheProductFamilyInformationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}