package com.project.setech.repository;

import com.project.setech.model.IItem;
import com.project.setech.util.CategoryType;

import java.util.List;
import java.util.Map;

/**
 * Interface used to provide a callback function to act on the returned
 * single item result
 */
public interface ISingleItemCallBack {
    public void onSuccess(IItem item, CategoryType categoryType, Map<String, String> specifications);
}
