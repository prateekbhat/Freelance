package com.idea_factory.idea_factory.model;

/**
 * Created by Admin on 9/18/2018.
 */

public class CategoryModel {

    String itemName = "";
    String itemImage = "";
    boolean status = false;

    public CategoryModel() {

    }

    public CategoryModel(String itemName, String itemImage, boolean status) {
        this.itemName = itemName;
        this.itemImage = itemImage;
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }
}
