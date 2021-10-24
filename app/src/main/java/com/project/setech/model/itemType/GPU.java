package com.project.setech.model.itemType;

import com.project.setech.model.Item;

import java.util.List;

/**
 * THis class represents a single GPU item
 */
public class GPU extends Item {
    private final String productModel;
    private final String memSize;
    private final String baseClockSpeed;
    private final String boostClockSpeed;
    private final String maxDisplays;
    private final String length;
    private final String dispPorts;
    private final String hdmiPorts;

    /**
     * The constructor of the GPU item
     * @param id ID of the GPU
     * @param name Name of the GPU
     * @param images List of integers representing this GPU's images
     * @param price Price of the GPU
     * @param viewCount View count of the GPU
     * @param productModel Product model of this GPU
     * @param memSize Memory size of this GPU
     * @param baseClockSpeed Base clock speed of this GPU
     * @param boostClockSpeed Boost clock speed of this GPU
     * @param maxDisplays Maximum displays of this GPU
     * @param length Length of this GPU
     * @param dispPorts Display ports of this GPU
     * @param hdmiPorts HDMI ports of this GPU
     */
    public GPU(String id,String name, List<Integer> images, String price, String viewCount, String productModel, String memSize, String baseClockSpeed, String boostClockSpeed, String maxDisplays, String length, String dispPorts, String hdmiPorts) {
        super(id, name, images, price, viewCount);
        this.productModel = productModel;
        this.memSize = memSize;
        this.baseClockSpeed = baseClockSpeed;
        this.boostClockSpeed = boostClockSpeed;
        this.maxDisplays = maxDisplays;
        this.length = length;
        this.dispPorts = dispPorts;
        this.hdmiPorts = hdmiPorts;
    }

    /**
     * Getter of productModel
     * @return productModel
     */
    public String getProductModel() {
        return productModel;
    }

    /**
     * Getter of memSize
     * @return memSize
     */
    public String getMemSize() {
        return memSize;
    }

    /**
     * Getter of baseClockSpeed
     * @return baseClockSpeed
     */
    public String getBaseClockSpeed() {
        return baseClockSpeed;
    }

    /**
     * Getter of boostClockSpeed
     * @return boostClockSpeed
     */
    public String getBoostClockSpeed() {
        return boostClockSpeed;
    }

    /**
     * Getter of maxDisplays
     * @return maxDisplays
     */
    public String getMaxDisplays() {
        return maxDisplays;
    }

    /**
     * Getter of length
     * @return length
     */
    public String getLength() {
        return length;
    }

    /**
     * Getter of dispPorts
     * @return dispPorts
     */
    public String getDispPorts() {
        return dispPorts;
    }

    /**
     * Getter of hdmiPorts
     * @return hdmiPorts
     */
    public String getHdmiPorts() {
        return hdmiPorts;
    }
}
