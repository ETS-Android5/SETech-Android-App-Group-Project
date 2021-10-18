package com.project.setech.activities.searchActivity.searchRecyclerView;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class SearchViewAdapter extends RecyclerView.Adapter implements Filterable{
    private Context context;
    private List<IItem> itemList;
    private CategoryType type;
    private List<IItem> itemListFull;
    private String order;

    public SearchViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
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
            case ALL:
                return new ALLViewHolder(parent);
            default:
                throw new IllegalArgumentException("Unsupported category type passed into list view adapter!");
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseItemViewHolder) holder).bind(itemList.get(position));

        animateView(holder.itemView);
    }

    private void animateView(View view) {
        Animation slideDown = AnimationUtils.loadAnimation(context, R.anim.item_animation_fall_down);
        view.startAnimation(slideDown);
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

            TextView noItemsFoundText = (TextView) ((Activity) context).findViewById(R.id.noItemsFoundText);

            if (itemList.size() <= 0) {
                noItemsFoundText.setVisibility(View.VISIBLE);
            }
            else {
                noItemsFoundText.setVisibility(View.GONE);
            }
        }
    };

    Comparator<IItem> compareByPrice = new Comparator<IItem>() {
        @Override
        public int compare(IItem o1, IItem o2) {
            return Float.compare(Float.parseFloat(o1.getPrice()), (Float.parseFloat(o2.getPrice())));
        }
    };

    Comparator<IItem> compareByName = new Comparator<IItem>() {
        @Override
        public int compare(IItem o1, IItem o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    Comparator<IItem> compareByView = new Comparator<IItem>() {
        @Override
        public int compare(IItem o1, IItem o2) {
            return Integer.parseInt(o1.getViewCount()) - Integer.parseInt(o2.getViewCount());
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortName(String s) {
        order = s;
        if(order == "increase"){
            Collections.sort(itemList, compareByName);
        } else {
            Collections.sort(itemList, compareByName.reversed());
        }
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortPrice(String s) {
        order = s;
        if(order == "increase"){
            Collections.sort(itemList, compareByPrice);
        } else {
            Collections.sort(itemList, compareByPrice.reversed());
        }
        notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sortView(String s) {
        order = s;
        if(order == "increase"){
            Collections.sort(itemList, compareByView);
        } else {
            Collections.sort(itemList, compareByView.reversed());
        }
        notifyDataSetChanged();
    }
}
