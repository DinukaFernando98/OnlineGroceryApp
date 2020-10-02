package com.example.grocery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.grocery.activities.RegisterAdminActivity;
import com.example.grocery.activities.RegisterBuyerActivity;
import com.example.grocery.activities.RegisterSellerActivity;

public class Main_register extends AppCompatActivity {

    private TextView registerBuyer, registerSeller, registerAdmin;
    private Button loginBtn, backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);

        registerBuyer = findViewById(R.id.registerBuyer);
        registerSeller = findViewById(R.id.registerSeller);
        registerAdmin = findViewById(R.id.registerAdmin);
        backBtn = findViewById(R.id.backBtn);

        registerBuyer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_register.this, RegisterBuyerActivity.class));
            }
        });

        registerSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_register.this, RegisterSellerActivity.class));
            }
        });

        registerAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Main_register.this, RegisterAdminActivity.class));
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}