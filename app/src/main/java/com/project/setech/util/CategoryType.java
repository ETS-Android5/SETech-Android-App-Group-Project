package com.project.setech.util;

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
