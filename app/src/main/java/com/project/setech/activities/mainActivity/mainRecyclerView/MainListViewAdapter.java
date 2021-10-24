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
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.BaseItemViewHolder;
import com.project.setech.activities.mainActivity.MainActivity;
import com.project.setech.activities.mainActivity.mainRecyclerView.viewHolders.topItemViewHolder;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;
import com.project.setech.util.Animations.Animations;
import com.project.setech.util.Animations.IAnimations;
import com.project.setech.util.CategoryType;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is an adapter for MainActivity
 */
public class MainListViewAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<IItem> topItemsList;
    private CategoryType type;
    private IAnimations animations;

    /**
     * Constructor of MainListViewAdapter
     * @param context Variable that holds global information about an application environment
     * @param itemList list of items
     * @param type The category type of item
     */
    public MainListViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        this.context = context;
        this.topItemsList = itemList;
        this.type = type;
        animations = new Animations(context);
    }

    /**
     * This method creates a view holder for the Main Activity screen.
     * @param parent ViewGroup object that is to be populated
     * @param viewType type of view determined by a number
     * @return a RecyclerView containing its relevant information
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new topItemViewHolder(parent);
    }

    /**
     * This method casts the items depending on the view holder
     * @param holder RecyclerView view holder for the items to be cast onto
     * @param position number order of the items in the top items list
     */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseItemViewHolder) holder).bind(topItemsList.get(position));
        animations.setFadeAnimation(holder.itemView);
    }

    /**
     * This method gets the number of the items in the top items list.
     * @return number of items in the list
     */
    @Override
    public int getItemCount() {
        return topItemsList.size();
    }
}