package com.project.setech.activities.mainActivity.mainRecyclerView.viewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.setech.model.IItem;

public abstract class BaseMainViewHolder extends RecyclerView.ViewHolder {

    public BaseMainViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public abstract void bind(IItem item);
}