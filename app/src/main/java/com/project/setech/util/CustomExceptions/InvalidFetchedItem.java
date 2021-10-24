package com.project.setech.util.CustomExceptions;

/**
 * Used in NewItemFactory whenever a fetched item does not meet certain criteria
 * therefore does not match any valid item types
 */
public class InvalidFetchedItem extends Exception{

    public InvalidFetchedItem() {

    }

    public InvalidFetchedItem(String message) {
        super(message);
    }
}
