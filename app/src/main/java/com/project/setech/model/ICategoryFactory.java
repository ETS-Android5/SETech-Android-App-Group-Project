package com.project.setech.model;

import android.content.Context;

import com.google.firebase.firestore.QueryDocumentSnapshot;

public interface ICategoryFactory {
    public ICategory createCategory(QueryDocumentSnapshot category, Context context);
}
