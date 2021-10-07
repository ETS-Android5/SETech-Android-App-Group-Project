package com.project.setech.activities.listActivity.listRecyclerView.viewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.setech.R;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;

public class MotherboardViewHolder extends BaseItemViewHolder{
    public ViewGroup parent;


    public MotherboardViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.cpu_row_view, parent, false));
        this.parent = parent;

    }

    @Override
    public void bind(IItem item) {

    }
}
