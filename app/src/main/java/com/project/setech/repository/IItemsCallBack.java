package com.project.setech.repository;

import com.project.setech.model.IItem;

import java.util.List;

/**
 * Interface used to provide a callback function to act on the returned
 * item list result
 */
public interface IItemsCallBack {
    public void onSuccess(List<IItem> items);
}
