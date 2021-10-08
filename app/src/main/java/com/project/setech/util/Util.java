package com.project.setech.util;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Integer> formatDrawableStringList(List<String> images, Context context) {
        List<Integer> formatedImages= new ArrayList<>();

        for (String i : images) {
            formatedImages.add(context.getResources().getIdentifier(i,"drawable",context.getPackageName()));
        }

        return formatedImages;
    }
}
