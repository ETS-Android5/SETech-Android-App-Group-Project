package com.project.setech.util.SearchBy;

import com.project.setech.model.IItem;

import java.util.List;

/**
 * Interface for the search query filtering of items
 */
public interface ISearchBy {
    public List<IItem> filterBySearchText(List<IItem> itemList,String searchText);
}
