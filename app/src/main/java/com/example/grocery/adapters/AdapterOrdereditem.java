package com.example.grocery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocery.R;
import com.example.grocery.models.ModelCartItem;
import com.example.grocery.models.ModelOrdereditem;

import java.util.ArrayList;

public class AdapterOrdereditem extends RecyclerView.Adapter<AdapterOrdereditem.HolderOrderedItem>{

    private Context context;
    private ArrayList<ModelOrdereditem> ordereditemArrayList;

    public AdapterOrdereditem(Context context, ArrayList<ModelOrdereditem> ordereditemArrayList) {
        this.context = context;
        this.ordereditemArrayList = ordereditemArrayList;
    }

    @NonNull
    @Override
    public HolderOrderedItem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_ordereditem, parent, false);
        return new HolderOrderedItem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderOrderedItem holder, int position) {

        //get data at position
        ModelOrdereditem modelOrdereditem = ordereditemArrayList.get(position);
        String getPid = modelOrdereditem.getpId();
        String name = modelOrdereditem.getpName();
        String cost = modelOrdereditem.getCost();
        String price = modelOrdereditem.getPrice();
        String quantity = modelOrdereditem.getQuantity();

        //set data
        //holder.itemTitleTv.setText("Item Name:"+name);
        holder.itemPriceEachTv.setText("LKR"+price);
        holder.itemPriceTv.setText("LKR"+cost);
        holder.itemQuantityTv.setText("[" + quantity + "]");
    }

    @Override
    public int getItemCount() {
        return ordereditemArrayList.size();
    }

    class HolderOrderedItem extends RecyclerView.ViewHolder{

        private TextView itemTitleTv, itemPriceTv, itemPriceEachTv, itemQuantityTv;

        public HolderOrderedItem(@NonNull View itemView) {
            super(itemView);

            itemTitleTv = itemView.findViewById(R.id.itemTitleTv);
            itemPriceTv = itemView.findViewById(R.id.itemPriceTv);
            itemPriceEachTv = itemView.findViewById(R.id.itemPriceEachTv);
            itemQuantityTv = itemView.findViewById(R.id.itemQuantityTv);
        }
    }
}
