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

public class ALLViewHolder extends BaseItemViewHolder {
    public ViewGroup parent;

    public TextView itemName, itemPrice, itemCores, itemClockSpeed;
    public ImageView itemImage;


    public ALLViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_view, parent, false));
        this.parent = parent;

        itemName = itemView.findViewById(R.id.name);
        itemPrice = itemView.findViewById(R.id.price);
        itemImage = itemView.findViewById(R.id.image);
    }

    @Override
    public void bind(IItem item) {

        itemName.setText(item.getName());
        itemPrice.setText(item.getPrice());
        itemImage.setImageResource(item.getImages().get(0));
    }
}
