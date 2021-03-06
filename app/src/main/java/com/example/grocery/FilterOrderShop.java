package com.example.grocery;
import android.widget.Filter;

import com.example.grocery.adapters.AdapterOrderShop;
import com.example.grocery.adapters.AdapterProductSeller;
import com.example.grocery.models.ModelOrderSeller;
import com.example.grocery.models.ModelProduct;

import java.util.ArrayList;

public class FilterOrderShop extends Filter {

    private AdapterOrderShop adapter;
    private ArrayList<ModelOrderSeller> filterList;

    public FilterOrderShop(AdapterOrderShop adapter, ArrayList<ModelOrderSeller> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        if (constraint != null && constraint.length()>0) {
            //search filed not empty, searching something, perform search

            //change to upper case,to make case insensitive
            constraint = constraint.toString().toUpperCase();

            //store our filtered list
            ArrayList<ModelOrderSeller> filteredModels = new ArrayList<>();
            for (int i = 0; i < filterList.size(); i++) {
                //check,search by title and category
                if (filterList.get(i).getOrderStatus().toUpperCase().contains(constraint)){
                    //add filtered data to list
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;
        }
        else{
            //search filed empty, not searching, return original/all/complete list
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults results) {
        adapter.orderSellerArrayList = (ArrayList<ModelOrderSeller>) results.values;
        adapter.notifyDataSetChanged();
    }
}
