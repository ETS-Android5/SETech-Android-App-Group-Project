package com.project.setech.model;

import android.util.Log;

import com.project.setech.model.itemType.CPU;
import com.project.setech.model.itemType.GPU;
import com.project.setech.model.itemType.Motherboard;
import com.project.setech.util.CategoryType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemFactory {
    public IItem createItem(String id,String name, List<Integer> images, String price, String viewCount, Map<String, String> specifications,CategoryType type) {

        switch (type) {
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
                return new Item(
                        name,
                        images,
                        price
                );
            default:
                throw new IllegalArgumentException("Unsupported item fetched from the database!");
        }
    }
}
