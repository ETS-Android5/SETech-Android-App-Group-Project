package com.project.setech.activities.searchActivity.searchRecyclerView;

import android.content.Context;
import com.project.setech.activities.ParentAdapter;
import com.project.setech.model.IItem;
import com.project.setech.util.CategoryType;

import java.util.List;

public class SearchViewAdapter extends ParentAdapter {

    public SearchViewAdapter(Context context, List<IItem> itemList, CategoryType type) {
        super(context, itemList, type);
        adType = "search";
    }
}
