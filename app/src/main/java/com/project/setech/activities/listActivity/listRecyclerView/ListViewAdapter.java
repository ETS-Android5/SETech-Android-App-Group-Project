package com.project.setech.activities.listActivity.listRecyclerView;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.project.setech.activities.ParentAdapter;
import com.project.setech.model.IItem;
import com.project.setech.util.CategoryType;

import java.util.List;

/**
 * This class is an adapter for ListActivity
 */
public class ListViewAdapter extends ParentAdapter {

    /**
     * Constructor of ListViewAdapter
     * @param context Variable that holds global information about an application environment
     * @param itemList list of items
     * @param type The category type of item
     */
    public ListViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        super(context, itemList, type);
        adType = "list";
    }

    /**
     * This method is used to get the sort order and type
     * @param s Order of the sort
     * @param c Type of the sort
     */
    public void setButtonAndString(String s, String c) {
        order = s;
        clickedString = c;
    }

    /**
     * This method is used to replace current itemList with a sorted full itemList
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void backToFull() {
        itemList.clear();
        itemList.addAll(itemListFull);
        sortByType(clickedString,order);
    }
}
