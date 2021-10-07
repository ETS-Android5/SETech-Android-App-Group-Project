package com.project.setech.activities.listActivity.listRecyclerView.viewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.setech.model.IItem;

public abstract class BaseItemViewHolder extends RecyclerView.ViewHolder {

    public BaseItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(IItem item);
}
