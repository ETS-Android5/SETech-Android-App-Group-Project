package com.project.setech.util.SortBy;

import com.project.setech.model.IItem;

import java.util.List;

public interface ISortBy {
    public void sortByPrice(List<IItem> itemList,String order);
    public void sortByName(List<IItem> itemList,String order);
    public void sortByView(List<IItem> itemList,String order);
}
