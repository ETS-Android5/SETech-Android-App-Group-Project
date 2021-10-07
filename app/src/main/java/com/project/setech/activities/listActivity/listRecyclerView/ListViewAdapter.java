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
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;

import java.util.List;

public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHandler> {
    private Context context;
    private List<IItem> itemList;
    private Class<com.project.setech.model.itemType.CPU> type;

    public ListViewAdapter(Context context, List<IItem> itemList, Class<com.project.setech.model.itemType.CPU> type) {
        this.context = context;
        this.itemList = itemList;
        this.type = type;
    }

    @NonNull
    @Override
    public ViewHandler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create view depending on type but test CPU for now
        View view = LayoutInflater.from(context).inflate(R.layout.cpu_row_view, parent, false);

        return new ViewHandler(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHandler holder, int position) {
        CPU cpu = (CPU) itemList.get(position);

        holder.itemName.setText(cpu.getName());
        holder.itemPrice.setText(cpu.getPrice());
        holder.itemCores.setText(cpu.getNumCores());
        holder.itemClockSpeed.setText(cpu.getClockSpeed());
        holder.itemImage.setImageResource(cpu.getImages().get(0));
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
