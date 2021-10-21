package com.project.setech.util.CustomExceptions;

public class InvalidFetchedItem extends Exception{

    public InvalidFetchedItem() {

    }

    public InvalidFetchedItem(String message) {
        super(message);
    }
}
