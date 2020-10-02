package com.example.grocery.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.grocery.R;
import com.example.grocery.adapters.AdapterBuyerAdmin;
import com.example.grocery.adapters.AdapterProductSeller;
import com.example.grocery.adapters.AdapterProductUser;
import com.example.grocery.adapters.AdapterShop;
import com.example.grocery.adapters.AdapterShopAdmin;
import com.example.grocery.models.ModelBuyer;
import com.example.grocery.models.ModelProduct;
import com.example.grocery.models.ModelShop;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainAdminActivity extends AppCompatActivity {
    private TextView tabShopsTv, nameTv;
    private RelativeLayout shopsRl;
    private ImageButton logoutBtn, editProfileBtn;
    private FirebaseAuth firebaseAuth;
    private RecyclerView shopsRv;
    private ImageView profileIv;

    private ProgressDialog progressDialog;

    private ArrayList<ModelShop> shopsList;
    private AdapterShopAdmin adapterShopAdmin;

    private ArrayList<ModelBuyer> buyerArrayList;
    private AdapterBuyerAdmin adapterBuyerAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        logoutBtn = findViewById(R.id.logoutBtn);
        editProfileBtn = findViewById(R.id.editProfileBtn);
        tabShopsTv = findViewById(R.id.tabShopsTv);
        shopsRv = findViewById(R.id.shopsRv);
        //ProductsRv = findViewById(R.id.ProductsRv);
        shopsRl = findViewById(R.id.shopsRl);
        profileIv = findViewById(R.id.profileIv);
        nameTv = findViewById(R.id.nameTv);




        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();
        showShopsUI();
        //loadAllProducts();


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


    }

    private void showShopsUI() {
        //show all shops
        shopsRl.setVisibility(View.VISIBLE);


        tabShopsTv.setTextColor(getResources().getColor(R.color.colorBlack));
        tabShopsTv.setBackgroundResource(R.drawable.shape_rect04);


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
                            String profileImage = ""+ds.child("profileImage").getValue();

                            nameTv.setText(name);

                            try {
                                Picasso.get().load(profileImage).placeholder(R.drawable.ic_baseline_add_shopping_cart_24).into(profileIv);
                            }catch (Exception e){
                                profileIv.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
                            }

                            loadShops();
                            //loadAllProducts();

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
                        adapterShopAdmin = new AdapterShopAdmin(MainAdminActivity.this, shopsList);
                        shopsRv.setAdapter(adapterShopAdmin);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

}