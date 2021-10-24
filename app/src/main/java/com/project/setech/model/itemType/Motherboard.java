package com.project.setech.model.itemType;

import com.project.setech.model.Item;

import java.util.List;

/**
 * THis class represents a single Motherboard item
 */
public class Motherboard extends Item {
    private final String mbSocket;
    private final String wifi;
    private final String chipset;
    private final String formFactor;
    private final String multiGpuSupport;
    private final String memType;
    private final String pciSlots;
    private final String fourPinRgbHeader;

    /**
     * The constructor of the Motherboard item
     * @param id ID of the Motherboard
     * @param name Name of the Motherboard
     * @param images List of integers representing this Motherboard's images
     * @param price Price of the Motherboard
     * @param viewCount View count of the Motherboard
     * @param mbSocket MB socket of this Motherboard
     * @param wifi WIFI functionality of this Motherboard
     * @param chipset Chipset of this Motherboard
     * @param formFactor Form factor of this Motherboard
     * @param multiGpuSupport Multi-GPU support of this Motherboard
     * @param memType Memory type of this Motherboard
     * @param pciSlots PCI slots of this Motherboard
     * @param fourPinRgbHeader Four Pin RGB Header of this Motherboard
     */
    public Motherboard(String id,
                       String name,
                       List<Integer> images,
                       String price,
                       String viewCount,
                       String mbSocket,
                       String wifi,
                       String chipset,
                       String formFactor,
                       String multiGpuSupport,
                       String memType,
                       String pciSlots,
                       String fourPinRgbHeader) {
        super(id, name, images, price, viewCount);
        this.mbSocket = mbSocket;
        this.wifi = wifi;
        this.chipset = chipset;
        this.formFactor = formFactor;
        this.multiGpuSupport = multiGpuSupport;
        this.memType = memType;
        this.pciSlots = pciSlots;
        this.fourPinRgbHeader = fourPinRgbHeader;
    }

    /**
     * Getter of mbSocket
     * @return mbSocket
     */
    public String getMbSocket() {
        return mbSocket;
    }

    /**
     * Getter of wifi
     * @return wifi
     */
    public String getWifi() {
        return wifi;
    }

    /**
     * Getter of chipset
     * @return chipset
     */
    public String getChipset() {
        return chipset;
    }

    /**
     * Getter of formFactor
     * @return formFactor
     */
    public String getFormFactor() {
        return formFactor;
    }

    /**
     * Getter of multiGpuSupport
     * @return multiGpuSupport
     */
    public String getMultiGpuSupport() {
        return multiGpuSupport;
    }

    /**
     * Getter of memType
     * @return memType
     */
    public String getMemType() {
        return memType;
    }

    /**
     * Getter of pciSlots
     * @return pciSlots
     */
    public String getPciSlots() {
        return pciSlots;
    }

    /**
     * Getter of fourPinRgbHeader
     * @return fourPinRgbHeader
     */
    public String getFourPinRgbHeader() {
        return fourPinRgbHeader;
    }
}
