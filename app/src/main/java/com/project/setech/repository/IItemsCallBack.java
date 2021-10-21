package com.project.setech.repository;

import com.project.setech.model.IItem;

import java.util.List;

public interface IItemsCallBack {
    public void onSuccess(List<IItem> items);
}
