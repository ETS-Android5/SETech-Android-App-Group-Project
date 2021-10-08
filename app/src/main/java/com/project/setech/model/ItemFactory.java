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
    public IItem createItem(String name, List<Integer> images, String price, Map<String, String> specifications,CategoryType type) {

        switch (type) {
            case Motherboard:
                return new Motherboard(
                        name,
                        images,
                        price,
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
                        name,
                        images,
                        price,
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
                        name,
                        images,
                        price,
                        specifications.get("cpuFamily"),
                        specifications.get("numCores"),
                        specifications.get("cpuSocket"),
                        specifications.get("clockSpeed"),
                        specifications.get("boostClockSpeed")
                );
            default:
                throw new IllegalArgumentException("Unsupported item fetched from the database!");
        }
    }
}
