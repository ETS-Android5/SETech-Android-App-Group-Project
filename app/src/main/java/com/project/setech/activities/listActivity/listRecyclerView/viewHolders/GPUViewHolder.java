package com.project.setech.activities.listActivity.listRecyclerView.viewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.project.setech.R;
import com.project.setech.model.IItem;

public class GPUViewHolder extends BaseItemViewHolder{
    public ViewGroup parent;


    public GPUViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.cpu_row_view, parent, false));
        this.parent = parent;

    }

    @Override
    public void bind(IItem item) {

    }
}
