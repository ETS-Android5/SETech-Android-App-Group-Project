package com.project.setech.model;

import android.content.Context;

import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;

public interface ICategoryFactory {
    public ICategory createCategory(String id,Map<String,Object> category, Context context);
}
