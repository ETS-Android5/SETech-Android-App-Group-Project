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
            // If the search query is an empty string then return all the items
            tempList.addAll(itemList);
        } else {
            for (IItem item : itemList) {
                // Otherwise filter the items out that don't contain the search query in its name
                if (item.getName().toLowerCase().trim().contains(searchText.toLowerCase().trim())) {
                    tempList.add(item);
                }
            }
        }

        return tempList;
    }
}
