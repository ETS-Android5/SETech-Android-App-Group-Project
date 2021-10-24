package com.project.setech.repository;

import com.project.setech.model.Category;
import com.project.setech.model.ICategory;
import com.project.setech.model.IItem;

import java.util.List;

/**
 * Interface used to provide a callback function to act on the returned
 * categories list result
 */
public interface ICategoryCallBack {
    public void onSuccess(List<ICategory> categories);
}
