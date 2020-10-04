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
import com.example.grocery.activities.shopDetailsActivity;
import com.example.grocery.models.ModelBuyer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterBuyers extends RecyclerView.Adapter<AdapterBuyers.HolderBuyer>{

    private Context context;
    public ArrayList<ModelBuyer> buyerArrayList;

    public AdapterBuyers(Context context, ArrayList<ModelBuyer> buyerArrayList) {
        this.context = context;
        this.buyerArrayList = buyerArrayList;
    }

    @NonNull
    @Override
    public HolderBuyer onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_buyer, parent, false);
        return new HolderBuyer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderBuyer holder, int position) {

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
            Picasso.get().load(profileImage).placeholder(R.drawable.ic_baseline_shop).into(holder.buyerIv);
        }
        catch (Exception e){
            holder.buyerIv.setImageResource(R.drawable.ic_baseline_shop);
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
        return buyerArrayList.size();
    }

    class HolderBuyer extends RecyclerView.ViewHolder{

        private ImageView buyerIv;
        private TextView nameTv, phoneTv, addressTv;

        public HolderBuyer(@NonNull View itemView) {
            super(itemView);

            buyerIv = itemView.findViewById(R.id.buyerIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            phoneTv = itemView.findViewById(R.id.phoneTv);
            addressTv = itemView.findViewById(R.id.addressTv);
        }
    }
}
