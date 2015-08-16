package com.divi.tsunapper.adapter;

/**
 * Created on 9/17/13.
 */
public class NavigationItem {
    private final int image;
    private final String text;

    public NavigationItem(int image, String text) {
        this.image = image;
        this.text = text;
    }

    public int getImage() {
        return image;
    }

    public String getText() {
        return text;
    }
}
