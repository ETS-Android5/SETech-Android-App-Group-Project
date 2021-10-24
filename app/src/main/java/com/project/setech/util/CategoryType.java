package com.project.setech.util;

/**
 * Enum used for the all the supported categories by the application
 * ALL is used whenever a list of items contains all the category types
 */
public enum CategoryType {
    CPU {
        @Override
        public String toString() {
            return "CPU";
        }
    },
    Motherboard {
        @Override
        public String toString() {
            return "Motherboard";
        }
    },
    GPU {
        @Override
        public String toString() {
            return "GPU";
        }
    },
    ALL {
        @Override
        public String toString() {
            return "ALL";
        }
    }
}
