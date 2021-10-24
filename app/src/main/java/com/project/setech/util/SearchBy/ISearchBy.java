package com.project.setech.util.SearchBy;

import com.project.setech.model.IItem;

import java.util.List;

public interface ISearchBy {
    public List<IItem> filterBySearchText(List<IItem> itemList,String searchText);
}
