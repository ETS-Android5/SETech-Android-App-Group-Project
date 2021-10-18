package com.project.setech.activities.listActivity.listRecyclerView.viewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.setech.model.IItem;

public class BaseItemViewHolder extends RecyclerView.ViewHolder {

    public View itemView;

    public BaseItemViewHolder(@NonNull View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(IItem item){

    };
}
