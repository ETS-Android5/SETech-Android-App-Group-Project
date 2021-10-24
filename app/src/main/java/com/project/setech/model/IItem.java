package com.project.setech.model;

import java.util.List;

/**
 * This interface provides methods for the Item class
 */
public interface IItem {
    public String getId();
    public String getName();
    public List<Integer> getImages();
    public String getPrice();
    public String getViewCount();
}
