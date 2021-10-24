package com.project.setech.activities.searchActivity.searchRecyclerView;

import android.content.Context;
import com.project.setech.activities.ParentAdapter;
import com.project.setech.model.IItem;
import com.project.setech.util.CategoryType;

import java.util.List;

/**
 * This class is an adapter for SearchActivity
 */
public class SearchViewAdapter extends ParentAdapter {

    /**
     * Constructor of SearchViewAdapter
     * @param context Variable that holds global information about an application environment
     * @param itemList list of items
     * @param type The category type of item
     */
    public SearchViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        super(context, itemList, type);
        adType = "search";
    }
}
