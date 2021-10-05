package com.project.setech.model.itemType;

import com.project.setech.model.Item;

import java.util.List;

public class CPU extends Item {
    private final String cpuFamily;
    private final String numCores;
    private final String cpuCores;
    private final String cpuSocket;
    private final String clockSpeed;
    private final String boostClockSpeed;

    public CPU(String name, String description, List<String> images, float price, String cpuFamily, String numCores, String cpuCores, String cpuSocket, String clockSpeed, String boostClockSpeed) {
        super(name, description, images, price);
        this.cpuFamily = cpuFamily;
        this.numCores = numCores;
        this.cpuCores = cpuCores;
        this.cpuSocket = cpuSocket;
        this.clockSpeed = clockSpeed;
        this.boostClockSpeed = boostClockSpeed;
    }

    public String getCpuFamily() {
        return cpuFamily;
    }

    public String getNumCores() {
        return numCores;
    }

    public String getCpuCores() {
        return cpuCores;
    }

    public String getCpuSocket() {
        return cpuSocket;
    }

    public String getClockSpeed() {
        return clockSpeed;
    }

    public String getBoostClockSpeed() {
        return boostClockSpeed;
    }
}
