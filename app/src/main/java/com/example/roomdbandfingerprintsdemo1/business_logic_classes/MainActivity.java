package com.example.roomdbandfingerprintsdemo1.business_logic_classes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.roomdbandfingerprintsdemo1.R;
import com.example.roomdbandfingerprintsdemo1.databinding.ActivityMainBinding;
import com.example.roomdbandfingerprintsdemo1.mvvm.UserViewModel;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static UserViewModel userViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUsers()
    }
}