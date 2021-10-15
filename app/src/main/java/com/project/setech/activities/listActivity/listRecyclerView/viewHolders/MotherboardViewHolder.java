package com.project.setech.activities.listActivity.listRecyclerView.viewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.setech.R;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.Motherboard;

public class MotherboardViewHolder extends BaseItemViewHolder{
    public ViewGroup parent;

    public TextView motherboardName, motherboardPrice, motherBoardFormFactor, motherboardSocket, motherBoardChipset;
    public ImageView motherboardImage;


    public MotherboardViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.motherboard_row_view, parent, false));
        this.parent = parent;

        motherboardName = itemView.findViewById(R.id.motherboardName);
        motherboardPrice = itemView.findViewById(R.id.gpuPrice);
        motherBoardFormFactor = itemView.findViewById(R.id.motherboardFormFactor);
        motherboardSocket = itemView.findViewById(R.id.motherboardSocket);
        motherBoardChipset = itemView.findViewById(R.id.motherboardChipset);
        motherboardImage = itemView.findViewById(R.id.motherboardImage);
    }

    @Override
    public void bind(IItem item) {
        Motherboard motherboard = (Motherboard) item;

        motherboardName.setText(motherboard.getName());
        motherboardPrice.setText("$"+motherboard.getPrice());
        motherBoardFormFactor.setText(motherboard.getFormFactor());
        motherboardSocket.setText(motherboard.getMbSocket());
        motherBoardChipset.setText(motherboard.getChipset());
        motherboardImage.setImageResource(motherboard.getImages().get(0));
    }
}
