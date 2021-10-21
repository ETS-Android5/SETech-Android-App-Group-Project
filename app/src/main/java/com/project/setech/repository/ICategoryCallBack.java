package com.project.setech.repository;

import com.project.setech.model.Category;
import com.project.setech.model.ICategory;
import com.project.setech.model.IItem;

import java.util.List;

public interface ICategoryCallBack {
    public void onSuccess(List<ICategory> categories);
}
