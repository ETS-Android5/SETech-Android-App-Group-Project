package com.project.setech.model.itemType;

import com.project.setech.model.Item;

import java.util.List;

public class GPU extends Item {
    private final String productModel;
    private final String memSize;
    private final String baseClockSpeed;
    private final String boostClockSpeed;
    private final String maxDisplays;
    private final String length;
    private final String dispPorts;
    private final String hdmiPorts;

    public GPU(String name, List<Integer> images, String price, String productModel, String memSize, String baseClockSpeed, String boostClockSpeed, String maxDisplays, String length, String dispPorts, String hdmiPorts) {
        super(name, images, price);
        this.productModel = productModel;
        this.memSize = memSize;
        this.baseClockSpeed = baseClockSpeed;
        this.boostClockSpeed = boostClockSpeed;
        this.maxDisplays = maxDisplays;
        this.length = length;
        this.dispPorts = dispPorts;
        this.hdmiPorts = hdmiPorts;
    }

    public String getProductModel() {
        return productModel;
    }

    public String getMemSize() {
        return memSize;
    }

    public String getBaseClockSpeed() {
        return baseClockSpeed;
    }

    public String getBoostClockSpeed() {
        return boostClockSpeed;
    }

    public String getMaxDisplays() {
        return maxDisplays;
    }

    public String getLength() {
        return length;
    }

    public String getDispPorts() {
        return dispPorts;
    }

    public String getHdmiPorts() {
        return hdmiPorts;
    }
}
