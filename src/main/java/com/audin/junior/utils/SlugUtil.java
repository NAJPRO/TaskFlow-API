package com.audin.junior.utils;

public class SlugUtil {
    public static String toSlug(String input){
        if(input == null) return null;
        return input.toLowerCase()
            .trim()
            .replaceAll("[^a-z0-9]+", "-")
            .replaceAll("(^-|-$)", "");
    }
}
