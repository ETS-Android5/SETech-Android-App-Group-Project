package com.project.setech.model;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.setech.activities.mainActivity.MainActivity;
import com.project.setech.model.itemType.CPU;
import com.project.setech.model.itemType.GPU;
import com.project.setech.model.itemType.Motherboard;
import com.project.setech.util.CategoryType;
import com.project.setech.util.CustomExceptions.InvalidFetchedItem;
import com.project.setech.util.Util;

import java.util.List;
import java.util.Map;

/**
 * This class is a factory calss for creating a single Item object
 */
public class NewItemFactory implements IItemFactory{

    /**
     * Empty constructor for the NewItemFactory class
     */
    public NewItemFactory() {
    }

    /**
     * This method creates an item.
     * @param id ID of the item
     * @param item Specifications of the item
     * @param context Global information about an application environment
     * @param categoryType Category type of the item
     * @return An Item with specific type
     * @throws InvalidFetchedItem Exception when items are not properly fetched
     */
    @Override
    public IItem createItem(String id,Map<String,Object> item, Context context, CategoryType categoryType) throws InvalidFetchedItem {
        try {
            //getting item's name, images, price, view count and specifications
            String name = (String) item.get("name");
            List<Integer> images = Util.formatDrawableStringList((List<String>) item.get("images"), context);
            String price = (String) item.get("price");
            String viewCount = (String) item.get("viewCount");
            Map<String, String> specifications = (Map<String, String>) item.get("specifications");

            switch (categoryType) {
                case Motherboard:
                    return new Motherboard(
                            id,
                            name,
                            images,
                            price,
                            viewCount,
                            specifications.get("mbSocket"),
                            specifications.get("wifi"),
                            specifications.get("chipset"),
                            specifications.get("formFactor"),
                            specifications.get("multiGpuSupport"),
                            specifications.get("memType"),
                            specifications.get("pciSlots"),
                            specifications.get("fourPinRgbHeader")
                    );
                case GPU:
                    return new GPU(
                            id,
                            name,
                            images,
                            price,
                            viewCount,
                            specifications.get("productModel"),
                            specifications.get("memSize"),
                            specifications.get("baseClockSpeed"),
                            specifications.get("boostClockSpeed"),
                            specifications.get("maxDisplays"),
                            specifications.get("length"),
                            specifications.get("dispPorts"),
                            specifications.get("hdmiPorts")
                    );
                case CPU:
                    return new CPU(
                            id,
                            name,
                            images,
                            price,
                            viewCount,
                            specifications.get("cpuFamily"),
                            specifications.get("numCores"),
                            specifications.get("cpuSocket"),
                            specifications.get("clockSpeed"),
                            specifications.get("boostClockSpeed")
                    );
                case ALL:
                    //for SearchActivity, return CategoryType ALL
                    return new Item(
                            id,
                            name,
                            images,
                            price,
                            viewCount
                    );
                default:
                    throw new InvalidFetchedItem("Unsupported Category type used!");
            }
        }
        catch (Exception e) {
            throw new InvalidFetchedItem("Item fetched from the database of invalid type");
        }
    }
}
