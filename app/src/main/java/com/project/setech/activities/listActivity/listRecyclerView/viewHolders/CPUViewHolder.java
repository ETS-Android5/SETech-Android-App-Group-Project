package com.project.setech.activities.listActivity.listRecyclerView.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.project.setech.R;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.CPU;

public class CPUViewHolder extends BaseItemViewHolder{
    public ViewGroup parent;

    public TextView itemName, itemPrice, itemCores, itemClockSpeed;
    public ImageView itemImage;


    public CPUViewHolder(ViewGroup parent) {
        // Inflate the cpu row view on the recycler view parent
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.cpu_row_view, parent, false));
        this.parent = parent;

        itemName = itemView.findViewById(R.id.itemName);
        itemPrice = itemView.findViewById(R.id.itemPrice);
        itemCores = itemView.findViewById(R.id.itemCores);
        itemClockSpeed = itemView.findViewById(R.id.itemClockSpeed);
        itemImage = itemView.findViewById(R.id.itemImage);
    }

    @Override
    public void bind(IItem item) {
        // Cast the item to a CPU and set the view properties based on the item's values
        CPU cpu = (CPU) item;

        itemName.setText(cpu.getName());
        itemPrice.setText("$"+cpu.getPrice());
        itemCores.setText(cpu.getNumCores());
        itemClockSpeed.setText(cpu.getClockSpeed());
        itemImage.setImageResource(cpu.getImages().get(0));
    }
}
