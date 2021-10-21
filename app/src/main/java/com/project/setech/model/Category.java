package com.project.setech.model;

import java.util.List;

public class Category implements ICategory {
    private final String id;
    private final String name;
    private final String description;
    private final int categoryImage;

    public Category(String id, String name, String description, int categoryImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryImage = categoryImage;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCategoryImage() {
        return categoryImage;
    }
}
