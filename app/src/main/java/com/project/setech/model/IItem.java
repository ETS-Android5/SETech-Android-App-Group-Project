package com.project.setech.model;

import java.util.List;

public interface IItem {
    public String getName();
    public String getDescription();
    public List<String> getImages();
    public float getPrice();
}
