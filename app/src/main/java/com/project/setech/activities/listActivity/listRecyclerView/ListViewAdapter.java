package com.project.setech.activities.listActivity.listRecyclerView;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.project.setech.activities.ParentAdapter;
import com.project.setech.model.IItem;
import com.project.setech.util.CategoryType;

import java.util.List;

public class ListViewAdapter extends ParentAdapter {


    public ListViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        super(context, itemList, type);
        adType = "list";
    }

    public void setButtonAndString(String s, String c) {
        order = s;
        clickedString = c;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void backToFull() {
        itemList.clear();
        itemList.addAll(itemListFull);
        if(clickedString == "price") {
            sortPrice(order);
        } else if(clickedString == "name") {
            sortName(order);
        } else if(clickedString == "view") {
            sortView(order);
        }
    }
}
