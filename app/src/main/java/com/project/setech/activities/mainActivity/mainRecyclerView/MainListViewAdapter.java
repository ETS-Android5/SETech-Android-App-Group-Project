package com.project.setech.activities.mainActivity.mainRecyclerView;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.project.setech.R;
import com.project.setech.activities.Animations;
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.BaseItemViewHolder;
import com.project.setech.activities.mainActivity.MainActivity;
import com.project.setech.activities.mainActivity.mainRecyclerView.viewHolders.topItemViewHolder;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;
import com.project.setech.util.CategoryType;

import java.util.ArrayList;
import java.util.List;

public class MainListViewAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<IItem> topItemsList;
    private CategoryType type;


    public MainListViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        this.context = context;
        this.topItemsList = itemList;
        this.type = type;
    }
    Animations animations = new Animations(context);
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new topItemViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseItemViewHolder) holder).bind(topItemsList.get(position));
        animations.setFadeAnimation(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return topItemsList.size();
    }
}