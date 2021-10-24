package com.project.setech.model;

import android.content.Context;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.setech.util.CategoryType;
import com.project.setech.util.CustomExceptions.InvalidFetchedItem;

import java.util.Map;

/**
 * This interface provides methods for the NewItemFactory class
 */
public interface IItemFactory {
    public IItem createItem(String id,Map<String,Object> item, Context context, CategoryType categoryType) throws InvalidFetchedItem;
}
