package com.project.setech.model;

import java.util.List;

public interface ICategory {
    public String getName();
    public String getDescription();
    public String getCategoryImage();
    public List<IItem> getCategoryItems();
}
