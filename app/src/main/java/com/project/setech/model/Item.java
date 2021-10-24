package com.project.setech.model;

import java.util.List;

/**
 * This class represents a single item
 */
public class Item implements IItem {
    private final String id;
    private final String name;
    private final List<Integer> images;
    private final String price;
    private final String viewCount;

    /**
     * The constructor for Item class
     * @param id The unique id of the item
     * @param name The name of the item
     * @param images A list of integers used for matching item with its images
     * @param price The price of the item
     * @param viewCount The number of views of this item
     */
    public Item(String id, String name, List<Integer> images, String price, String viewCount) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.price = price;
        this.viewCount = viewCount;
    }

    /**
     * Getter fot the ID of the Item
     * @return ID String
     */
    public String getId() {
        return id;
    }

    /**
     * Getter fot the Name of the Item
     * @return Name String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter fot the images
     * @return A list of integers representing the images
     */
    public List<Integer> getImages() {
        return images;
    }

    /**
     * Getter fot the price
     * @return Price String
     */
    public String getPrice() {
        return price;
    }

    /**
     * Getter fot the View
     * @return View String
     */
    public String getViewCount() {
        return viewCount;
    }
}
