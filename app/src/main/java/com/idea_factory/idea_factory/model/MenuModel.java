package com.idea_factory.idea_factory.model;

import android.graphics.drawable.Drawable;

public class MenuModel {
    public String menuName;
    public boolean hasChildren, isGroup;
    public Drawable icon;

    public MenuModel(String menuName, boolean isGroup, boolean hasChildren, Drawable icon) {

        this.menuName = menuName;
        this.icon = icon;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }
}
