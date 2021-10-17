package com.project.setech.activities.listActivity.listRecyclerView.viewHolders;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.setech.R;
import com.project.setech.model.IItem;
import com.project.setech.model.itemType.GPU;

public class GPUViewHolder extends BaseItemViewHolder{
    public ViewGroup parent;

    public TextView gpuName, gpuPrice;
    public ImageView gpuImage;


    public GPUViewHolder(ViewGroup parent) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.gpu_row_view, parent, false));
        this.parent = parent;

        gpuName = itemView.findViewById(R.id.gpuName);
        gpuPrice = itemView.findViewById(R.id.detailsItemPrice);
        gpuImage = itemView.findViewById(R.id.gpuImage);
    }

    @Override
    public void bind(IItem item) {
        GPU gpu = (GPU) item;

        gpuName.setText(gpu.getName());
        gpuPrice.setText("$"+gpu.getPrice());
        gpuImage.setImageResource(gpu.getImages().get(0));
    }
}
