package com.project.setech.model;

import java.util.List;

/**
 * This class represents the category for the MainActivity
 */
public class Category implements ICategory {
    private final String id;
    private final String name;
    private final String description;
    private final int categoryImage;

    /**
     * Constructor of the Category class
     * @param id ID of the category
     * @param name Name of the category
     * @param description Description of the category
     * @param categoryImage Image of the category
     */
    public Category(String id, String name, String description, int categoryImage) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryImage = categoryImage;
    }

    /**
     * Getter of id
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter of name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter of description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter of categoryImage
     * @return categoryImage
     */
    public int getCategoryImage() {
        return categoryImage;
    }
}
