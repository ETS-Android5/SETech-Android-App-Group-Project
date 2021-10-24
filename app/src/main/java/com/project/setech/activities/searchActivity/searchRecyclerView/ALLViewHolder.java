package com.project.setech.activities.searchActivity.searchRecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.project.setech.R;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.BaseItemViewHolder;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;

/**
 * This class is a view holder class for the SearchActivity screen.
 */
public class ALLViewHolder extends BaseItemViewHolder {
    public ViewGroup parent;

    public TextView itemName, itemPrice, itemCores, itemClockSpeed;
    public ImageView itemImage;


    /**
     * The constructor of the ALLViewHolder class
     * @param parent ViewGroup object that is to be populated
     */
    public ALLViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.gpu_row_view, parent, false));
        this.parent = parent;

        itemName = itemView.findViewById(R.id.gpuName);
        itemPrice = itemView.findViewById(R.id.detailsItemPrice);
        itemImage = itemView.findViewById(R.id.gpuImage);
    }

    /**
     * This method casts the item and set the view properties; name and images.
     * @param item IItem object that the information is being retrieved from
     */
    @Override
    public void bind(IItem item) {

        itemName.setText(item.getName());
        itemPrice.setText(item.getPrice());
        itemImage.setImageResource(item.getImages().get(0));
    }
}
