package com.example.grocery.adapters;

import android.content.Context;
import android.content.Intent;
import android.icu.text.Transliterator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.activities.shopDetailsActivity;
import com.example.grocery.models.ModelShop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.HolderShop>{

    private Context context;
    public ArrayList<ModelShop> shopList;

    public AdapterShop(Context context, ArrayList<ModelShop> shopList) {
        this.context = context;
        this.shopList = shopList;
    }

    @NonNull
    @Override
    public HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_shop, parent, false);
        return new HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderShop holder, int position) {

        ModelShop modelShop = shopList.get(position);
        final String uid = modelShop.getUid();
        String email = modelShop.getEmail();
        String name = modelShop.getName();
        String shopName = modelShop.getShopName();
        String phoneNumber = modelShop.getPhoneNumber();
        String address = modelShop.getAddress();
        String deliveryFee = modelShop.getDeliveryFee();
        String timestamp = modelShop.getTimestamp();
        String profileImage = modelShop.getProfileImage();
        String accountType = modelShop.getAccountType();

        //set data
        holder.shopNameTv.setText(shopName);
        holder.phoneTv.setText(phoneNumber);
        holder.addressTv.setText(address);

        try {
            Picasso.get().load(profileImage).placeholder(R.drawable.ic_baseline_shop).into(holder.shopIv);
        }
        catch (Exception e){
            holder.shopIv.setImageResource(R.drawable.ic_baseline_shop);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, shopDetailsActivity.class);
                intent.putExtra("shopUid", uid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class HolderShop extends RecyclerView.ViewHolder{

        private ImageView shopIv;
        private TextView shopNameTv, phoneTv, addressTv;

        public HolderShop(@NonNull View itemView) {
            super(itemView);

            shopIv = itemView.findViewById(R.id.shopIv);
            shopNameTv = itemView.findViewById(R.id.shopNameTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
            addressTv = itemView.findViewById(R.id.addressTv);
        }
    }
}
