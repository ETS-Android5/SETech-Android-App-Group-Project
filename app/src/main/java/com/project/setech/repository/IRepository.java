package com.project.setech.repository;

import com.google.firebase.firestore.Query;
import com.project.setech.model.ICategory;
import com.project.setech.model.IItem;
import com.project.setech.util.CategoryType;

import java.util.List;

public interface IRepository {
    public void fetchItem(String id, ISingleItemCallBack callBack);
    public List<IItem> fetchItems(IItemsCallBack callBack);
    public void fetchItems(CategoryType categoryType, IItemsCallBack callBack);
//    public List<IItem> fetchItems(int limit, CategoryType categoryType,IItemCallBack callBack);
    public void fetchItems(String sortByValue, Query.Direction direction, int limit, IItemsCallBack callBack);
    public void fetchItems(CategoryType categoryType, String sortByValue, Query.Direction direction, int limit, IItemsCallBack callBack);

    public List<ICategory> fetchCategories(ICategoryCallBack callBack);
}
