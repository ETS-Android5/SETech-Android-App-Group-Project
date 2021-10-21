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

    @Override
    public void fetchItem(String id, ISingleItemCallBack callBack) {

        itemsRef.document(id).get().addOnCompleteListener(itemTask -> {
            if (itemTask.isSuccessful()) {
                DocumentSnapshot itemDoc = itemTask.getResult();

                assert itemDoc != null;
                if (itemDoc.exists()) {
                    // figure out which item type it is and cast it through item factor
                    itemDoc.getDocumentReference("category").get().addOnCompleteListener(categoryTask -> {
                        if (categoryTask.isSuccessful()) {
                            try {
                                DocumentSnapshot categoryDoc = categoryTask.getResult();
                                CategoryType categoryType = CategoryType.valueOf(categoryDoc.getId());

                                IItem item = itemFactory.createItem(itemDoc.getId(),itemDoc.getData(), context, categoryType);

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

    @Override
    public void updateItemValue(String id, String valueName, Object value) {
        itemsRef.document(id).get().addOnCompleteListener(itemTask -> {
            if (itemTask.isSuccessful()) {
                DocumentSnapshot itemDoc = itemTask.getResult();

                Map<String, Object> data = itemDoc.getData();

                if (data.containsKey(valueName)) {
                    data.put(valueName, value);
                    itemsRef.document(id).set(data);
                }
                else {
                    throw new IllegalArgumentException("Invalid value name passed into update item in repository!");
                }
            }
        });
    }

    @Override
    public List<IItem> fetchItems(IItemsCallBack callBack) {
        List<IItem> items = new ArrayList<>();

        itemsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
                    try {
                        items.add(itemFactory.createItem(item.getId(),item.getData(), context, CategoryType.ALL));
                    } catch (InvalidFetchedItem e) {
                        Log.d("Repository", "Item of id: " + item.getId() + " failed to be fetched/created!");
                    }
                }
            }

            callBack.onSuccess(items);
        });

        return items;
    }

    @Override
    public void fetchItems(CategoryType categoryType, IItemsCallBack callBack) {
        List<IItem> items = new ArrayList<>();

        itemsRef.whereEqualTo("category", categoriesRef.document(categoryType.toString())).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
                    try {
                        items.add(itemFactory.createItem(item.getId(),item.getData(), context, categoryType));
                    } catch (InvalidFetchedItem e) {
                        Log.d("Repository", "Item of id: " + item.getId() + " failed to be fetched/created!");
                    }
                }
            }

            callBack.onSuccess(items);
        });
    }

    @Override
    public void fetchItems(String sortByValue, Query.Direction direction, int limit, IItemsCallBack callBack) {
        List<IItem> items = new ArrayList<>();

        itemsRef.orderBy("viewCount", direction).limit(limit).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
                    try {
                        items.add(itemFactory.createItem(item.getId(),item.getData(), context, CategoryType.ALL));
                    } catch (InvalidFetchedItem e) {
                        Log.d("Repository", "Item of id: " + item.getId() + " failed to be fetched/created!");
                    }
                }
            }

            callBack.onSuccess(items);
        });
    }

    @Override
    public void fetchItems(CategoryType categoryType, String sortByValue, Query.Direction direction, int limit, IItemsCallBack callBack) {
        List<IItem> items = new ArrayList<>();

        itemsRef.whereEqualTo("category", categoriesRef.document(categoryType.toString())).orderBy("viewCount", direction).limit(limit).get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot item : queryDocumentSnapshots) {
                    try {
                        items.add(itemFactory.createItem(item.getId(),item.getData(), context, categoryType));
                    } catch (InvalidFetchedItem e) {
                        Log.d("Repository", "Item of id: " + item.getId() + " failed to be fetched/created!");
                    }
                }
            }

            callBack.onSuccess(items);
        });
    }

    @Override
    public List<ICategory> fetchCategories(ICategoryCallBack callBack) {
        if (categoryFactory == null) return null;

        List<ICategory> categories = new ArrayList<>();

        categoriesRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (QueryDocumentSnapshot category : queryDocumentSnapshots) {
                    categories.add(categoryFactory.createCategory(category.getId(),(Map<String,Object>) category.getData(), context));
                }
            }

            callBack.onSuccess(categories);
        });

        return categories;
    }
}
