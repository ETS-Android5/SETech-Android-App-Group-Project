package com.project.setech.activities.mainActivity.mainRecyclerView.viewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.project.setech.R;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.BaseItemViewHolder;
import com.project.setech.model.IItem;
import com.project.setech.model.Item;

/**
 * This class is a view holder class for the MainActivity screen.
 */
public class topItemViewHolder extends BaseItemViewHolder {
    public ViewGroup parent;

    public TextView itemName;
    public ImageView itemImage;

    /**
     * This method fetches the xml layout that populate the MainActivity screen.
     * @param parent ViewGroup object that is to be populated
     */
    public topItemViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.top_item_column, parent, false));
        this.parent = parent;

        itemName = itemView.findViewById(R.id.itemName);
        itemImage = itemView.findViewById(R.id.itemImage);
    }

    /**
     * This method casts the item and set the view properties; name and images.
     * @param item IItem object that the information is being retrieved from
     */
    @Override
    public void bind(IItem item) {
        Item topItem = (Item) item;

        itemName.setText(topItem.getName());
        itemImage.setImageResource(item.getImages().get(0));
    }
}
