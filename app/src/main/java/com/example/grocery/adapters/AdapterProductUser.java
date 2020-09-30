package com.example.grocery.adapters;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.grocery.activities.shopDetailsActivity;
import com.example.grocery.models.ModelProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterProductUser extends RecyclerView.Adapter<AdapterProductUser.HolderProductUser> implements Filterable {

    private Context context;
    public ArrayList<ModelProduct> productsList, filterList;
    private FilterProductUser filter;
    private Object HashMap;

    public AdapterProductUser(Context context, ArrayList<ModelProduct> productsList) {
        this.context = context;
        this.productsList = productsList;
        this.filterList = filterList;
    }





    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_user, parent, false);
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


        holder.titleTv.setText(productTitle);
        holder.descriptionTv.setText(productDescription);
        holder.originalPriceTv.setText("LKR"+originalPrice);

        try{
            Picasso.get().load(productIcon).placeholder(R.drawable.ic_baseline_add_shopping_cart_24).into(holder.productIconIv);
        }
        catch (Exception e){
            holder.productIconIv.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);
        }
        holder.addToCartTv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //handle item clicks,show item details
                 showQuantityDialog(modelProduct);

            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //handle item clicks,show item details
            }
        });


    }

    private double cost = 0;
    private double finalCost = 0;
    private int quantity = 0;

    private void showQuantityDialog(final ModelProduct modelProduct) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_quantity, null);

            ImageView productIv = view.findViewById(R.id.productIv);
            final TextView titleTv = view.findViewById(R.id.titleTv);
            TextView pQuantityTv = view.findViewById(R.id.pQuantityTv);
            TextView descriptionTv = view.findViewById(R.id.descriptionTv);
            final TextView originalPriceTv = view.findViewById(R.id.originalPriceTv);
            final TextView finalPriceTv = view.findViewById(R.id.finalPriceTv);
            ImageButton decrementBtn = view.findViewById(R.id.decrementBtn);
            final TextView quantityTv = view.findViewById(R.id.quantityTv);
            ImageButton incrementBtn = view.findViewById(R.id.incrementBtn);
            Button continueBtn = view.findViewById(R.id.continueBtn);

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

        incrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finalCost = finalCost + cost;
                quantity++;

                finalPriceTv.setText("LKR"+finalCost);
                quantityTv.setText(""+quantity);
            }
        });

        decrementBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity>1){
                    finalCost = finalCost - cost;
                    quantity--;

                    finalPriceTv.setText("LKR"+finalCost);
                    quantityTv.setText(""+quantity);

                }
            }
        });

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleTv.getText().toString().trim();
                String priceEach = price;
                String totalPrice = finalPriceTv.getText().toString().trim().replace("LKR", "");
                String quantity = quantityTv.getText().toString().trim();

                //add to sql db
               addToCart(productId, title, priceEach, totalPrice, quantity);
                dialog.dismiss();
            }
        });
    }

    private int itemId = 1;
    private void addToCart(String productId, String title, String priceEach, String price, String quantity) {
        itemId++;

        EasyDB easyDB = EasyDB.init(context, "ITEMS_DB")
                .setTableName("ITEMS_TABLE")
                .addColumn(new Column("Item_Id", new String[]{"text","unique"}))
                .addColumn(new Column("Item_PID", new String[]{"text","not null"}))
                .addColumn(new Column("Item_Name", new String[]{"text","not null"}))
                .addColumn(new Column("Item_Price_Each", new String[]{"text","not null"}))
                .addColumn(new Column("Item_Price", new String[]{"text","not null"}))
                .addColumn(new Column("Item_Quantity", new String[]{"text","not null"}))
                .doneTableColumn();

            Boolean b = easyDB.addData("Item_Id", itemId)
                    .addData("Item_PID", productId)
                    .addData("Item_Name", title)
                    .addData("Item_Price_Each", priceEach)
                    .addData("Item_Price", price)
                    .addData("Item_Quantity", quantity)
                    .doneDataAdding();

        Toast.makeText(context, "Added to cart ...", Toast.LENGTH_SHORT).show();

        //update cart Items
        ((shopDetailsActivity)context).cartCount();
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter ==null){
            filter = new FilterProductUser(this, filterList);
        }
        return filter;
    }

    class HolderProductUser extends RecyclerView.ViewHolder{

        private ImageView productIconIv;
        private TextView titleTv, descriptionTv, originalPriceTv, addToCartTv;

        public HolderProductUser(@NonNull View itemView) {
            super(itemView);

            productIconIv = itemView.findViewById(R.id.productIconIv);
            titleTv = itemView.findViewById(R.id.titleTv);
            descriptionTv = itemView.findViewById(R.id.descriptionTv);
            originalPriceTv = itemView.findViewById(R.id.originalPriceTv);
            addToCartTv = itemView.findViewById(R.id.addToCartTv);
        }
    }
}
