package com.project.setech.util.SearchBy;

import com.project.setech.model.IItem;

import java.util.ArrayList;
import java.util.List;

public class SearchBy implements ISearchBy{
    @Override
    public List<IItem> filterBySearchText(List<IItem> itemList,String searchText) {
        //create temporary list to hold filtered items
        List<IItem> tempList = new ArrayList<>();

        if (searchText.length() == 0) {
            tempList.addAll(itemList);
        } else {
            for (IItem item : itemList) {
                if (item.getName().toLowerCase().trim().contains(searchText.toLowerCase().trim())) {
                    tempList.add(item);
                }
            }
        }

        return tempList;
    }
}
