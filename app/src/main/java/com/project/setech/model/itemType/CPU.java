package com.project.setech.model.itemType;

import androidx.annotation.NonNull;

import com.project.setech.model.Item;

import java.util.Arrays;
import java.util.List;

public class CPU extends Item {
    private final String cpuFamily;
    private final String numCores;
    private final String cpuSocket;
    private final String clockSpeed;
    private final String boostClockSpeed;

    public CPU(String name, List<Integer> images, String price, String cpuFamily, String numCores, String cpuSocket, String clockSpeed, String boostClockSpeed) {
        super(name, images, price);
        this.cpuFamily = cpuFamily;
        this.numCores = numCores;
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

    public String getCpuSocket() {
        return cpuSocket;
    }

    public String getClockSpeed() {
        return clockSpeed;
    }

    public String getBoostClockSpeed() {
        return boostClockSpeed;
    }

    @NonNull
    @Override
    public String toString() {
        return cpuFamily+numCores+cpuSocket+clockSpeed+boostClockSpeed+getName()+getPrice();
    }
}
