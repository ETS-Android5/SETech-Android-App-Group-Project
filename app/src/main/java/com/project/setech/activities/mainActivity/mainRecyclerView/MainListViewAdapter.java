package com.project.setech.activities.mainActivity.mainRecyclerView;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.setech.R;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.BaseItemViewHolder;
import com.project.setech.activities.mainActivity.mainRecyclerView.viewHolders.topItemViewHolder;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;
import com.project.setech.util.CategoryType;

import java.util.List;

public class MainListViewAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<IItem> itemList;
    private CategoryType type;

    public MainListViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        this.context = context;
        this.itemList = itemList;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new topItemViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseItemViewHolder) holder).bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }
}