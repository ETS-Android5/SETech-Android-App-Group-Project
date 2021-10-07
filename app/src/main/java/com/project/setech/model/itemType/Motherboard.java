package com.project.setech.model.itemType;

import com.project.setech.model.Item;

import java.util.List;

public class Motherboard extends Item {
    private final String mbSocket;
    private final String wifi;
    private final String chipset;
    private final String formFactor;
    private final String multiGpuSupport;
    private final String memType;
    private final String pciSlots;
    private final String fourPinRgbHeader;

    public Motherboard(String name,
                       List<String> images,
                       float price, String mbSocket,
                       String wifi, String chipset,
                       String formFactor,
                       String multiGpuSupport,
                       String memType,
                       String pciSlots,
                       String fourPinRgbHeader) {
        super(name, images, price);
        this.mbSocket = mbSocket;
        this.wifi = wifi;
        this.chipset = chipset;
        this.formFactor = formFactor;
        this.multiGpuSupport = multiGpuSupport;
        this.memType = memType;
        this.pciSlots = pciSlots;
        this.fourPinRgbHeader = fourPinRgbHeader;
    }

    public String getMbSocket() {
        return mbSocket;
    }

    public String getWifi() {
        return wifi;
    }

    public String getChipset() {
        return chipset;
    }

    public String getFormFactor() {
        return formFactor;
    }

    public String getMultiGpuSupport() {
        return multiGpuSupport;
    }

    public String getMemType() {
        return memType;
    }

    public String getPciSlots() {
        return pciSlots;
    }

    public String getFourPinRgbHeader() {
        return fourPinRgbHeader;
    }
}
