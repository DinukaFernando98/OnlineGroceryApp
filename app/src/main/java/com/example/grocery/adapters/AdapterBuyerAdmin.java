package com.example.grocery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.activities.AdminBuyerDetailsActivity;
import com.example.grocery.activities.AdminShopDetailsActivity;
import com.example.grocery.models.ModelBuyer;
import com.example.grocery.models.ModelShop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterBuyerAdmin extends RecyclerView.Adapter<AdapterBuyerAdmin.HolderShop>{

    private Context context;
    public ArrayList<ModelBuyer> buyerArrayList;

    public AdapterBuyerAdmin(Context context, ArrayList<ModelBuyer> buyerArrayList) {
        this.context = context;
        this.buyerArrayList = buyerArrayList;
    }

    @NonNull
    @Override
    public HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_buyer, parent, false);
        return new HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderShop holder, int position) {

        ModelBuyer modelBuyer = buyerArrayList.get(position);
        final String uid = modelBuyer.getUid();
        String email = modelBuyer.getEmail();
        String name = modelBuyer.getName();
        String phoneNumber = modelBuyer.getPhoneNumber();
        String address = modelBuyer.getAddress();
        String timestamp = modelBuyer.getTimestamp();
        String profileImage = modelBuyer.getProfileImage();
        String accountType = modelBuyer.getAccountType();

        //set data
        holder.nameTv.setText(name);
        holder.phoneTv.setText(phoneNumber);
        holder.addressTv.setText(address);

        try {
            Picasso.get().load(profileImage).placeholder(R.drawable.ic_person_grey).into(holder.shopIv);
        }
        catch (Exception e){
            holder.shopIv.setImageResource(R.drawable.ic_person_grey);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdminBuyerDetailsActivity.class);
                intent.putExtra("Uid", uid);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class HolderShop extends RecyclerView.ViewHolder{

        private ImageView shopIv;
        private TextView nameTv, phoneTv, addressTv;

        public HolderShop(@NonNull View itemView) {
            super(itemView);

            shopIv = itemView.findViewById(R.id.shopIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
            addressTv = itemView.findViewById(R.id.addressTv);
        }
    }
}
