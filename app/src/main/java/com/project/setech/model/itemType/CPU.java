package com.project.setech.model.itemType;

import androidx.annotation.NonNull;

import com.project.setech.model.Item;

import java.util.List;

/**
 * THis class represents a single CPU item
 */
public class CPU extends Item {
    private final String cpuFamily;
    private final String numCores;
    private final String cpuSocket;
    private final String clockSpeed;
    private final String boostClockSpeed;

    /**
     * The constructor of the CPU item
     * @param id ID of the CPU
     * @param name Name of the CPU
     * @param images List of integers representing this CPU's images
     * @param price Price of the CPU
     * @param viewCount View count of the CPU
     * @param cpuFamily Family of this CPU
     * @param numCores Number of cores of this CPU
     * @param cpuSocket CPU socket
     * @param clockSpeed Clock speed of this CPU
     * @param boostClockSpeed Boost clock speed of this CPU
     */
    public CPU(String id,String name, List<Integer> images, String price,String viewCount, String cpuFamily, String numCores, String cpuSocket, String clockSpeed, String boostClockSpeed) {
        super(id, name, images, price, viewCount);
        this.cpuFamily = cpuFamily;
        this.numCores = numCores;
        this.cpuSocket = cpuSocket;
        this.clockSpeed = clockSpeed;
        this.boostClockSpeed = boostClockSpeed;
    }

    /**
     * Getter of cpuFamily
     * @return cpuFamily
     */
    public String getCpuFamily() {
        return cpuFamily;
    }

    /**
     * Getter of numCores
     * @return numCores
     */
    public String getNumCores() {
        return numCores;
    }

    /**
     * Getter of cpuSocket
     * @return cpuSocket
     */
    public String getCpuSocket() {
        return cpuSocket;
    }

    /**
     * Getter of clockSpeed
     * @return clockSpeed
     */
    public String getClockSpeed() {
        return clockSpeed;
    }

    /**
     * Getter of boostClockSpeed
     * @return boostClockSpeed
     */
    public String getBoostClockSpeed() {
        return boostClockSpeed;
    }

    /**
     * Return the informations of the CPU in String form
     * @return
     */
    @NonNull
    @Override
    public String toString() {
        return cpuFamily+numCores+cpuSocket+clockSpeed+boostClockSpeed+getName()+getPrice();
    }
}
