package com.project.setech.activities.mainActivity.mainRecyclerView.viewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.setech.R;
import com.project.setech.model.IItem;
import com.project.setech.model.Item;

public class topItemViewHolder extends BaseMainViewHolder{
    public ViewGroup parent;

    public TextView itemName, itemPrice;
    public ImageView itemImage;


    public topItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.top_item_column, parent, false));
        this.parent = parent;

        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
        itemImage = itemView.findViewById(R.id.itemImage);
    }

    @Override
    public void bind(IItem item) {
        Item topItem = (Item) item;

        itemName.setText(topItem.getName());
        itemPrice.setText("$"+topItem.getPrice());
        itemImage.setImageResource(item.getImages().get(0));
    }
}
