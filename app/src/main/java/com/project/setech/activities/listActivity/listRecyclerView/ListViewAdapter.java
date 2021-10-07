package com.project.setech.activities.listActivity.listRecyclerView;

import android.content.Context;
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
import com.project.setech.activities.listActivity.listRecyclerView.viewHolders.CPUViewHolder;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;
import com.project.setech.util.CategoryType;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter{
    private Context context;
    private List<IItem> itemList;
    private CategoryType type;

    public ListViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        this.context = context;
        this.itemList = itemList;
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view depending on type but test CPU for now
        return new CPUViewHolder(parent);
//        View view = LayoutInflater.from(context).inflate(R.layout.cpu_row_view, parent, false);
//
//        return new ViewHandler(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((BaseItemViewHolder) holder).bind(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHandler extends RecyclerView.ViewHolder {
        public TextView itemName, itemPrice, itemCores, itemClockSpeed;
        public ImageView itemImage;

        public ViewHandler(@NonNull View itemView, Context ctx) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemCores = itemView.findViewById(R.id.itemCores);
            itemClockSpeed = itemView.findViewById(R.id.itemClockSpeed);

            itemImage = itemView.findViewById(R.id.itemImage);
        }
    }
}
