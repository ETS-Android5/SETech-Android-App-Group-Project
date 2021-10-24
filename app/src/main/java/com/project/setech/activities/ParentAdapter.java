package com.project.setech.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.project.setech.R;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.BaseItemViewHolder;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.CPUViewHolder;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.GPUViewHolder;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.MotherboardViewHolder;
import com.project.setech.activities.searchActivity.searchRecyclerView.ALLViewHolder;
import com.project.setech.model.IItem;
import com.project.setech.util.Animations.Animations;
import com.project.setech.util.Animations.IAnimations;
import com.project.setech.util.CategoryType;
import com.project.setech.util.SearchBy.ISearchBy;
import com.project.setech.util.SearchBy.SearchBy;
import com.project.setech.util.SortBy.ISortBy;
import com.project.setech.util.SortBy.SortBy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class is extended by ListViewAdapter and SearchViewAdapter due to the similar layout and functionality they have
 */
public class ParentAdapter extends RecyclerView.Adapter implements Filterable {
    public Context context;
    public List<IItem> itemList;
    public CategoryType type;
    public List<IItem> itemListFull;
    public String order;
    public String clickedString;
    public IAnimations animations;
    public String adType;
    private ISearchBy searchBy;
    public ISortBy sortBy;

    /**
     * Constructor of ParentAdapter
     * @param context Variable that holds global information about an application environment
     * @param itemList list of items
     * @param type The category type of item
     */
    public ParentAdapter(Context context, List<IItem> itemList, CategoryType type) {
        this.context = context;
        this.itemList = itemList;
        this.type = type;
        itemListFull = new ArrayList<>(itemList);
        animations = new Animations(context);
        searchBy = new SearchBy();
        sortBy = new SortBy();
    }

    /**
     * This method is used to direct to different view holders based on the item category type
     * @param parent The base class for layouts and views containers
     * @param viewType Integer number that represents the view type
     * @return A viewHolder based on the category type
     */
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
            case ALL:
                return new ALLViewHolder(parent);
            default:
                throw new IllegalArgumentException("Unsupported category type passed into list view adapter!");
        }
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     * @param holder A viewHolder
     * @param position Integer number that represents the position in the list of items
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseItemViewHolder) holder).bind(itemList.get(position));

        animations.setFallDownAnimation(holder.itemView);
    }

    /**
     * Getter of number of items in the list
     * @return Number of items
     */
    @Override
    public int getItemCount() {
        return itemList.size();
    }

    /**
     * Getter of the Filter object
     * @return A Filter object
     */
    @Override
    public Filter getFilter() {
        return FilterItem;
    }

    /**
     * This method create a Filter object based on the comparator filtering functionality
     */
    private Filter FilterItem = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            //create FilteredResults based on tempList
            FilterResults results = new FilterResults();
            results.values = searchBy.filterBySearchText(itemListFull,charSequence.toString());
            return results;
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            //replace itemList with new filtered results
            itemList.clear();
            itemList.addAll((Collection<? extends IItem>) results.values);
            notifyDataSetChanged();

            //sort filtered results
            if(adType == "list") {
                sortByType(clickedString,order);
            }

            //Text to show no items are found
            TextView noItemsFoundText = (TextView) ((Activity) context).findViewById(R.id.noItemsFoundText);

            if (itemList.size() <= 0) {
                noItemsFoundText.setVisibility(View.VISIBLE);
            } else {
                noItemsFoundText.setVisibility(View.GONE);
            }
        }
    };

    public void sortByType(String sortType,String order) {
        if (sortType == null) {
            order = "increase";
            clickedString = "name";
            sortBy.sortByName(itemList,order);

            return;
        }

        if(sortType.equals("price")) {
            sortBy.sortByPrice(itemList,order);
        } else if(sortType.equals("name")) {
            sortBy.sortByName(itemList,order);
        } else if(sortType.equals("views")) {
            sortBy.sortByView(itemList,order);
        }
        else {
            order = "increase";
            clickedString = "name";
            sortBy.sortByName(itemList,order);
        }

        notifyDataSetChanged();
    }
}
