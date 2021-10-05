package com.project.setech.model;

import java.util.List;

public class Category implements ICategory{
    private final String name;
    private final String description;
    private final String categoryImage;
    private final List<IItem> categoryItems;

    public Category(String name, String description, String categoryImage, List<IItem> categoryItems) {
        this.name = name;
        this.description = description;
        this.categoryImage = categoryImage;
        this.categoryItems = categoryItems;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public List<IItem> getCategoryItems() {
        return categoryItems;
    }
}
