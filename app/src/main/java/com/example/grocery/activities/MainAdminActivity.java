package com.example.grocery.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.grocery.R;
import com.example.grocery.adapters.AdapterProductSeller;
import com.example.grocery.adapters.AdapterShop;
import com.example.grocery.models.ModelProduct;
import com.example.grocery.models.ModelShop;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainAdminActivity extends AppCompatActivity {
    private TextView tabShopsTv, tabProductsTv, tabBuyersTv;
    private RelativeLayout shopsRl, productsRl, buyersRl;
    private ImageButton logoutBtn, editProfileBtn;
    private FirebaseAuth firebaseAuth;
    private RecyclerView shopsRv, ProductsRv, buyersRv;

    private ProgressDialog progressDialog;

    private ArrayList<ModelShop> shopsList;
    private AdapterShop adapterShop;

    private ArrayList<ModelProduct> productList;
    private AdapterProductSeller adapterProductSeller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        logoutBtn = findViewById(R.id.logoutBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        tabShopsTv = findViewById(R.id.tabShopsTv);
        tabProductsTv = findViewById(R.id.tabProductsTv);
        tabBuyersTv = findViewById(R.id.tabBuyersTv);
        shopsRv = findViewById(R.id.shopsRv);
        ProductsRv = findViewById(R.id.ProductsRv);
        buyersRv = findViewById(R.id.buyersRv);
        shopsRl = findViewById(R.id.shopsRl);
        productsRl = findViewById(R.id.productsRl);
        buyersRl = findViewById(R.id.buyersRl);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        showShopsUI();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open edit prof page
                startActivity(new Intent(MainAdminActivity.this, ProfileEditAdminActivity.class));
            }
        });

        tabShopsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShopsUI();
            }
        });

        tabProductsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProductsUI();
            }
        });

        tabBuyersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBuyersUI();
            }
        });
    }

    private void showShopsUI() {
        shopsRl.setVisibility(View.VISIBLE);
        productsRl.setVisibility(View.GONE);
        buyersRl.setVisibility(View.GONE);

        tabShopsTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabShopsTv.setBackgroundResource(R.drawable.shape_rect04);

        tabProductsTv.setTextColor(getResources().getColor(R.color.colorWhite));
        tabProductsTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabBuyersTv.setTextColor(getResources().getColor(R.color.colorWhite));
        tabBuyersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void  showProductsUI () {
        //show products ui and hide orders ui
        productsRl.setVisibility(View.VISIBLE);
        shopsRl.setVisibility(View.GONE);
        buyersRl.setVisibility(View.GONE);

        tabProductsTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabProductsTv.setBackgroundResource(R.drawable.shape_rect04);

        tabShopsTv.setTextColor(getResources().getColor(R.color.colorWhite));
        tabShopsTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabBuyersTv.setTextColor(getResources().getColor(R.color.colorWhite));
        tabBuyersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void  showBuyersUI () {
        //show products ui and hide orders ui
        productsRl.setVisibility(View.VISIBLE);
        shopsRl.setVisibility(View.GONE);
        productsRl.setVisibility(View.GONE);

        tabProductsTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabProductsTv.setBackgroundResource(R.drawable.shape_rect04);

        tabBuyersTv.setTextColor(getResources().getColor(R.color.colorWhite));
        tabBuyersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabBuyersTv.setTextColor(getResources().getColor(R.color.colorWhite));
        tabBuyersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null){
            startActivity(new Intent(MainAdminActivity.this, LoginActivity.class));
            finish();
        }
        else{
            loadMyInfo();
        }
    }

    private void loadMyInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            String name = ""+ds.child("name").getValue();
                            loadShops();
                            loadAllProducts();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }


    private void loadShops() {

        shopsList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("accountType").equalTo("Seller")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        shopsList.clear();
                        for (DataSnapshot ds: dataSnapshot.getChildren()){
                            ModelShop modelShop = ds.getValue(ModelShop.class);

                            shopsList.add(modelShop);
                        }
                        adapterShop = new AdapterShop(MainAdminActivity.this, shopsList);
                        shopsRv.setAdapter(adapterShop);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private void loadAllProducts() {
        productList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child("Users").child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);
                        }

                        adapterProductSeller = new AdapterProductSeller(MainAdminActivity.this, productList);

                        ProductsRv.setAdapter(adapterProductSeller);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}