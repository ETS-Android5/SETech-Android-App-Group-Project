package com.project.setech.activities.listActivity.listRecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.setech.R;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.BaseItemViewHolder;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.CPUViewHolder;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.GPUViewHolder;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.MotherboardViewHolder;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;
import com.project.setech.util.CategoryType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

public class ListViewAdapter extends RecyclerView.Adapter implements Filterable {
    private Context context;
    private List<IItem> itemList;
    private CategoryType type;
    private List<IItem> itemListFull;

    public ListViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        this.context = context;
        this.itemList = itemList;
        this.type = type;
        itemListFull = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view depending on type but test CPU for now
        switch (type) {
            case CPU:
                return new CPUViewHolder(parent);
            case GPU:
                return new GPUViewHolder(parent);
            case Motherboard:
                return new MotherboardViewHolder(parent);
            default:
                throw new IllegalArgumentException("Unsupported category type passed into list view adapter!");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseItemViewHolder) holder).bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {
        return FilterItem;
    }

    private Filter FilterItem = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            String searchText=charSequence.toString().toLowerCase().trim();
            List<IItem>tempList=new ArrayList<>();
            if(searchText.length()==0 || searchText.isEmpty()){
                tempList.addAll(itemListFull);
            } else {
                for(IItem item : itemListFull) {
                    if(item.getName().toLowerCase().trim().contains(searchText)) {
                        tempList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values=tempList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            itemList.clear();
            itemList.addAll((Collection<? extends IItem>) results.values);
            notifyDataSetChanged();
        }
    };
}
