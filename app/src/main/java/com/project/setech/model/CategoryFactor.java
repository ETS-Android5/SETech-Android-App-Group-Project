package com.project.setech.model;

import android.content.Context;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public class CategoryFactor implements ICategoryFactory{
    @Override
    public ICategory createCategory(QueryDocumentSnapshot category, Context context) {
        return null;
    }
}
