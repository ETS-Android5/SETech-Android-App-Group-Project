package com.project.setech.model;

import android.content.Context;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.setech.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * This is a factory class used to create Category
 */
public class CategoryFactor implements ICategoryFactory{

    /**
     * Creating a Category
     * @param id ID of the category
     * @param category The fetched category map with its information
     * @param context  Global information about an application environment
     * @return Category object
     */
    @Override
    public ICategory createCategory(String id,Map<String,Object> category, Context context) {
        String name = (String) category.get("categoryName");
        String description = (String) category.get("categoryDescription");
        String categoryImage = (String) category.get("categoryImage");

        List<Integer> images = Util.formatDrawableStringList(new ArrayList<String>(Arrays.asList(categoryImage)), context);

        return new Category(id,name,description,images.get(0));
    }
}
