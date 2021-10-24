package com.project.setech.util.SortBy;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.project.setech.model.IItem;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortBy implements ISortBy{

    /**
     * Sorts the items by price in the provided order using the compareByPrice comparator
     */
    @Override
    public void sortByPrice(List<IItem> itemList, String order) {
        if (order.equals("increase")) {
            Collections.sort(itemList, compareByPrice);
        } else {
            Collections.sort(itemList, compareByPrice.reversed());
        }
    }

    /**
     * Sorts the items by alphabetical order in the provided order using the compareByName comparator
     */
    @Override
    public void sortByName(List<IItem> itemList, String order) {
        if (order.equals("increase")) {
            Collections.sort(itemList, compareByName);
        } else {
            Collections.sort(itemList, compareByName.reversed());
        }
    }

    /**
     * Sorts the items by view count in the provided order using the compareByView comparator
     */
    @Override
    public void sortByView(List<IItem> itemList, String order) {
        if (order.equals("increase")) {
            Collections.sort(itemList, compareByView);
        } else {
            Collections.sort(itemList, compareByView.reversed());
        }
    }

    //comparator used for comparing prices
    Comparator<IItem> compareByPrice = new Comparator<IItem>() {
        @Override
        public int compare(IItem o1, IItem o2) {
            return Float.compare(Float.parseFloat(o1.getPrice()), (Float.parseFloat(o2.getPrice())));
        }
    };

    //comparator used for comparing names
    Comparator<IItem> compareByName = new Comparator<IItem>() {
        @Override
        public int compare(IItem o1, IItem o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

    //comparator used for comparing views
    Comparator<IItem> compareByView = new Comparator<IItem>() {
        @Override
        public int compare(IItem o1, IItem o2) {
            return Integer.parseInt(o1.getViewCount()) - Integer.parseInt(o2.getViewCount());
        }
    };
}
