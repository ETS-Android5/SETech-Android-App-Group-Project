package com.project.setech.repository;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.project.setech.model.ICategory;
import com.project.setech.model.ICategoryFactory;
import com.project.setech.model.IItem;
import com.project.setech.model.IItemFactory;
import com.project.setech.util.CategoryType;
import com.project.setech.util.CustomExceptions.InvalidFetchedItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Repository implements IRepository {

    private final Context context;
    IItemFactory itemFactory;
    ICategoryFactory categoryFactory;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference itemsRef = db.collection("Items");
    private CollectionReference categoriesRef = db.collection("Categories");

    public Repository(Context context, IItemFactory itemFactory) {
        this.context = context;
        this.itemFactory = itemFactory;
    }

    public Repository(Context context, IItemFactory itemFactory, ICategoryFactory categoryFactory) {
        this.context = context;
        this.itemFactory = itemFactory;
        this.categoryFactory = categoryFactory;
    }

    /**
     * Fetch a single item of given id from the database
     */
    @Override
    public void fetchItem(String id, ISingleItemCallBack callBack) {

        itemsRef.document(id).get().addOnCompleteListener(itemTask -> {
            if (itemTask.isSuccessful()) {
                DocumentSnapshot itemDoc = itemTask.getResult();

                assert itemDoc != null;
                if (itemDoc.exists()) {
                    itemDoc.getDocumentReference("category").get().addOnCompleteListener(categoryTask -> {
                        if (categoryTask.isSuccessful()) {
                            try {
                                DocumentSnapshot categoryDoc = categoryTask.getResult();
                                CategoryType categoryType = CategoryType.valueOf(categoryDoc.getId());


                                // Use the provided item factory in order to create the item
                                IItem item = itemFactory.createItem(itemDoc.getId(),itemDoc.getData(), context, categoryType);

                                // Call the callback method
                                callBack.onSuccess(item,categoryType, (Map<String, String>) itemDoc.get("specifications"));
                            } catch (InvalidFetchedItem invalidFetchedItem) {
                                Log.d("Repository", "Item of id: " + itemDoc.getId() + " failed to be fetched/created!");
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Update a value of an item given an id, value name and what to set it to
     */
    @Override
    public void updateItemValue(String id, String valueName, Object value) {
        itemsRef.document(id).get().addOnCompleteListener(itemTask -> {
            if (itemTask.isSuccessful()) {
                DocumentSnapshot itemDoc = itemTask.getResult();

                // Get the data map
                Map<String, Object> data = itemDoc.getData();

                // Find if the key exsits and set it
                if (data.containsKey(valueName)) {
                    data.put(valueName, value);
                    itemsRef.document(id).set(data);
                }
                else {
                    // Otherwise through an error as the method was used with invalid data
                    throw new IllegalArgumentException("Invalid value name passed into update item in repository!");
                }
            }
        });
    }

    /**
     * Fetch all of items from the database
     */
    @Override
    public List<IItem> fetchItems(IItemsCallBack callBack) {
        List<IItem> items = new ArrayList<>();

        itemsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
                    try {
                        // Attempt to create the item using Item factory
                        items.add(itemFactory.createItem(item.getId(),item.getData(), context, CategoryType.ALL));
                    } catch (InvalidFetchedItem e) {
                        // Otherwise the item is of invalid type and therefore failed to be loaded
                        // Don't want to throw any errors but instead just don't add the item
                        Log.d("Repository", "Item of id: " + item.getId() + " failed to be fetched/created!");
                    }
                }
            }

            // Call the callback method
            callBack.onSuccess(items);
        });

        return items;
    }

    /**
     * Fetch all the items of given category from the database
     */
    @Override
    public void fetchItems(CategoryType categoryType, IItemsCallBack callBack) {
        List<IItem> items = new ArrayList<>();

        // Where category is equal to the passed in category
        itemsRef.whereEqualTo("category", categoriesRef.document(categoryType.toString())).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
                    try {
                        // Attempt to create the item using Item factory
                        items.add(itemFactory.createItem(item.getId(),item.getData(), context, categoryType));
                    } catch (InvalidFetchedItem e) {
                        // Otherwise the item is of invalid type and therefore failed to be loaded
                        // Don't want to throw any errors but instead just don't add the item
                        Log.d("Repository", "Item of id: " + item.getId() + " failed to be fetched/created!");
                    }
                }
            }

            // Call the callback method
            callBack.onSuccess(items);
        });
    }

    /**
     * Fetch all the items and sort it by a given value and given direction, and also only return the given limit
     */
    @Override
    public void fetchItems(String sortByValue, Query.Direction direction, int limit, IItemsCallBack callBack) {
        List<IItem> items = new ArrayList<>();

        itemsRef.orderBy("viewCount", direction).limit(limit).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
                    try {
                        // Attempt to create the item using Item factory
                        items.add(itemFactory.createItem(item.getId(),item.getData(), context, CategoryType.ALL));
                    } catch (InvalidFetchedItem e) {
                        // Otherwise the item is of invalid type and therefore failed to be loaded
                        // Don't want to throw any errors but instead just don't add the item
                        Log.d("Repository", "Item of id: " + item.getId() + " failed to be fetched/created!");
                    }
                }
            }

            // Call the callback method
            callBack.onSuccess(items);
        });
    }

    /**
     * Fetch all the items of given category and sort them by value and in a given direction as well as only return the given limit
     */
    @Override
    public void fetchItems(CategoryType categoryType, String sortByValue, Query.Direction direction, int limit, IItemsCallBack callBack) {
        List<IItem> items = new ArrayList<>();

        itemsRef.whereEqualTo("category", categoriesRef.document(categoryType.toString())).orderBy("viewCount", direction).limit(limit).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
                    try {
                        // Attempt to create the item using Item factory
                        items.add(itemFactory.createItem(item.getId(),item.getData(), context, categoryType));
                    } catch (InvalidFetchedItem e) {
                        // Otherwise the item is of invalid type and therefore failed to be loaded
                        // Don't want to throw any errors but instead just don't add the item
                        Log.d("Repository", "Item of id: " + item.getId() + " failed to be fetched/created!");
                    }
                }
            }

            // Call the callback method
            callBack.onSuccess(items);
        });
    }

    /**
     * Fetch all the categories from the database
     */
    @Override
    public List<ICategory> fetchCategories(ICategoryCallBack callBack) {
        // If no category factory was provided in the constructor then return null for the list
        if (categoryFactory == null) return null;

        List<ICategory> categories = new ArrayList<>();

        categoriesRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot category : queryDocumentSnapshots) {
                    // Use the category factory and create the categories
                    categories.add(categoryFactory.createCategory(category.getId(),(Map<String,Object>) category.getData(), context));
                }
            }

            // Call the callback method
            callBack.onSuccess(categories);
        });

        return categories;
    }
}
