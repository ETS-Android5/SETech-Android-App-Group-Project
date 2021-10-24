package com.project.setech.activities.listActivity.listRecyclerView.viewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.setech.model.IItem;

/**
 * Parent class for viewh olders used in list and search Activity
 */
public class BaseItemViewHolder extends RecyclerView.ViewHolder {

    public View itemView;

    public BaseItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    /**
     * Used to bind an item to an itemView by casting the item depending on
     * the view holder
     * @param item
     */
    public void bind(IItem item){

    };
}
