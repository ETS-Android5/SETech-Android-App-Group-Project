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

    public static String splitCamelCase(String s) {
        s = s.substring(0, 1).toUpperCase() + s.substring(1);

        return s.replaceAll(
                String.format("%s|%s|%s",
                        "(?<=[A-Z])(?=[A-Z][a-z])",
                        "(?<=[^A-Z])(?=[A-Z])",
                        "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
        );
    }
}
