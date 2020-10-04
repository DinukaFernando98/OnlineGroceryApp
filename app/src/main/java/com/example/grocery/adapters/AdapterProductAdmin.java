package com.example.grocery.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.FilterProductUser;
import com.example.grocery.R;
import com.example.grocery.activities.AdminDeleteProductActivity;
import com.example.grocery.activities.AdminShopDetailsActivity;
import com.example.grocery.activities.EditProductActivity;
import com.example.grocery.activities.shopDetailsActivity;
import com.example.grocery.models.ModelProduct;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterProductAdmin extends RecyclerView.Adapter<AdapterProductAdmin.HolderProductUser> implements Filterable {

    private Context context;
    public ArrayList<ModelProduct> productsList, filterList;
    private FilterProductUser filter;
    private Object HashMap;
    private String shopUid;

    public AdapterProductAdmin(Context context, ArrayList<ModelProduct> productsList, String shopUid) {
        this.context = context;
        this.productsList = productsList;
        this.filterList = filterList;
        this.shopUid = shopUid;
        //this.pid = pid;
    }





    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_admin, parent, false);
        return new HolderProductUser(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductUser holder, int position) {

        final ModelProduct modelProduct = productsList.get(position);
        String productCategory = modelProduct.getProductCategory();
        String productDescription = modelProduct.getProductDescription();
        String productIcon = modelProduct.getProductIcon();
        String productQuantity = modelProduct.getProductQuantity();
        String timestamp = modelProduct.getTimestamp();
        String originalPrice = modelProduct.getOriginalPrice();
        String productTitle = modelProduct.getProductTitle();
        final String productId = modelProduct.getProductId();


       // holder.titleTv.setText(productTitle);
      //  holder.descriptionTv.setText(productDescription);
       // holder.originalPriceTv.setText("LKR"+originalPrice);

        try{
            Picasso.get().load(productIcon).placeholder(R.drawable.ic_baseline_add_shopping_cart_24).into(holder.productIconIv);
        }
        catch (Exception e){
            holder.productIconIv.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                showQuantityDialog(modelProduct);
            }
        });

        holder.addToCartTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //handle item clicks,show item details
                showQuantityDialog(modelProduct);

            }
        });



    }

    private double cost = 0;
    private double finalCost = 0;
    private int quantity = 0;

    private void showQuantityDialog(final ModelProduct modelProduct) {
        View view = LayoutInflater.from(context).inflate(R.layout.admin_dialog_quantity, null);

        ImageView productIv = view.findViewById(R.id.productIv);
        final TextView titleTv = view.findViewById(R.id.titleTv);
        TextView pQuantityTv = view.findViewById(R.id.pQuantityTv);
        TextView descriptionTv = view.findViewById(R.id.descriptionTv);
        final TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);
        final TextView finalPriceTv = view.findViewById(R.id.finalPriceTv);
        ImageButton decrementBtn = view.findViewById(R.id.decrementBtn);
        final TextView quantityTv = view.findViewById(R.id.quantityTv);
        ImageButton incrementBtn = view.findViewById(R.id.incrementBtn);
        Button deleteBtn = view.findViewById(R.id.deleteBtn);

        //get data from model
        final String productId = modelProduct.getProductId();
        String title = modelProduct.getProductTitle();
        String productQuantity = modelProduct.getProductQuantity();
        String description = modelProduct.getProductDescription();
        String image = modelProduct.getProductIcon();
        final String price = modelProduct.getOriginalPrice();

        cost = Double.parseDouble(price.replaceAll("LKR", ""));
        finalCost = Double.parseDouble(price.replaceAll("LKR", ""));
        quantity = 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);

        //set data
        try {
            Picasso.get().load(image).placeholder(R.drawable.ic_baseline_cart_grey).into(productIv);
        }
        catch (Exception e){
            productIv.setImageResource(R.drawable.ic_baseline_cart_grey);
        }


        titleTv.setText(""+title);
        pQuantityTv.setText(""+productQuantity);
        descriptionTv.setText(""+description);
        quantityTv.setText(""+quantity);
        originalPriceTv.setText("LKR"+modelProduct.getOriginalPrice());
        finalPriceTv.setText("LKR"+finalCost);

        final AlertDialog dialog = builder.create();
        dialog.show();


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteProduct(productId);
                Intent intent = new Intent(context, AdminDeleteProductActivity.class);
                intent.putExtra("productId", productId);
                context.startActivity(intent);
            }
        });
    }

    private void deleteProduct(String productId) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(shopUid).child("Products").child(productId).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context, "Product Deleted..", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter ==null){
            //filter = new FilterProductUser(this, filterList);
        }
        return filter;
    }

    class HolderProductUser extends RecyclerView.ViewHolder{

        private ImageView productIconIv;
        private TextView titleTv, descriptionTv, originalPriceTv, addToCartTv, deleteBtn;

        public HolderProductUser(@NonNull View itemView) {
            super(itemView);

            productIconIv = itemView.findViewById(R.id.productIconIv);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
            addToCartTv = itemView.findViewById(R.id.addToCartTv);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
